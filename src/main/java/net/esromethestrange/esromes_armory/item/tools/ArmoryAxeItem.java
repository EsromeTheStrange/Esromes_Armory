package net.esromethestrange.esromes_armory.item.tools;

import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;

public class ArmoryAxeItem extends ArmoryMiningToolItem{
    protected static final MaterialItem AXE_HEAD = (MaterialItem) ArmoryItems.AXE_HEAD;

    public ArmoryAxeItem(Settings settings) {
        super(settings, ToolType.AXE, COMPONENT_HANDLE, COMPONENT_BINDING, AXE_HEAD);
    }

    @Override
    protected MaterialItem getHeadComponent() {
        return AXE_HEAD;
    }
}
