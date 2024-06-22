package net.esromethestrange.esromes_armory.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialHandler;
import net.esromethestrange.esromes_armory.data.ArmoryMaterials;
import net.esromethestrange.esromes_armory.data.MaterialTypes;
import net.esromethestrange.esromes_armory.item.ModItems;
import net.esromethestrange.esromes_armory.item.material.ComponentBasedItem;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.item.Vanishable;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ArmoryMiningToolItem extends MiningToolItem implements ComponentBasedItem, Vanishable {
    protected final ToolType toolType;
    protected List<MaterialItem> components = new ArrayList<>();

    protected static final MaterialItem COMPONENT_BINDING = (MaterialItem) ModItems.TOOL_BINDING;
    protected static final MaterialItem COMPONENT_HANDLE = (MaterialItem) ModItems.TOOL_HANDLE;

    public ArmoryMiningToolItem(Settings settings, ToolType toolType, MaterialItem... components) {
        super(1, 1, ToolMaterials.WOOD, toolType.effectiveBlocks, settings);
        this.toolType = toolType;
        this.components.addAll(Arrays.asList(components));
        COMPONENT_BASED_ITEMS.add(this);
    }

    @Override
    public Text getName(ItemStack stack) {
        MutableText materialText = Text.translatable(getMaterial(stack, getHeadComponent()).translatable_name);
        Text toolText = super.getName(stack);
        return materialText.append(toolText);
    }

    @Override
    public boolean isSuitableFor(ItemStack stack, BlockState state) {
        return MiningLevelManager.getRequiredMiningLevel(state) <= calculateMiningLevel(stack);
    }
    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return state.isIn(this.toolType.effectiveBlocks) ? calculateMiningSpeed(stack) : 1.0f;
    }
    public int getEnchantability(ItemStack stack) {
        return calculateEnchantability(stack);
    }
    public final int getMaxDamage(ItemStack stack){
        return calculateDurability(stack);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier",
                    calculateAttackDamage(stack), EntityAttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier",
                    calculateAttackSpeed(stack), EntityAttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(stack, slot);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        if(EsromesArmory.CONFIG.componentTooltips()){
            for(MaterialItem item : getComponents()){
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
        ArmoryMaterial headMaterial = getMaterial(stack, getHeadComponent());
        return headMaterial.miningLevel;
    }
    protected float calculateMiningSpeed(ItemStack stack) {
        ArmoryMaterial headMaterial = getMaterial(stack, getHeadComponent());
        return headMaterial.miningSpeed;
    }
    protected int calculateDurability(ItemStack stack) {
        ArmoryMaterial headMaterial = getMaterial(stack, getHeadComponent());
        return headMaterial.durability;
    }
    protected double calculateAttackDamage(ItemStack stack) {
        ArmoryMaterial headMaterial = getMaterial(stack, getHeadComponent());
        return (double)headMaterial.attackDamage * toolType.getAttackDamageMultiplier();
    }
    protected double calculateAttackSpeed(ItemStack stack) {
        ArmoryMaterial headMaterial = getMaterial(stack, getHeadComponent());
        return toolType.getAttackSpeed() +
                (double)headMaterial.attackSpeed * toolType.getAttackSpeedMultiplier();
    }
    protected int calculateEnchantability(ItemStack stack) {
        ArmoryMaterial headMaterial = getMaterial(stack, getHeadComponent());
        return headMaterial.enchantability;
    }

    protected abstract MaterialItem getHeadComponent();

    @Override
    public List<ItemStack> getDefaultStacks() {
        List<ItemStack> defaultStacks = new ArrayList<>();
        for(Identifier id : MaterialTypes.METAL){
            ItemStack stack = getDefaultStack();
            setMaterial(stack, getHeadComponent(), ArmoryMaterialHandler.getMaterial(id));
            setMaterial(stack, COMPONENT_BINDING, ArmoryMaterialHandler.getMaterial(ArmoryMaterials.STRING.id));
            setMaterial(stack, COMPONENT_HANDLE, ArmoryMaterialHandler.getMaterial(ArmoryMaterials.OAK.id));
            defaultStacks.add(stack);
        }
        return defaultStacks;
    }

    @Override
    public List<MaterialItem> getComponents() {
        return components;
    }

    public static class ToolType{
        public static final ToolType SHOVEL = new ToolType(1.1f, -2.8f,0.5f, BlockTags.SHOVEL_MINEABLE);
        public static final ToolType PICKAXE = new ToolType(0.5f, -2.8f,0.5f, BlockTags.PICKAXE_MINEABLE);
        public static final ToolType AXE = new ToolType(0.5f, -2.8f,0.5f, BlockTags.AXE_MINEABLE);
        public static final ToolType HOE = new ToolType(0.5f, -2.8f,0.5f, BlockTags.HOE_MINEABLE);
        public static final ToolType SWORD = new ToolType(0.5f, -2.8f,0.5f, BlockTags.SWORD_EFFICIENT);

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
