package net.esromethestrange.esromes_armory.util;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class MaterialHelper {
    public static Identifier getItemIdWithMaterial(Identifier materialId, Identifier itemId){
        String path = itemId.getPath() + "_" + materialId.getNamespace() + "_" + materialId.getPath();
        return new Identifier(itemId.getNamespace(), path);
    }
}
