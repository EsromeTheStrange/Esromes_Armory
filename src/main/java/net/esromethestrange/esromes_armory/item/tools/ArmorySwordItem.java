package net.esromethestrange.esromes_armory.item.tools;

import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.MaterialTypes;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class ArmorySwordItem extends ArmoryMiningToolItem {
    protected static final MaterialItem SWORD_GRIP = (MaterialItem) ArmoryItems.SWORD_GRIP;
    protected static final MaterialItem SWORD_GUARD = (MaterialItem) ArmoryItems.SWORD_GUARD;
    protected static final MaterialItem SWORD_BLADE = (MaterialItem) ArmoryItems.SWORD_BLADE;

    public ArmorySwordItem(Item.Settings settings) {
        super(settings, ArmoryMiningToolItem.ToolType.SWORD, SWORD_GRIP, SWORD_GUARD, SWORD_BLADE);
    }

    @Override
    protected ItemStack createDefaultStack(RegistryEntry<Material> material){
        ItemStack stack = super.createDefaultStack(material);
        setMaterial(stack, SWORD_GUARD, material);
        return stack;
    }

    @Override protected MaterialItem getHeadComponent() {
        return SWORD_BLADE;
    }
    @Override protected MaterialItem getBindingComponent() { return SWORD_GUARD; }
    @Override protected MaterialItem getHandleComponent() { return SWORD_GRIP; }
}
