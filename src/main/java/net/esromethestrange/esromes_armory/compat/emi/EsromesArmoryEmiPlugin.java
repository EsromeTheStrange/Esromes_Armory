package net.esromethestrange.esromes_armory.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ModBlocks;
import net.esromethestrange.esromes_armory.recipe.ForgingRecipe;
import net.esromethestrange.esromes_armory.recipe.ModRecipes;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;

public class EsromesArmoryEmiPlugin implements EmiPlugin {
    public static final Identifier FORGING_SPRITESHEET = new Identifier(EsromesArmory.MOD_ID, "textures/gui/forging_emi.png");
    public static final EmiStack FORGING = EmiStack.of(ModBlocks.FORGE);
    public static final EmiRecipeCategory FORGING_CATEGORY = new EmiRecipeCategory(ForgingRecipe.ID,
            FORGING, new EmiTexture(FORGING_SPRITESHEET, 0, 0, 16, 16));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(FORGING_CATEGORY);

        registry.addWorkstation(FORGING_CATEGORY, FORGING);

        RecipeManager manager = registry.getRecipeManager();

        for (ForgingRecipe recipe : manager.listAllOfType(ModRecipes.FORGE_RECIPE_TYPE)){
            ItemStack[] possibleOutputs = recipe.getIngredients().get(0).getMatchingStacks();
            for(int i=0; i<possibleOutputs.length; i++){
                ItemStack input = possibleOutputs[i];
                registry.addRecipe(new ForgingEmiRecipe(recipe.getId().withSuffixedPath("_" + i), input, recipe.craft(new SimpleInventory(input), null)));
            }
        }
    }
}
