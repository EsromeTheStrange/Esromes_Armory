package net.esromethestrange.esromes_armory.item.material;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.component.ArmoryComponents;
import net.esromethestrange.esromes_armory.data.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.material.ArmoryMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public interface MaterialItem {
    List<MaterialItem> MATERIAL_ITEMS = new ArrayList<>();

    String NBT_MATERIAL = EsromesArmory.MOD_ID + ".material";

    ItemStack getStack(ArmoryMaterial material);
    void addMaterialTooltip(ItemStack stack, List<Text> tooltip, boolean partNameIncluded);

    default List<ItemStack> getDefaultStacks(){ return getDefaultStacks(false); }
    List<ItemStack> getDefaultStacks(boolean includeNone);
    List<ArmoryMaterial> getValidMaterials();
    ArmoryMaterial getDefaultMaterial();

    default void addMaterialTooltip(ItemStack stack, List<Text> tooltip){
        addMaterialTooltip(stack, tooltip, false);
    }

    default void setMaterial(ItemStack stack, ArmoryMaterial material){
        stack.set(ArmoryComponents.MATERIALS, material.id.toString());
    }

    default ArmoryMaterial getMaterial(ItemStack stack){
        if(stack.getItem() instanceof MaterialItem){
            String material = stack.get(ArmoryComponents.MATERIALS);
            if(material != null)
                return ArmoryMaterials.getMaterial(Identifier.tryParse(material));
        }
        else if(stack.getItem() instanceof PartBasedItem partBasedItem){
            return partBasedItem.getMaterial(stack, this);
        }
        return ArmoryMaterials.NONE;
    }

    default Identifier getRawIdentifier() {
        return Registries.ITEM.getId((Item)this);
    }
}
