package net.esromethestrange.esromes_armory.item.tools;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.item.material.PartBasedItem;
import net.esromethestrange.esromes_armory.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.material.ArmoryMaterials;
import net.esromethestrange.esromes_armory.material.MaterialTypes;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ArmoryMiningToolItem extends MiningToolItem implements PartBasedItem {
    protected final ToolType toolType;
    protected List<MaterialItem> components = new ArrayList<>();

    protected static final MaterialItem COMPONENT_BINDING = (MaterialItem) ArmoryItems.TOOL_BINDING;
    protected static final MaterialItem COMPONENT_HANDLE = (MaterialItem) ArmoryItems.TOOL_HANDLE;

    public ArmoryMiningToolItem(Settings settings, ToolType toolType, MaterialItem... components) {
        super(ToolMaterials.WOOD, toolType.effectiveBlocks, settings);
        this.toolType = toolType;
        this.components.addAll(Arrays.asList(components));
        PART_BASED_ITEMS.add(this);
    }

    @Override
    public Text getName(ItemStack stack) {
        MutableText materialText = Text.translatable(getMaterial(stack, getHeadComponent()).translatable_name);
        Text toolText = super.getName(stack);
        return materialText.append(toolText);
    }

    //TODO Is Suitable For
//    @Override
//    public boolean isSuitableFor(ItemStack stack, BlockState state) {
//        return MiningLevelManager.getRequiredMiningLevel(state) <= calculateMiningLevel(stack);
//    }
    //TODO Mining Speed
//    @Override
//    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
//        return state.isIn(this.toolType.effectiveBlocks) ? calculateMiningSpeed(stack) : 1.0f;
//    }
    public int getEnchantability(ItemStack stack) {
        return calculateEnchantability(stack);
    }
    public final int getMaxDamage(ItemStack stack){
        return calculateDurability(stack);
    }

    //TODO Entity Attribute Modifiers
//    @Override
//    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
//        if (slot == EquipmentSlot.MAINHAND) {
//            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
//            builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier",
//                    calculateAttackDamage(stack), EntityAttributeModifier.Operation.ADDITION));
//            builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier",
//                    calculateAttackSpeed(stack), EntityAttributeModifier.Operation.ADDITION));
//            return builder.build();
//        }
//        return super.getAttributeModifiers(stack, slot);
//    }


    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        if(EsromesArmory.CONFIG.componentTooltips()){
            for(MaterialItem item : getParts()){
                item.addMaterialTooltip(item.getStack(getMaterial(stack, item)), tooltip, true);
            }
        }
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round(13.0f - (float)stack.getDamage() * 13.0f / (float)getMaxDamage(stack));
    }
    @Override
    public int getItemBarColor(ItemStack stack) {
        float f = Math.max(0.0f, ((float)getMaxDamage(stack) - (float)stack.getDamage()) / (float)getMaxDamage(stack));
        return MathHelper.hsvToRgb(f / 3.0f, 1.0f, 1.0f);
    }

    protected int calculateMiningLevel(ItemStack stack) {
        ArmoryMaterial headMaterial = getPrimaryMaterial(stack);
        return headMaterial.miningLevel;
    }
    protected float calculateMiningSpeed(ItemStack stack) {
        ArmoryMaterial headMaterial = getPrimaryMaterial(stack);
        return headMaterial.miningSpeed;
    }
    protected int calculateDurability(ItemStack stack) {
        ArmoryMaterial headMaterial = getPrimaryMaterial(stack);
        ArmoryMaterial bindingMaterial = getBindingMaterial(stack);
        ArmoryMaterial handleMaterial = getHandleMaterial(stack);
        return (int) (
                headMaterial.durability * 25 +
                bindingMaterial.durability * 50 +
                handleMaterial.durability * 15
        );
    }
    protected double calculateAttackDamage(ItemStack stack) {
        ArmoryMaterial headMaterial = getPrimaryMaterial(stack);
        return (double)headMaterial.attackDamage * toolType.getAttackDamageMultiplier();
    }
    protected double calculateAttackSpeed(ItemStack stack) {
        ArmoryMaterial bindingMaterial = getBindingMaterial(stack);
        return toolType.getAttackSpeed() +
                (double)bindingMaterial.attackSpeed * toolType.getAttackSpeedMultiplier();
    }
    protected int calculateEnchantability(ItemStack stack) {
        ArmoryMaterial headMaterial = getMaterial(stack, getHeadComponent());
        return headMaterial.enchantability;
    }

    @Override
    public ArmoryMaterial getPrimaryMaterial(ItemStack stack) {
        return getMaterial(stack, getHeadComponent());
    }
    public ArmoryMaterial getBindingMaterial(ItemStack stack){
        return getMaterial(stack, getBindingComponent());
    }
    public ArmoryMaterial getHandleMaterial(ItemStack stack){
        return getMaterial(stack, getHandleComponent());
    }

    protected abstract MaterialItem getHeadComponent();
    protected MaterialItem getBindingComponent() { return COMPONENT_BINDING; }
    protected MaterialItem getHandleComponent() {return COMPONENT_HANDLE; }

    @Override
    public List<ItemStack> getDefaultStacks(boolean includeNone) {
        List<ItemStack> defaultStacks = new ArrayList<>();
        if(includeNone){
            ItemStack stack = getDefaultStack();
            setMaterial(stack, getHeadComponent(), ArmoryMaterials.NONE);
            defaultStacks.add(stack);
        }
        for(ArmoryMaterial material : MaterialTypes.METAL){
            ItemStack stack = getDefaultStack();
            for(MaterialItem materialItem : getParts()){
                setMaterial(stack, materialItem, materialItem.getDefaultMaterial());
            }
            setMaterial(stack, getHeadComponent(), material);
            defaultStacks.add(stack);
        }
        return defaultStacks;
    }

    @Override
    public List<MaterialItem> getParts() {
        return components;
    }

    public static class ToolType{
        public static final ToolType SHOVEL = new ToolType(0.5f, -3.0f,0.25f, BlockTags.SHOVEL_MINEABLE);
        public static final ToolType PICKAXE = new ToolType(0.5f, -2.8f,0.25f, BlockTags.PICKAXE_MINEABLE);
        public static final ToolType AXE = new ToolType(1.5f, -3.1f,0.1f, BlockTags.AXE_MINEABLE);
        public static final ToolType HOE = new ToolType(0.0f, -1.0f,0.75f, BlockTags.HOE_MINEABLE);
        public static final ToolType SWORD = new ToolType(1.0f, -2.4f,0.25f, BlockTags.SWORD_EFFICIENT);

        private final float attackDamageMultiplier;
        private final float attackSpeed;
        private final float attackSpeedMultiplier;
        private final TagKey<Block> effectiveBlocks;

        ToolType(float attackDamageMultiplier, float attackSpeed, float attackSpeedMultiplier, TagKey<Block> effectiveBlocks){
            this.attackDamageMultiplier = attackDamageMultiplier;
            this.attackSpeed = attackSpeed;
            this.attackSpeedMultiplier = attackSpeedMultiplier;
            this.effectiveBlocks = effectiveBlocks;
        }

        public float getAttackDamageMultiplier() { return this.attackDamageMultiplier; }
        public float getAttackSpeed() { return this.attackSpeed; }
        public float getAttackSpeedMultiplier() { return this.attackSpeedMultiplier; }
        public final TagKey<Block> getEffectiveBlocks() { return this.effectiveBlocks; }
    }
}
