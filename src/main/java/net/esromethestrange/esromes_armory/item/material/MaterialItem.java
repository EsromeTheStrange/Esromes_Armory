package net.esromethestrange.esromes_armory.item.material;

import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.minecraft.item.ItemStack;

public interface MaterialItem {
    void setMaterial(ItemStack stack, ArmoryMaterial material);
    ArmoryMaterial getMaterial(ItemStack stack);
}
