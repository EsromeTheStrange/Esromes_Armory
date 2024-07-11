package net.esromethestrange.esromes_armory.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.TankWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.esromethestrange.esromes_armory.recipe.CastingRecipe;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class EmiCastingRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EmiCastingRecipe(Identifier id, CastingRecipe recipe) {
        this.id = id;
        this.input = new ArrayList<>();
        input.add(EmiIngredient.of(recipe.getInput()));

        List<EmiStack> fluidInput = new ArrayList<>();
        for(FluidVariant fluidVariant : recipe.getFluidTester().getFluids()){
            fluidInput.add(EmiStack.of(fluidVariant.getFluid(), recipe.getFluidAmount()));
        }
        input.add(EmiIngredient.of(fluidInput));

        this.output = List.of(EmiStack.of(recipe.getOutput()));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EsromesArmoryEmiPlugin.CASTING;
    }

    @Override public Identifier getId() { return id; }
    @Override public List<EmiIngredient> getInputs() { return input; }
    @Override public List<EmiStack> getOutputs() { return output; }

    @Override public int getDisplayWidth() { return 90; }
    @Override public int getDisplayHeight() { return 18; }


    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 40, 1);

        widgets.add(new TankWidget(input.get(1), 0, 0, 12, 18, 1)
                .drawBack(false));
        widgets.addSlot(input.get(0), 14, 0);

        widgets.addSlot(output.getFirst(), 72, 0).recipeContext(this);
    }
}
