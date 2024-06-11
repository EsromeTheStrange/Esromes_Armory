package net.esromethestrange.esromes_armory.compat.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.esromethestrange.esromes_armory.recipe.ForgingRecipe;

public class ForgingEmiRecipe extends BasicEmiRecipe {
    public ForgingEmiRecipe(ForgingRecipe recipe) {
        super(EsromesArmoryEmiPlugin.FORGING_CATEGORY, recipe.getId(), 70, 18);
        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));
        this.outputs.add(EmiStack.of(recipe.getOutput(null)));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        // Add an arrow texture to indicate processing
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 26, 1);

        // Adds an input slot on the left
        widgets.addSlot(inputs.get(0), 0, 0);

        // Adds an output slot on the right
        // Note that output slots need to call `recipeContext` to inform EMI about their recipe context
        // This includes being able to resolve recipe trees, favorite stacks with recipe context, and more
        widgets.addSlot(outputs.get(0), 58, 0).recipeContext(this);
    }
}
