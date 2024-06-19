package net.esromethestrange.esromes_armory.item.material;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.MaterialHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public interface MaterialItem {
    String NBT_MATERIAL = EsromesArmory.MOD_ID + ".material";

    ItemStack getStack(ArmoryMaterial material);

    default void setMaterial(ItemStack stack, ArmoryMaterial material){
        NbtCompound nbt = stack.getNbt();
        if(nbt == null)
            nbt = new NbtCompound();
        nbt.putString(NBT_MATERIAL, material.id.toString());
        stack.setNbt(nbt);
    }
    default ArmoryMaterial getMaterial(ItemStack stack){
        NbtCompound nbt = stack.getNbt();
        if(nbt == null)
            return ArmoryMaterial.NONE;
        String materialId = nbt.getString(NBT_MATERIAL);
        return MaterialHandler.getMaterial(Identifier.tryParse(materialId));
    }
    Identifier getIdentifier();
}
