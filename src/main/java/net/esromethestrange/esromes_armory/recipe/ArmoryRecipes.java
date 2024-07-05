package net.esromethestrange.esromes_armory.recipe;

import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ArmoryRecipes {
    public static final RecipeType<WorkbenchRecipe> WORKBENCH_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, WorkbenchRecipe.ID,
            new RecipeType<WorkbenchRecipe>() {
                @Override public String toString() { return WorkbenchRecipe.ID.toString(); }
            });

    public static void registerRecipes(){
        Registry.register(Registries.RECIPE_SERIALIZER, WorkbenchRecipe.ID, WorkbenchRecipe.Serializer.INSTANCE);
    }
}
