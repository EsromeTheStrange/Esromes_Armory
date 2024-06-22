package net.esromethestrange.esromes_armory.item.tools;

import net.esromethestrange.esromes_armory.item.ModItems;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.minecraft.item.Item;

public class ArmorySwordItem extends ArmoryMiningToolItem {
    protected static final MaterialItem SWORD_BLADE = (MaterialItem) ModItems.SWORD_BLADE;

    public ArmorySwordItem(Item.Settings settings) {
        super(settings, ArmoryMiningToolItem.ToolType.SWORD, COMPONENT_HANDLE, COMPONENT_BINDING, SWORD_BLADE);
    }

    @Override
    protected MaterialItem getHeadComponent() {
        return SWORD_BLADE;
    }
}
