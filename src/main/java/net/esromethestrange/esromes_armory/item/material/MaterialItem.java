package net.esromethestrange.esromes_armory.item.material;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.material.ArmoryMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public interface MaterialItem {
    List<MaterialItem> MATERIAL_ITEMS = new ArrayList<>();

    String NBT_MATERIAL = EsromesArmory.MOD_ID + ".material";

    ItemStack getStack(ArmoryMaterial material);
    void addMaterialTooltip(ItemStack stack, List<Text> tooltip, boolean componentNameIncluded);
    List<ItemStack> getDefaultStacks();
    List<ArmoryMaterial> getValidMaterials();

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
            return ArmoryMaterials.NONE;

        if(stack.getItem() instanceof MaterialItem){
            String materialId = nbt.getString(NBT_MATERIAL);
            return ArmoryMaterials.getMaterial(Identifier.tryParse(materialId));
        }
        if(stack.getItem() instanceof ComponentBasedItem componentBasedItem){
            return componentBasedItem.getMaterial(stack, this);
        }
        return ArmoryMaterials.NONE;
    }

    default Identifier getRawIdentifier() {
        return Registries.ITEM.getId((Item)this);
    }
}
