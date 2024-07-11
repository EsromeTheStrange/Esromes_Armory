package net.esromethestrange.esromes_armory.util;

import net.minecraft.util.Identifier;

public class MaterialHelper {
    public static Identifier getItemModelIdentifier(Identifier materialId, Identifier itemId){
        String path = itemId.getPath() + "_" + materialId.getNamespace() + "_" + materialId.getPath();
        return Identifier.of(itemId.getNamespace(), path).withPrefixedPath("item/");
    }
}
