package net.esromethestrange.esromes_armory.item.material;

import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.item.component.ArmoryComponents;
import net.esromethestrange.esromes_armory.item.component.ItemPartsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public interface PartBasedItem {
    List<PartBasedItem> PART_BASED_ITEMS = new ArrayList<>();

    List<MaterialItem> getParts();
    List<ItemStack> getDefaultStacks(boolean includeNone);
    RegistryEntry<Material> getPrimaryMaterial(ItemStack stack);

    default void setMaterial(ItemStack stack, MaterialItem part, RegistryEntry<Material> material){
        if(!containsPart(part)) return;

        ItemPartsComponent partsComponent = stack.get(ArmoryComponents.ITEM_PARTS);
        if(partsComponent == null)
            partsComponent = new ItemPartsComponent();

        stack.set(ArmoryComponents.ITEM_PARTS, partsComponent.withPart(part, material));
    }

    default int getFuelTime(ItemStack stack){
        if(getPrimaryMaterial(stack).value().fuelTimeMultiplier() == 0)
            return 0;
        int fuelTime = 0;
        for(MaterialItem part : getParts()){
            ItemStack partStack = part.getStack(getMaterial(stack, part));
            fuelTime += ((MaterialItem)partStack.getItem()).getFuelTime(partStack);
        }
        return fuelTime;
    }

    default RegistryEntry<Material> getMaterial(ItemStack stack, MaterialItem part){
        ItemPartsComponent partsComponent = stack.get(ArmoryComponents.ITEM_PARTS);
        if(partsComponent == null)
            return Materials.get(Materials.NONE);
        return partsComponent.getPart(part);
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
