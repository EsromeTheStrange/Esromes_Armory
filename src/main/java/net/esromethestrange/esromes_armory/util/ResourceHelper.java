package net.esromethestrange.esromes_armory.util;

import com.google.common.base.Charsets;
import com.google.gson.*;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.recipe.ingredient.MaterialIngredientData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.*;

public class ResourceHelper {
    public static final ModelTransformation HANDHELD_TRANSFORMATION = loadTransformFromJson(Identifier.of("minecraft:models/item/handheld"));

    public static final String JSON_ENTRIES = "entries";

    //Material Ingredients
    public static MaterialIngredientData readMaterialIngredient(Identifier id, ResourceManager manager){
        String[] idParts = id.getPath().split("/");
        String materialTypeName = idParts[idParts.length-1].split(".json")[0];

        try(InputStream stream = manager.getResource(id).get().getInputStream()) {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new InputStreamReader(stream));
            return parseMaterialIngredient(jsonObject, id.getNamespace(), materialTypeName);
        } catch(Exception e) {
            EsromesArmory.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
        }
        return MaterialIngredientData.EMPTY;
    }


    private static MaterialIngredientData parseMaterialIngredient(JsonObject json, String modId, String materialTypeName){
        MaterialIngredientData newIngredient = new MaterialIngredientData(Identifier.of(modId, materialTypeName));

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
        Identifier file = Identifier.of(location.getNamespace(), location.getPath() + ".json");
        Resource resource = MinecraftClient.getInstance().getResourceManager().getResource(file).get();
        return new BufferedReader(new InputStreamReader(resource.getInputStream(), Charsets.UTF_8));
    }
}
