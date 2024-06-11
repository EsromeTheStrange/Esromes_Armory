package net.esromethestrange.esromes_armory.recipe;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static final RecipeType<ForgingRecipe> FORGE_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, new Identifier(EsromesArmory.MOD_ID, ForgingRecipe.ID),
            new RecipeType<ForgingRecipe>() {
                @Override public String toString() { return ForgingRecipe.ID; }
            });

    public static void registerRecipes(){
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(EsromesArmory.MOD_ID, ForgingRecipe.ID), ForgingRecipe.Serializer.INSTANCE);
    }
}