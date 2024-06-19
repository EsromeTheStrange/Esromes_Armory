package net.esromethestrange.esromes_armory.item.tools;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.item.ModItems;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;

public class ArmoryPickaxeItem extends ArmoryMiningToolItem {
    private static final MaterialItem COMPONENT_HEAD = (MaterialItem) ModItems.PICKAXE_HEAD;
    private static final MaterialItem COMPONENT_HANDLE = (MaterialItem) ModItems.TOOL_HANDLE;
    public static final ToolType PICKAXE_TOOL_TYPE = new ToolType(0.5f, -2.8f,0.5f, BlockTags.PICKAXE_MINEABLE);

    public ArmoryPickaxeItem(Settings settings) {
        super(settings, PICKAXE_TOOL_TYPE, COMPONENT_HEAD, COMPONENT_HANDLE);
    }

    @Override
    public Text getName(ItemStack stack) {
        String key = "item." + EsromesArmory.MOD_ID + ".";
        key += getMaterial(stack, COMPONENT_HEAD).materialName;
        key += "_" + Registries.ITEM.getId(this).getPath();
        return Text.translatable(key);
    }

    @Override
    protected int calculateMiningLevel(ItemStack stack) {
        ArmoryMaterial headMaterial = getMaterial(stack, COMPONENT_HEAD);
        return headMaterial.miningLevel;
    }

    @Override
    protected float calculateMiningSpeed(ItemStack stack) {
        ArmoryMaterial headMaterial = getMaterial(stack, COMPONENT_HEAD);
        return headMaterial.miningSpeed;
    }

    @Override
    protected int calculateDurability(ItemStack stack) {
        ArmoryMaterial headMaterial = getMaterial(stack, COMPONENT_HEAD);
        return headMaterial.durability;
    }

    @Override
    protected double calculateAttackDamage(ItemStack stack) {
        ArmoryMaterial headMaterial = getMaterial(stack, COMPONENT_HEAD);
        return (double)headMaterial.attackDamage * toolType.getAttackDamageMultiplier();
    }

    @Override
    protected double calculateAttackSpeed(ItemStack stack) {
        ArmoryMaterial headMaterial = getMaterial(stack, COMPONENT_HEAD);
        return toolType.getAttackSpeed() +
                (double)headMaterial.attackSpeed * toolType.getAttackSpeedMultiplier();
    }

    @Override
    protected int calculateEnchantability(ItemStack stack) {
        ArmoryMaterial headMaterial = getMaterial(stack, COMPONENT_HEAD);
        return headMaterial.enchantability;
    }
}
