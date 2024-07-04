package net.esromethestrange.esromes_armory.item.material;

import net.esromethestrange.esromes_armory.item.component.ArmoryComponents;
import net.esromethestrange.esromes_armory.item.component.ItemPartsComponent;
import net.esromethestrange.esromes_armory.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.material.ArmoryMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public interface PartBasedItem {
    List<PartBasedItem> PART_BASED_ITEMS = new ArrayList<>();

    List<MaterialItem> getParts();
    default List<ItemStack> getDefaultStacks() { return getDefaultStacks(false); }
    List<ItemStack> getDefaultStacks(boolean includeNone);
    ArmoryMaterial getPrimaryMaterial(ItemStack stack);

    default void setMaterial(ItemStack stack, MaterialItem part, ArmoryMaterial material){
        if(!containsPart(part)) return;

        ItemPartsComponent partsComponent = stack.get(ArmoryComponents.ITEM_PARTS);
        if(partsComponent == null)
            partsComponent = new ItemPartsComponent();

        stack.set(ArmoryComponents.ITEM_PARTS, partsComponent.withPart(part, material));
    }

    default ArmoryMaterial getMaterial(ItemStack stack, MaterialItem part){
        ItemPartsComponent partsComponent = stack.get(ArmoryComponents.ITEM_PARTS);
        if(partsComponent == null)
            return ArmoryMaterials.NONE;

        Identifier materialId = partsComponent.getPart(part);
        return materialId == null ? ArmoryMaterials.NONE : ArmoryMaterials.getMaterial(materialId);
    }

    default boolean containsPart(MaterialItem component){
        for(MaterialItem item : getParts()){
            if(component.getRawIdentifier().equals(item.getRawIdentifier()))
                return true;
        }
        return false;
    }

    default Identifier getRawIdentifier() {
        return Registries.ITEM.getId((Item)this);
    }
}
