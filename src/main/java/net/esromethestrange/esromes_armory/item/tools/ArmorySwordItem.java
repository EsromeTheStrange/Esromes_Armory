package net.esromethestrange.esromes_armory.item.tools;

import net.esromethestrange.esromes_armory.item.ModItems;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.material.ArmoryMaterials;
import net.esromethestrange.esromes_armory.material.MaterialTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ArmorySwordItem extends ArmoryMiningToolItem {
    protected static final MaterialItem SWORD_GRIP = (MaterialItem) ModItems.SWORD_GRIP;
    protected static final MaterialItem SWORD_GUARD = (MaterialItem) ModItems.SWORD_GUARD;
    protected static final MaterialItem SWORD_BLADE = (MaterialItem) ModItems.SWORD_BLADE;

    public ArmorySwordItem(Item.Settings settings) {
        super(settings, ArmoryMiningToolItem.ToolType.SWORD, SWORD_GRIP, SWORD_GUARD, SWORD_BLADE);
    }

    @Override
    public List<ItemStack> getDefaultStacks(boolean includeNone) {
        List<ItemStack> defaultStacks = new ArrayList<>();
        if(includeNone){
            ItemStack stack = getDefaultStack();
            setMaterial(stack, SWORD_GUARD, ArmoryMaterials.NONE);
            setMaterial(stack, getHeadComponent(), ArmoryMaterials.NONE);
            defaultStacks.add(stack);
        }
        for(ArmoryMaterial material : MaterialTypes.METAL){
            ItemStack stack = getDefaultStack();
            for(MaterialItem materialItem : getComponents()){
                setMaterial(stack, materialItem, materialItem.getDefaultMaterial());
            }
            setMaterial(stack, SWORD_GUARD, material);
            setMaterial(stack, getHeadComponent(), material);
            defaultStacks.add(stack);
        }
        return defaultStacks;
    }

    @Override
    protected MaterialItem getHeadComponent() {
        return SWORD_BLADE;
    }
}
