package net.esromethestrange.esromes_armory.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {
    public static final RecipeType<ForgingRecipe> FORGE_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, ForgingRecipe.ID,
            new RecipeType<ForgingRecipe>() {
                @Override public String toString() { return ForgingRecipe.ID.getPath(); }
            });
    public static final RecipeType<WorkbenchRecipe> WORKBENCH_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, WorkbenchRecipe.ID,
            new RecipeType<WorkbenchRecipe>() {
                @Override public String toString() { return WorkbenchRecipe.ID.getPath(); }
            });

    public static void registerRecipes(){
        Registry.register(Registries.RECIPE_SERIALIZER, ForgingRecipe.ID, ForgingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, WorkbenchRecipe.ID, WorkbenchRecipe.Serializer.INSTANCE);
    }
}
