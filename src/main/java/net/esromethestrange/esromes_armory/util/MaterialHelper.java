package net.esromethestrange.esromes_armory.util;

import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.item.material.PartBasedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class MaterialHelper {
    public static boolean isSpecialFuel(ItemStack stack){
        if(stack.getItem() instanceof MaterialItem materialItem)
            return materialItem.getFuelTime(stack) > 0;
        if(stack.getItem() instanceof PartBasedItem partBasedItem)
            return partBasedItem.getFuelTime(stack) > 0;
        return false;
    }

    public static String getTranslatableName(RegistryKey<Material> material){
        Identifier id = material.getValue();
        return id.getNamespace() + ".material." + id.getPath();
    }
    public static String getTranslatableName(RegistryEntry<Material> material){
        return getTranslatableName(material.getKey().get());
    }
}
