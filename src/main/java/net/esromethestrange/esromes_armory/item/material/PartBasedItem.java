package net.esromethestrange.esromes_armory.item.material;

import net.esromethestrange.esromes_armory.data.component.ArmoryComponents;
import net.esromethestrange.esromes_armory.data.component.ItemPartsComponent;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
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
    Material getPrimaryMaterial(ItemStack stack);

    default void setMaterial(ItemStack stack, MaterialItem part, Material material){
        if(!containsPart(part)) return;

        ItemPartsComponent partsComponent = stack.get(ArmoryComponents.ITEM_PARTS);
        if(partsComponent == null)
            partsComponent = new ItemPartsComponent();

        stack.set(ArmoryComponents.ITEM_PARTS, partsComponent.withPart(part, material));
    }

    default Material getMaterial(ItemStack stack, MaterialItem part){
        ItemPartsComponent partsComponent = stack.get(ArmoryComponents.ITEM_PARTS);
        if(partsComponent == null)
            return Materials.NONE;

        Identifier materialId = partsComponent.getPart(part);
        return materialId == null ? Materials.NONE : Materials.getMaterial(materialId);
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
