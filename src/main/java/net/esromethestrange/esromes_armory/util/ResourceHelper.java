package net.esromethestrange.esromes_armory.util;

import com.google.common.base.Charsets;
import com.google.gson.*;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialInfo;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialIngredientInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ResourceHelper {
    public static final String JSON_DURABILITY = "durability";
    public static final String JSON_MINING_LEVEL = "miningLevel";
    public static final String JSON_MINING_SPEED = "miningSpeed";
    public static final String JSON_ATTACK_DAMAGE = "attackDamage";
    public static final String JSON_ATTACK_SPEED = "attackSpeed";
    public static final String JSON_ENCHANTABILITY = "enchantability";
    public static final String JSON_COLOR = "color";

    public static final String JSON_MATERIALS = "materials";

    public static final String JSON_ENTRIES = "entries";

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

        return json;
    }

    public static List<Identifier> getExpectedMaterials(){
        List<Identifier> materialsList = new ArrayList<>();

        materialsList.add(new Identifier(EsromesArmory.MOD_ID, "none"));
        materialsList.add(new Identifier(EsromesArmory.MOD_ID, "steel"));
        materialsList.add(new Identifier("minecraft", "iron"));
        materialsList.add(new Identifier("minecraft", "oak"));
        materialsList.add(new Identifier("minecraft", "string"));
        materialsList.add(new Identifier("minecraft", "slime"));

        return materialsList;
    }

    //Material Ingredients
    public static ArmoryMaterialIngredientInfo readMaterialIngredient(Identifier id, ResourceManager manager){
        String[] idParts = id.getPath().split("/");
        String materialTypeName = idParts[idParts.length-1].split(".json")[0];

        try(InputStream stream = manager.getResource(id).get().getInputStream()) {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new InputStreamReader(stream));
            return parseMaterialIngredient(jsonObject, id.getNamespace(), materialTypeName);
        } catch(Exception e) {
            EsromesArmory.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
        }
        return ArmoryMaterialIngredientInfo.NONE;
    }


    private static ArmoryMaterialIngredientInfo parseMaterialIngredient(JsonObject json, String modId, String materialTypeName){
        ArmoryMaterialIngredientInfo newIngredient = new ArmoryMaterialIngredientInfo(new Identifier(modId, materialTypeName));

        JsonObject ingredients = json.get(JSON_ENTRIES).getAsJsonObject();
        for (String key : ingredients.keySet()){
            newIngredient.addEntry(Identifier.tryParse(key), Registries.ITEM.get(Identifier.tryParse(ingredients.get(key).getAsString())));
        }

        return newIngredient;
    }


    //Model Stuff
    /**
     * This code was taken from Smithee.
     * @author LordDeatHunter
     */
    public static ModelTransformation loadTransformFromJson(Identifier location) {
        try {
            return JsonUnbakedModel.deserialize(getReaderForResource(location)).getTransformations();
        } catch (IOException exception) {
            EsromesArmory.LOGGER.warn("Can't load resource " + location);
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * This code was taken from Smithee.
     * @author LordDeatHunter
     */
    public static Reader getReaderForResource(Identifier location) throws IOException {
        Identifier file = new Identifier(location.getNamespace(), location.getPath() + ".json");
        Resource resource = MinecraftClient.getInstance().getResourceManager().getResource(file).get();
        return new BufferedReader(new InputStreamReader(resource.getInputStream(), Charsets.UTF_8));
    }
}
