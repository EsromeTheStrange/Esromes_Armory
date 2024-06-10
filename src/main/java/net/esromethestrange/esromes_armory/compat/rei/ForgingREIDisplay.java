package net.esromethestrange.esromes_armory.compat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.esromethestrange.esromes_armory.recipe.ForgingRecipe;

import java.util.*;

public class ForgingREIDisplay extends BasicDisplay {
    //TODO Fix this entire class when I change forging.
    public ForgingREIDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public ForgingREIDisplay(ForgingRecipe recipe){
        super(getInputs(recipe), getOutputs(recipe));
    }

    private static List<EntryIngredient> getInputs(ForgingRecipe recipe){
        if(recipe == null) return Collections.emptyList();
        List<EntryIngredient> inputs = new ArrayList<>();
        inputs.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(0)));
        return inputs;
    }
    private static List<EntryIngredient> getOutputs(ForgingRecipe recipe){
        if(recipe == null) return Collections.emptyList();
        List<EntryIngredient> outputs = new ArrayList<>();
        outputs.add(EntryIngredients.of(recipe.getOutput(null)));
        return outputs;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ForgingREICategory.FORGING;
    }
}
