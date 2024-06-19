package net.esromethestrange.esromes_armory.datagen;

import net.esromethestrange.esromes_armory.block.ModBlocks;
import net.esromethestrange.esromes_armory.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerBlasting(exporter, List.of(Items.IRON_INGOT), RecipeCategory.MISC, ModItems.STEEL_INGOT, 1.2f, 1600, "steel_ingot");

        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.STEEL_INGOT, RecipeCategory.DECORATIONS, ModBlocks.STEEL_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, Items.CHARCOAL, RecipeCategory.DECORATIONS, ModBlocks.CHARCOAL_BLOCK);
    }
}
