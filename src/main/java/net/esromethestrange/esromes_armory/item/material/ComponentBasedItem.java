package net.esromethestrange.esromes_armory.item.material;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.MaterialHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.List;

public interface ComponentBasedItem {
    String NBT_MATERIALPREFIX = EsromesArmory.MOD_ID + ".materials.";

    default void setMaterial(ItemStack stack, MaterialItem component, ArmoryMaterial material){
        if(!containsComponent(component)) return;

        NbtCompound nbt = stack.getNbt();
        if(nbt == null)
            nbt = new NbtCompound();
        nbt.putString(NBT_MATERIALPREFIX + component.getIdentifier().toString(), material.id.toString());
        stack.setNbt(nbt);
    }

    default ArmoryMaterial getMaterial(ItemStack stack, MaterialItem component){
        NbtCompound nbt = stack.getNbt();
        if(nbt == null)
            return ArmoryMaterial.NONE;
        String materialId = nbt.getString(NBT_MATERIALPREFIX + component.getIdentifier().toString());
        return MaterialHandler.getMaterial(Identifier.tryParse(materialId));
    }

    default ArmoryMaterial getMaterial(ItemStack stack, int index){
        if(getComponents().size() <= index)
            return ArmoryMaterial.NONE;
        return getMaterial(stack, getComponents().get(index));
    }

    default boolean containsComponent(MaterialItem component){
        for(MaterialItem item : getComponents()){
            if(component.getIdentifier().equals(item.getIdentifier()))
                return true;
        }
        return false;
    }

    List<MaterialItem> getComponents();
}
