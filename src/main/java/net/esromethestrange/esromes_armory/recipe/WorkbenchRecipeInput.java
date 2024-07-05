package net.esromethestrange.esromes_armory.recipe;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

import java.util.List;

public class WorkbenchRecipeInput implements RecipeInput {
    private final List<ItemStack> stacks;

    public WorkbenchRecipeInput(SimpleInventory inventory){
        this.stacks = inventory.heldStacks;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return stacks.get(slot);
    }

    @Override
    public int getSize() {
        return 3;
    }
}
