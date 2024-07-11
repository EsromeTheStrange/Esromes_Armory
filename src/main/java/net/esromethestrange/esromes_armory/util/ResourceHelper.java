package net.esromethestrange.esromes_armory.util;

import com.google.common.base.Charsets;
import com.google.gson.*;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.heat.*;
import net.esromethestrange.esromes_armory.recipe.ingredient.MaterialIngredientData;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
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
    public static final String JSON_ITEM = "item";
    public static final String JSON_FLUID = "fluid";
    public static final String JSON_AMOUNT = "amount";

    //Heat Data
    public static HeatData readHeatData(Identifier id, ResourceManager manager){
        String[] idParts = id.getPath().split("/");
        String materialTypeName = idParts[idParts.length-1].split(".json")[0];

        try(InputStream stream = manager.getResource(id).get().getInputStream()) {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new InputStreamReader(stream));
            return parseHeatData(jsonObject, id.getNamespace(), materialTypeName);
        } catch(Exception e) {
            EsromesArmory.LOGGER.error("Error occurred while loading Heat Data resource json" + id.toString(), e);
        }
        return HeatData.EMPTY;
    }


    private static HeatData parseHeatData(JsonObject json, String modId, String materialTypeName){
        HeatData newHeatData = new HeatData(Identifier.of(modId, materialTypeName));

        JsonObject results = json.get(JSON_ENTRIES).getAsJsonObject();
        for (String key : results.keySet()){
            JsonObject resultJson = results.getAsJsonObject(key);
            if(resultJson.has(JSON_ITEM)){
                Identifier itemId = Identifier.tryParse(resultJson.get(JSON_ITEM).getAsString());
                newHeatData.addEntry(HeatLevel.tryParse(key), new ItemHeatingResult(Registries.ITEM.get(itemId)));
            }
            if(resultJson.has(JSON_FLUID)){
                Identifier fluidId = Identifier.tryParse(resultJson.get(JSON_FLUID).getAsString());
                long fluidAmount = FluidConstants.BUCKET;
                if(resultJson.has(JSON_AMOUNT)){
                    fluidAmount = resultJson.get(JSON_AMOUNT).getAsLong();
                }
                newHeatData.addEntry(HeatLevel.tryParse(key), new FluidHeatingResult(Registries.FLUID.get(fluidId), fluidAmount));
            }
        }

        return newHeatData;
    }

    //Material Ingredients
    public static MaterialIngredientData readMaterialIngredient(Identifier id, ResourceManager manager){
        String[] idParts = id.getPath().split("/");
        String materialTypeName = idParts[idParts.length-1].split(".json")[0];

        try(InputStream stream = manager.getResource(id).get().getInputStream()) {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new InputStreamReader(stream));
            return parseMaterialIngredient(jsonObject, id.getNamespace(), materialTypeName);
        } catch(Exception e) {
            EsromesArmory.LOGGER.error("Error occurred while loading Material Ingredient resource json" + id.toString(), e);
        }
        return MaterialIngredientData.EMPTY;
    }


    private static MaterialIngredientData parseMaterialIngredient(JsonObject json, String modId, String materialTypeName){
        MaterialIngredientData newIngredient = new MaterialIngredientData(Identifier.of(modId, materialTypeName));

        if(json.has(JSON_ENTRIES)){
            JsonObject ingredients = json.get(JSON_ENTRIES).getAsJsonObject();
            for (String key : ingredients.keySet()){
                newIngredient.addEntry(Identifier.tryParse(key), Registries.ITEM.get(Identifier.tryParse(ingredients.get(key).getAsString())));
            }
        }

        if(json.has(JSON_FLUID)){
            JsonObject fluids = json.get(JSON_FLUID).getAsJsonObject();
            for (String key : fluids.keySet()){
                newIngredient.addEntry(Identifier.tryParse(key), Registries.FLUID.get(Identifier.tryParse(fluids.get(key).getAsString())));
            }
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
            Reader reader = getReaderForResource(location);
            ModelTransformation modelTransformation = JsonUnbakedModel.deserialize(reader).getTransformations();
            reader.close();
            return modelTransformation;
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
