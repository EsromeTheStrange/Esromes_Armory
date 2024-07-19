package net.esromethestrange.esromes_armory.util;

import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.item.material.PartBasedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class MaterialHelper {
    public static Identifier getItemModelIdentifier(Identifier materialId, Identifier itemId){
        String path = itemId.getPath() + "_" + materialId.getNamespace() + "_" + materialId.getPath();
        return Identifier.of(itemId.getNamespace(), path).withPrefixedPath("item/");
    }

    public static boolean isSpecialFuel(ItemStack stack){
        if(stack.getItem() instanceof MaterialItem materialItem)
            return materialItem.getFuelTime(stack) > 0;
        if(stack.getItem() instanceof PartBasedItem partBasedItem)
            return partBasedItem.getFuelTime(stack) > 0;
        return false;
    }
}
