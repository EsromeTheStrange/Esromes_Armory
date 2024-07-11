package net.esromethestrange.esromes_armory.item.tools;

import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.minecraft.item.Item;

public class ArmoryHoeItem extends ArmoryMiningToolItem {
    protected static final MaterialItem HOE_HEAD = (MaterialItem) ArmoryItems.HOE_HEAD;

    public ArmoryHoeItem(Item.Settings settings) {
        super(settings, ToolType.HOE, COMPONENT_HANDLE, COMPONENT_BINDING, HOE_HEAD);
    }

    @Override
    protected MaterialItem getHeadComponent() {
        return HOE_HEAD;
    }
}
