package net.esromethestrange.esromes_armory.data.recipe;

import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ArmoryRecipes {
    public static final RecipeType<WorkbenchRecipe> WORKBENCH_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, WorkbenchRecipe.ID,
            new RecipeType<WorkbenchRecipe>() {
                @Override public String toString() { return WorkbenchRecipe.ID.toString(); }
            });
    public static final RecipeType<AnvilRecipe> ANVIL_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, AnvilRecipe.ID,
            new RecipeType<AnvilRecipe>() {
                @Override public String toString() { return AnvilRecipe.ID.toString(); }
            });

    public static void registerRecipes(){
        Registry.register(Registries.RECIPE_SERIALIZER, WorkbenchRecipe.ID, WorkbenchRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, AnvilRecipe.ID, AnvilRecipe.Serializer.INSTANCE);
    }
}
