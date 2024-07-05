package net.esromethestrange.esromes_armory.item.tools;

import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.data.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.material.ArmoryMaterials;
import net.esromethestrange.esromes_armory.data.material.MaterialTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
    public List<ItemStack> getDefaultStacks(boolean includeNone) {
        List<ItemStack> defaultStacks = new ArrayList<>();
        if(includeNone){
            ItemStack stack = getDefaultStack();
            setMaterial(stack, SWORD_GUARD, ArmoryMaterials.NONE);
            setMaterial(stack, getHeadComponent(), ArmoryMaterials.NONE);
            setupComponents(stack);
            defaultStacks.add(stack);
        }
        for(ArmoryMaterial material : MaterialTypes.METAL){
            ItemStack stack = getDefaultStack();
            for(MaterialItem materialItem : getParts()){
                setMaterial(stack, materialItem, materialItem.getDefaultMaterial());
            }
            setMaterial(stack, SWORD_GUARD, material);
            setMaterial(stack, getHeadComponent(), material);
            setupComponents(stack);
            defaultStacks.add(stack);
        }
        return defaultStacks;
    }

    @Override
    protected int calculateDurability(ItemStack stack) {
        ArmoryMaterial headMaterial = getPrimaryMaterial(stack);
        ArmoryMaterial bindingMaterial = getBindingMaterial(stack);
        ArmoryMaterial handleMaterial = getHandleMaterial(stack);
        return (int) (
                headMaterial.durability * 5 +
                        bindingMaterial.durability * 5 +
                        handleMaterial.durability * 5
        ) * 10;
    }

    @Override protected MaterialItem getHeadComponent() {
        return SWORD_BLADE;
    }
    @Override protected MaterialItem getBindingComponent() { return SWORD_GUARD; }
    @Override protected MaterialItem getHandleComponent() { return SWORD_GRIP; }
}
