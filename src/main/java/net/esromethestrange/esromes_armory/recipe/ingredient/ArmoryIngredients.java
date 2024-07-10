package net.esromethestrange.esromes_armory.recipe.ingredient;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.util.ResourceHelper;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class ArmoryIngredients implements SimpleSynchronousResourceReloadListener {
    private static HashMap<Identifier, MaterialIngredientData> materialIngredients = new HashMap<>();

    @Override
    public Identifier getFabricId() {
        return Identifier.of(EsromesArmory.MOD_ID, "ingredient");
    }

    @Override
    public void reload(ResourceManager manager) {
        materialIngredients.clear();
        for(Identifier id : manager.findResources("esrome/material_ingredients", i -> i.toString().endsWith(".json")).keySet()){
            MaterialIngredientData ingredientData = ResourceHelper.readMaterialIngredient(id, manager);
            materialIngredients.put(ingredientData.id, ingredientData);
        }

        EsromesArmory.LOGGER.info("Loaded " + materialIngredients.size() + " material ingredients");
    }

    public static MaterialIngredientData getMaterialIngredient(Identifier id){
        if(materialIngredients.containsKey(id))
            return materialIngredients.get(id);
        return MaterialIngredientData.EMPTY;
    }

    public static void registerIngredients(){
        CustomIngredientSerializer.register(MaterialIngredient.Serializer.INSTANCE);
    }
}
