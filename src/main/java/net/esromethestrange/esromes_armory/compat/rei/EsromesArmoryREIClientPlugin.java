package net.esromethestrange.esromes_armory.compat.rei;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.esromethestrange.esromes_armory.block.ModBlocks;
import net.esromethestrange.esromes_armory.recipe.ForgingRecipe;
import net.esromethestrange.esromes_armory.recipe.ModRecipes;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;

public class EsromesArmoryREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new ForgingREICategory());

        registry.addWorkstations(ForgingREICategory.FORGING, EntryStacks.of(ModBlocks.FORGE));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(ForgingRecipe.class, ModRecipes.FORGE_RECIPE_TYPE, ForgingREIDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        //TODO put real numbers here instead of hardcoding values.
        registry.registerClickArea(screen -> new Rectangle(75,30,20,30), ForgingScreen.class, ForgingREICategory.FORGING);
    }
}
