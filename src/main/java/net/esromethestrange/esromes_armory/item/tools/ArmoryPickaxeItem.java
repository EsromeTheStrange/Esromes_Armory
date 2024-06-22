package net.esromethestrange.esromes_armory.item.tools;

import net.esromethestrange.esromes_armory.item.ModItems;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;

public class ArmoryPickaxeItem extends ArmoryMiningToolItem {
    protected static final MaterialItem PICKAXE_HEAD = (MaterialItem) ModItems.PICKAXE_HEAD;

    public ArmoryPickaxeItem(Settings settings) {
        super(settings, ToolType.PICKAXE, COMPONENT_HANDLE, COMPONENT_BINDING, PICKAXE_HEAD);
    }

    @Override
    protected MaterialItem getHeadComponent() {
        return PICKAXE_HEAD;
    }
}
