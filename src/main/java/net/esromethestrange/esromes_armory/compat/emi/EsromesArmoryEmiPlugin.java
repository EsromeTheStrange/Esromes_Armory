package net.esromethestrange.esromes_armory.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.esromethestrange.esromes_armory.recipe.AnvilRecipe;
import net.esromethestrange.esromes_armory.recipe.ArmoryRecipes;
import net.esromethestrange.esromes_armory.recipe.CastingRecipe;
import net.esromethestrange.esromes_armory.recipe.WorkbenchRecipe;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;

public class EsromesArmoryEmiPlugin implements EmiPlugin {
    public static final Identifier SPRITE_SHEET = Identifier.of(EsromesArmory.MOD_ID, "textures/gui/emi_simplified_textures.png");

    public static final EmiStack SMELTERY = EmiStack.of(ArmoryBlocks.SMELTERY);
    public static final EmiRecipeCategory CASTING = new EmiRecipeCategory(
            Identifier.of(EsromesArmory.MOD_ID, "casting"), SMELTERY,
            new EmiTexture(SPRITE_SHEET, 0, 0, 16, 16));

    public static final EmiStack ANVIL = EmiStack.of(ArmoryBlocks.ANVIL);
    public static final EmiRecipeCategory ANVIL_RECIPE = new EmiRecipeCategory(
            Identifier.of(EsromesArmory.MOD_ID, "anvil"), ANVIL,
            new EmiTexture(SPRITE_SHEET, 0, 0, 16, 16));

    public static final EmiStack WORKBENCH = EmiStack.of(ArmoryBlocks.WORKBENCH);
    public static final EmiRecipeCategory WORKBENCH_RECIPE = new EmiRecipeCategory(
            Identifier.of(EsromesArmory.MOD_ID, "workbench"), WORKBENCH,
            new EmiTexture(SPRITE_SHEET, 0, 0, 16, 16));

    @Override
    public void register(EmiRegistry registry) {
        RecipeManager manager = registry.getRecipeManager();

        //Casting
        registry.addCategory(CASTING);
        registry.addWorkstation(CASTING, SMELTERY);

        for (RecipeEntry<CastingRecipe> recipe : manager.listAllOfType(ArmoryRecipes.CASTING_RECIPE_TYPE)) {
            registry.addRecipe(new EmiCastingRecipe(recipe.id(), recipe.value()));
        }

        //Anvil
        registry.addCategory(ANVIL_RECIPE);
        registry.addWorkstation(ANVIL_RECIPE, ANVIL);

        for (RecipeEntry<AnvilRecipe> recipe : manager.listAllOfType(ArmoryRecipes.ANVIL_RECIPE_TYPE)) {
            registry.addRecipe(new EmiAnvilRecipe(recipe.id(), recipe.value()));
        }

        //Workbench
        registry.addCategory(WORKBENCH_RECIPE);
        registry.addWorkstation(WORKBENCH_RECIPE, WORKBENCH);

        for (RecipeEntry<WorkbenchRecipe> recipe : manager.listAllOfType(ArmoryRecipes.WORKBENCH_RECIPE_TYPE)) {
            registry.addRecipe(new EmiWorkbenchRecipe(recipe.id(), recipe.value()));
        }
    }
}
