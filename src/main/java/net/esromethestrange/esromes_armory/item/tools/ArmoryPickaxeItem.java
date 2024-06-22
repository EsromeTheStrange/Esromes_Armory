package net.esromethestrange.esromes_armory.item.tools;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialHandler;
import net.esromethestrange.esromes_armory.data.ArmoryMaterials;
import net.esromethestrange.esromes_armory.data.MaterialTypes;
import net.esromethestrange.esromes_armory.item.ModItems;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ArmoryPickaxeItem extends ArmoryMiningToolItem {
    private static final MaterialItem COMPONENT_HEAD = (MaterialItem) ModItems.PICKAXE_HEAD;
    private static final MaterialItem COMPONENT_HANDLE = (MaterialItem) ModItems.TOOL_HANDLE;
    public static final ToolType PICKAXE_TOOL_TYPE = new ToolType(0.5f, -2.8f,0.5f, BlockTags.PICKAXE_MINEABLE);

    public ArmoryPickaxeItem(Settings settings) {
        super(settings, PICKAXE_TOOL_TYPE, COMPONENT_HANDLE, COMPONENT_HEAD);
    }

    @Override
    public Text getName(ItemStack stack) {
        MutableText materialText = Text.translatable(getMaterial(stack, COMPONENT_HEAD).translatable_name);
        Text toolText = super.getName(stack);
        return materialText.append(toolText);
    }

    @Override
    public List<ItemStack> getDefaultStacks() {
        List<ItemStack> defaultStacks = new ArrayList<>();
        for(Identifier id : MaterialTypes.METAL){
            ItemStack stack = getDefaultStack();
            setMaterial(stack, COMPONENT_HEAD, ArmoryMaterialHandler.getMaterial(id));
            setMaterial(stack, COMPONENT_HANDLE, ArmoryMaterialHandler.getMaterial(ArmoryMaterials.OAK.id));
            defaultStacks.add(stack);
        }
        return defaultStacks;
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
