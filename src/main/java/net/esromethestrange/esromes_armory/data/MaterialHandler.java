package net.esromethestrange.esromes_armory.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MaterialHandler implements SimpleSynchronousResourceReloadListener {
    private static HashMap<String, ArmoryMaterial> materials = new HashMap<>();
    private static HashMap<String, ArmoryMaterialType> materialTypes = new HashMap<>();

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
        materials.put(newMaterial.id.toString(), newMaterial);
        EsromesArmory.LOGGER.info("Material created with id "+newMaterial.id.toString());
    }

    private void readMaterialType(String modId, String materialTypeName, JsonObject jsonObject){
        JsonArray materialsArray = jsonObject.get("materials").getAsJsonArray();
        List<String> materials = new ArrayList<>();
        for (JsonElement element : materialsArray){
            materials.add(element.getAsString());
        }
        ArmoryMaterialType newMaterialType = new ArmoryMaterialType(modId, materialTypeName, materials);
        materialTypes.put(newMaterialType.id.toString(), newMaterialType);

        EsromesArmory.LOGGER.info("Material Type created with id "+newMaterialType.id.toString()+
                " containing: "+ Arrays.toString(newMaterialType.materials.toArray()));
    }

    public static ArmoryMaterial getMaterial(String id) {
        if(!materials.containsKey(id)) return ArmoryMaterial.NONE;
        return materials.get(id);
    }
    public static ArmoryMaterialType getMaterialType(String id){
        if(!materials.containsKey(id)) return ArmoryMaterialType.NONE;
        return materialTypes.get(id);
    }
}
