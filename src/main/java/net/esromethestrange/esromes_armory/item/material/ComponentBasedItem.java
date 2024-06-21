package net.esromethestrange.esromes_armory.item.material;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;

public interface ComponentBasedItem {
    String NBT_MATERIALPREFIX = EsromesArmory.MOD_ID + ".materials.";

    List<MaterialItem> getComponents();

    default void setMaterial(ItemStack stack, MaterialItem component, ArmoryMaterial material){
        if(!containsComponent(component)) return;

        NbtCompound nbt = stack.getNbt();
        if(nbt == null)
            nbt = new NbtCompound();
        nbt.putString(NBT_MATERIALPREFIX + component.getRawIdentifier().toString(), material.id.toString());
        stack.setNbt(nbt);
    }

    default ArmoryMaterial getMaterial(ItemStack stack, MaterialItem component){
        NbtCompound nbt = stack.getNbt();
        if(nbt == null)
            return ArmoryMaterial.NONE;
        String materialId = nbt.getString(NBT_MATERIALPREFIX + component.getRawIdentifier().toString());
        return ArmoryMaterialHandler.getMaterial(Identifier.tryParse(materialId));
    }

    default boolean containsComponent(MaterialItem component){
        for(MaterialItem item : getComponents()){
            if(component.getRawIdentifier().equals(item.getRawIdentifier()))
                return true;
        }
        return false;
    }

    default Identifier getRawIdentifier() {
        return Registries.ITEM.getId((Item)this);
    }
}
