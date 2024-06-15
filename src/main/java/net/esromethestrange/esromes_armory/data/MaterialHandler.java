package net.esromethestrange.esromes_armory.data;

import com.google.gson.*;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MaterialHandler implements SimpleSynchronousResourceReloadListener {
    private static HashMap<Identifier, ArmoryMaterial> materials = new HashMap<>();
    private static HashMap<Identifier, ArmoryMaterialType> materialTypes = new HashMap<>();

    @Override
    public Identifier getFabricId() {
        return new Identifier(EsromesArmory.MOD_ID, "material");
    }

    @Override
    public void reload(ResourceManager manager) {
        clearMaterials();
        EsromesArmory.LOGGER.info("Loading Materials...");

        for(Identifier id : manager.findResources("esrome/materials", i -> i.toString().endsWith(".json")).keySet()) {
            String[] idParts = id.getPath().split("/");
            String materialId = idParts[idParts.length-1].split(".json")[0];

            try(InputStream stream = manager.getResource(id).get().getInputStream()) {
                JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new InputStreamReader(stream));
                readMaterial(id.getNamespace(), materialId, jsonObject);
            } catch(Exception e) {
                EsromesArmory.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
            }
        }

        for(Identifier id : manager.findResources("esrome/material_types", i -> i.toString().endsWith(".json")).keySet()) {
            String[] idParts = id.getPath().split("/");
            String materialTypeName = idParts[idParts.length-1].split(".json")[0];

            try(InputStream stream = manager.getResource(id).get().getInputStream()) {
                JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new InputStreamReader(stream));
                readMaterialType(id.getNamespace(), materialTypeName, jsonObject);
            } catch(Exception e) {
                EsromesArmory.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
            }
        }
    }

    private void clearMaterials(){ materials.clear(); }

    private void readMaterial(String modId, String materialName, JsonObject jsonObject){
        int durability = jsonObject.get("durability").getAsInt();
        int miningLevel = jsonObject.get("miningLevel").getAsInt();
        float miningSpeed = jsonObject.get("miningSpeed").getAsInt();
        int attackDamage = jsonObject.get("attackDamage").getAsInt();
        float attackSpeed = jsonObject.get("attackSpeed").getAsFloat();
        int enchantability = jsonObject.get("enchantability").getAsInt();
        int color = Integer.decode(jsonObject.get("color").getAsString());

        ArmoryMaterial newMaterial = new ArmoryMaterial(modId, materialName, color,
                durability, miningLevel, miningSpeed,
                attackDamage, attackSpeed,
                enchantability);

        JsonObject items = jsonObject.get("items").getAsJsonObject();
        for(String itemType : items.keySet()){
            String itemString = items.get(itemType).getAsString();
            Item item = Registries.ITEM.getOrEmpty(Identifier.tryParse(itemString)).orElseThrow(()
                    -> new JsonSyntaxException("Unknown item '" + itemString + "'"));
            newMaterial.addItem(itemType, item);
        }

        materials.put(newMaterial.id, newMaterial);
        EsromesArmory.LOGGER.info("Material created with id "+newMaterial.id.toString());
    }

    private void readMaterialType(String modId, String materialTypeName, JsonObject jsonObject){
        JsonArray materialsArray = jsonObject.get("materials").getAsJsonArray();
        List<Identifier> materials = new ArrayList<>();
        for (JsonElement element : materialsArray){
            materials.add(Identifier.tryParse(element.getAsString()));
        }
        ArmoryMaterialType newMaterialType = new ArmoryMaterialType(modId, materialTypeName, materials);
        materialTypes.put(newMaterialType.id, newMaterialType);

        EsromesArmory.LOGGER.info("Material Type created with id "+newMaterialType.id.toString()+
                " containing: "+ Arrays.toString(newMaterialType.materials.toArray()));
    }

    public static ArmoryMaterial getMaterial(Identifier id) {
        ArmoryMaterial material = materials.get(id);
        return material == null ? ArmoryMaterial.NONE : material;
    }
    public static ArmoryMaterialType getMaterialType(Identifier id){
        ArmoryMaterialType materialType = materialTypes.get(id);
        return materialType == null ? ArmoryMaterialType.NONE : materialType;
    }
}
