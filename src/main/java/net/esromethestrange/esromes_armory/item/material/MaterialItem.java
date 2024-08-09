package net.esromethestrange.esromes_armory.item.material;

import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.item.component.ArmoryComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public interface MaterialItem {
    List<MaterialItem> MATERIAL_ITEMS = new ArrayList<>();

    ItemStack getStack(RegistryEntry<Material> material);
    void addMaterialTooltip(ItemStack stack, List<Text> tooltip, boolean partNameIncluded);

    List<ItemStack> getDefaultStacks();
    List<RegistryEntry<Material>> getValidMaterials();
    int getBaseFuelTime();
    void setupMaterials(RegistryWrapper.Impl<Material> materialRegistry);

    default int getFuelTime(ItemStack stack){
        return getBaseFuelTime() * getMaterial(stack).value().fuelTimeMultiplier();
    }

    default void addMaterialTooltip(ItemStack stack, List<Text> tooltip){
        addMaterialTooltip(stack, tooltip, false);
    }

    default void setMaterial(ItemStack stack, RegistryEntry<Material> material){
        stack.set(ArmoryComponents.MATERIAL, material);
    }

    default RegistryEntry<Material> getMaterial(ItemStack stack){
        RegistryEntry<Material> material = Materials.get(Materials.NONE);
        if(stack.getComponents().contains(ArmoryComponents.MATERIAL))
            material = stack.get(ArmoryComponents.MATERIAL);
        if(stack.getComponents().contains(ArmoryComponents.ITEM_PARTS))
            material = stack.get(ArmoryComponents.ITEM_PARTS).getPart(this);
        return material;
    }

    default RegistryEntry<Material> getDefaultMaterial(){
        for(RegistryEntry<Material> material : getValidMaterials())
            if(!material.matchesKey(Materials.NONE))
                return material;
        return getValidMaterials().get(0);
    }

    default Identifier getRawIdentifier() {
        return Registries.ITEM.getId((Item)this);
    }
}
