package net.esromethestrange.esromes_armory.item.material;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.material.ArmoryMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public interface ComponentBasedItem {
    List<ComponentBasedItem> COMPONENT_BASED_ITEMS = new ArrayList<>();

    String NBT_MATERIALS_PREFIX = EsromesArmory.MOD_ID + ".materials.";

    List<MaterialItem> getComponents();
    default List<ItemStack> getDefaultStacks() { return getDefaultStacks(false); }
    List<ItemStack> getDefaultStacks(boolean includeNone);
    ArmoryMaterial getPrimaryMaterial(ItemStack stack);

    default void setMaterial(ItemStack stack, MaterialItem component, ArmoryMaterial material){
        if(!containsComponent(component)) return;

        NbtCompound nbt = stack.getNbt();
        if(nbt == null)
            nbt = new NbtCompound();
        nbt.putString(NBT_MATERIALS_PREFIX + component.getRawIdentifier().toString(), material.id.toString());
        stack.setNbt(nbt);
    }

    default ArmoryMaterial getMaterial(ItemStack stack, MaterialItem component){
        NbtCompound nbt = stack.getNbt();
        if(nbt == null)
            return ArmoryMaterials.NONE;
        String materialId = nbt.getString(NBT_MATERIALS_PREFIX + component.getRawIdentifier().toString());
        return ArmoryMaterials.getMaterial(Identifier.tryParse(materialId));
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
