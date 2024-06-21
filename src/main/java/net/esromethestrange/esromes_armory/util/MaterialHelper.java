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

    public static String getTranslatableName(ItemStack stack){
        if(stack.getItem() instanceof MaterialItem materialItem){
            Identifier materialIdentifier = materialItem.getMaterial(stack).id;
            return "item." + new Identifier(materialIdentifier.getNamespace(),
                    materialIdentifier.getPath() + "_" + materialItem.getRawIdentifier().getPath())
                    .toString().replace(":",".");
        }

        EsromesArmory.LOGGER.error("Failed to get material id for stack: " + stack.toString());
        return ArmoryMaterial.NONE.translatable_name;
    }
}
