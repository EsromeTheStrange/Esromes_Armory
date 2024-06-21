package net.esromethestrange.esromes_armory.util;

import com.google.gson.*;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialInfo;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ArmoryResourceHelper {
    private static final String JSON_DURABILITY = "durability";
    private static final String JSON_MINING_LEVEL = "miningLevel";
    private static final String JSON_MINING_SPEED = "miningSpeed";
    private static final String JSON_ATTACK_DAMAGE = "attackDamage";
    private static final String JSON_ATTACK_SPEED = "attackSpeed";
    private static final String JSON_ENCHANTABILITY = "enchantability";
    private static final String JSON_COLOR = "color";
    private static final String JSON_ITEMS = "items";

    private static final String JSON_MATERIALS = "materials";

    //Materials
    public static ArmoryMaterial readMaterial(Identifier id, ResourceManager manager){
        String[] idParts = id.getPath().split("/");
        String materialName = idParts[idParts.length-1].split(".json")[0];

        try(InputStream stream = manager.getResource(id).get().getInputStream()) {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new InputStreamReader(stream));
            return parseMaterial(jsonObject, id.getNamespace(), materialName);
        } catch(Exception e) {
            EsromesArmory.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
        }
        return ArmoryMaterial.NONE;
    }

    private static ArmoryMaterial parseMaterial(JsonObject jsonObject, String modId, String materialName){
        int durability = jsonObject.get(JSON_DURABILITY).getAsInt();
        int miningLevel = jsonObject.get(JSON_MINING_LEVEL).getAsInt();
        float miningSpeed = jsonObject.get(JSON_MINING_SPEED).getAsInt();
        int attackDamage = jsonObject.get(JSON_ATTACK_DAMAGE).getAsInt();
        float attackSpeed = jsonObject.get(JSON_ATTACK_SPEED).getAsFloat();
        int enchantability = jsonObject.get(JSON_ENCHANTABILITY).getAsInt();
        int color = jsonObject.get(JSON_COLOR).getAsInt();

        ArmoryMaterial newMaterial = new ArmoryMaterial(modId, materialName, color,
                durability, miningLevel, miningSpeed,
                attackDamage, attackSpeed,
                enchantability);

        JsonObject items = jsonObject.get(JSON_ITEMS).getAsJsonObject();
        for(String itemType : items.keySet()){
            String itemString = items.get(itemType).getAsString();
            Item item = Registries.ITEM.getOrEmpty(Identifier.tryParse(itemString)).orElseThrow(()
                    -> new JsonSyntaxException("Unknown item '" + itemString + "'"));
            newMaterial.addItem(itemType, item);
        }

        return newMaterial;
    }

    public static JsonObject writeToJson(ArmoryMaterialInfo materialInfo){
        JsonObject json = new JsonObject();
        json.addProperty(JSON_DURABILITY, materialInfo.durability);
        json.addProperty(JSON_MINING_LEVEL, materialInfo.miningLevel);
        json.addProperty(JSON_MINING_SPEED, materialInfo.miningSpeed);

        json.addProperty(JSON_ATTACK_DAMAGE, materialInfo.attackDamage);
        json.addProperty(JSON_ATTACK_SPEED, materialInfo.attackSpeed);
        json.addProperty(JSON_ENCHANTABILITY, materialInfo.enchantability);

        json.addProperty(JSON_COLOR, materialInfo.color);

        JsonObject itemsJson = new JsonObject();
        for(String key : materialInfo.items.keySet()){
            Item item = materialInfo.items.get(key);
            itemsJson.addProperty(key, Registries.ITEM.getId(item).toString());
        }

        json.add(JSON_ITEMS, itemsJson);
        return json;
    }


    //Material Types
    public static ArmoryMaterialType readMaterialType(Identifier id, ResourceManager manager){
        String[] idParts = id.getPath().split("/");
        String materialTypeName = idParts[idParts.length-1].split(".json")[0];

        try(InputStream stream = manager.getResource(id).get().getInputStream()) {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new InputStreamReader(stream));
            return parseMaterialType(jsonObject, id.getNamespace(), materialTypeName);
        } catch(Exception e) {
            EsromesArmory.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
        }
        return ArmoryMaterialType.NONE;
    }

    private static ArmoryMaterialType parseMaterialType(JsonObject json, String modId, String materialTypeName){
        JsonArray materialsArray = json.get(JSON_MATERIALS).getAsJsonArray();
        List<Identifier> materials = new ArrayList<>();
        for (JsonElement element : materialsArray){
            materials.add(Identifier.tryParse(element.getAsString()));
        }

        return new ArmoryMaterialType(modId, materialTypeName, materials);
    }
}
