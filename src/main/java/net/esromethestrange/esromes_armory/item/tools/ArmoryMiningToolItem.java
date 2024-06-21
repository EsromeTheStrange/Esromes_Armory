package net.esromethestrange.esromes_armory.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.esromethestrange.esromes_armory.EsromesArmory;
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
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ArmoryMiningToolItem extends MiningToolItem implements ComponentBasedItem {
    protected final ToolType toolType;
    protected List<MaterialItem> components = new ArrayList<>();

    public ArmoryMiningToolItem(Settings settings, ToolType toolType, MaterialItem... components) {
        super(1, 1, ToolMaterials.WOOD, toolType.effectiveBlocks, settings);
        this.toolType = toolType;
        this.components.addAll(Arrays.asList(components));
        COMPONENT_BASED_ITEMS.add(this);
    }

    public int getEnchantability(ItemStack stack) {
        return calculateEnchantability(stack);
    }

    public final int getMaxDamage(ItemStack stack){
        return calculateDurability(stack);
    }

    @Override
    public boolean isSuitableFor(ItemStack stack, BlockState state) {
        return MiningLevelManager.getRequiredMiningLevel(state) <= calculateMiningLevel(stack);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return state.isIn(this.toolType.effectiveBlocks) ? calculateMiningSpeed(stack) : 1.0f;
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

    protected abstract int calculateMiningLevel(ItemStack stack);
    protected abstract float calculateMiningSpeed(ItemStack stack);
    protected abstract int calculateDurability(ItemStack stack);

    protected abstract double calculateAttackDamage(ItemStack stack);
    protected abstract double calculateAttackSpeed(ItemStack stack);

    protected abstract int calculateEnchantability(ItemStack stack);

    @Override
    public List<MaterialItem> getComponents() {
        return components;
    }

    public static class ToolType{
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
