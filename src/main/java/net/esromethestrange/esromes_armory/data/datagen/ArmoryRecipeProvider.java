package net.esromethestrange.esromes_armory.data.datagen;

import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ArmoryRecipeProvider extends FabricRecipeProvider {
    public ArmoryRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerBlasting(exporter, List.of(Items.IRON_INGOT), RecipeCategory.MISC, ArmoryItems.STEEL_INGOT, 1.2f, 1600, "steel_ingot");

        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ArmoryItems.STEEL_INGOT, RecipeCategory.DECORATIONS, ArmoryBlocks.STEEL_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, Items.CHARCOAL, RecipeCategory.DECORATIONS, ArmoryBlocks.CHARCOAL_BLOCK);
    }
}
