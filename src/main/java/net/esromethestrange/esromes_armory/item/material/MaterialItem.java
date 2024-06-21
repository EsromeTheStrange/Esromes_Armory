package net.esromethestrange.esromes_armory.item.material;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public interface MaterialItem {
    String NBT_MATERIAL = EsromesArmory.MOD_ID + ".material";

    ItemStack getStack(ArmoryMaterial material);
    void addMaterialTooltip(ItemStack stack, List<Text> tooltip, boolean componentNameIncluded);

    default void addMaterialTooltip(ItemStack stack, List<Text> tooltip){
        addMaterialTooltip(stack, tooltip, false);
    }

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

        if(stack.getItem() instanceof MaterialItem){
            String materialId = nbt.getString(NBT_MATERIAL);
            return ArmoryMaterialHandler.getMaterial(Identifier.tryParse(materialId));
        }
        if(stack.getItem() instanceof ComponentBasedItem componentBasedItem){
            return componentBasedItem.getMaterial(stack, this);
        }
        return ArmoryMaterial.NONE;
    }

    default Identifier getRawIdentifier() {
        return Registries.ITEM.getId((Item)this);
    }
}
