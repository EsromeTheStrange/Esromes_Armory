package net.esromethestrange.esromes_armory.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.esromethestrange.esromes_armory.recipe.AnvilRecipe;
import net.esromethestrange.esromes_armory.recipe.WorkbenchRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class EmiWorkbenchRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EmiWorkbenchRecipe(Identifier id, WorkbenchRecipe recipe) {
        this.id = id;
        this.input = new ArrayList<>();
        for(Ingredient ingredient : recipe.getIngredients()){
            input.add(EmiIngredient.of(ingredient));
        }
        this.output = List.of(EmiStack.of(recipe.getResult(null)));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EsromesArmoryEmiPlugin.WORKBENCH_RECIPE;
    }

    @Override public Identifier getId() { return id; }
    @Override public List<EmiIngredient> getInputs() { return input; }
    @Override public List<EmiStack> getOutputs() { return output; }

    @Override public int getDisplayWidth() { return 116; }
    @Override public int getDisplayHeight() { return 18; }


    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 66, 1);

        widgets.addSlot(input.get(0), 0, 0);
        widgets.addSlot(input.get(1), 20, 0);
        widgets.addSlot(input.get(2), 40, 0);

        widgets.addSlot(output.getFirst(), 98, 0).recipeContext(this);
    }
}
