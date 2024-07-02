package net.esromethestrange.esromes_armory.item.material;

import net.esromethestrange.esromes_armory.item.component.ArmoryComponents;
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
        stack.set(ArmoryComponents.MATERIALS, material.id.toString());
    }

    default ArmoryMaterial getMaterial(ItemStack stack, MaterialItem part){
        String materialId = stack.get(ArmoryComponents.MATERIALS);
        //TODO Components
        return materialId == null ? ArmoryMaterials.NONE : ArmoryMaterials.getMaterial(Identifier.tryParse(materialId));
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
