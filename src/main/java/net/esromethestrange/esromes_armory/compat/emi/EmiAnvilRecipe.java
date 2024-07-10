package net.esromethestrange.esromes_armory.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.esromethestrange.esromes_armory.data.heat.HeatLevel;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.recipe.AnvilRecipe;
import net.esromethestrange.esromes_armory.recipe.CastingRecipe;
import net.esromethestrange.esromes_armory.recipe.ingredient.MaterialIngredient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmiAnvilRecipe implements EmiRecipe {
    public static final String HEATING_TRANSLATION_KEY = "emi.description.esromes_armory.heating";

    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;
    private final HeatLevel requiredHeatLevel;

    public EmiAnvilRecipe(Identifier id, AnvilRecipe recipe) {
        this.id = id;
        this.input = new ArrayList<>();
        for(Ingredient ingredient : recipe.getIngredients()){
            input.add(EmiIngredient.of(ingredient));
        }
        this.output = List.of(EmiStack.of(recipe.getResult(null)));
        this.requiredHeatLevel = recipe.getRequiredHeatLevel();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EsromesArmoryEmiPlugin.ANVIL_RECIPE;
    }

    @Override public Identifier getId() { return id; }
    @Override public List<EmiIngredient> getInputs() { return input; }
    @Override public List<EmiStack> getOutputs() { return output; }

    @Override public int getDisplayWidth() { return 96; }
    @Override public int getDisplayHeight() { return 26; }


    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 46, 1);
        widgets.addText(Text.translatable(requiredHeatLevel.translation_key), 0, 18, requiredHeatLevel.color, true);
        widgets.addTooltipText(List.of(Text.translatable(HEATING_TRANSLATION_KEY)), 0, 18, 48, 8);

        widgets.addSlot(input.get(0), 0, 0);
        widgets.addSlot(input.get(1), 20, 0);

        widgets.addSlot(output.getFirst(), 78, 0).recipeContext(this);
    }
}
