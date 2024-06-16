package net.esromethestrange.esromes_armory.recipe.ingredient;

import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;

public class ModIngredients {
    public static void registerIngredients(){
        CustomIngredientSerializer.register(MaterialIngredient.Serializer.INSTANCE);
    }
}
