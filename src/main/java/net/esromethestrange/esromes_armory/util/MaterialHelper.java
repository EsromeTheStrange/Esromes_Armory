package net.esromethestrange.esromes_armory.util;

import net.minecraft.util.Identifier;

public class MaterialHelper {
    public static Identifier getItemIdWithMaterial(Identifier materialId, Identifier itemId){
        String path = itemId.getPath() + "_" + materialId.getNamespace() + "_" + materialId.getPath();
        return Identifier.of(itemId.getNamespace(), path);
    }
}
