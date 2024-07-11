package net.esromethestrange.esromes_armory.item.tools;

import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;

public class ArmoryShovelItem extends ArmoryMiningToolItem {
    protected static final MaterialItem SHOVEL_HEAD = (MaterialItem) ArmoryItems.SHOVEL_HEAD;

    public ArmoryShovelItem(Settings settings) {
        super(settings, ToolType.SHOVEL, COMPONENT_HANDLE, COMPONENT_BINDING, SHOVEL_HEAD);
    }

    @Override
    protected MaterialItem getHeadComponent() {
        return SHOVEL_HEAD;
    }
}
