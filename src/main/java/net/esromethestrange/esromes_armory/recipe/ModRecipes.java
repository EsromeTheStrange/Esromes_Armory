package net.esromethestrange.esromes_armory.recipe;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static final RecipeType<ForgeRecipe> FORGE_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, new Identifier(EsromesArmory.MOD_ID, ForgeRecipe.ID),
            new RecipeType<ForgeRecipe>() {
                @Override public String toString() { return ForgeRecipe.ID; }
            });

    public static void registerRecipes(){
        EsromesArmory.LOGGER.info("Registering recipes...");

        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(EsromesArmory.MOD_ID, ForgeRecipe.ID), ForgeRecipe.Serializer.INSTANCE);
    }
}
