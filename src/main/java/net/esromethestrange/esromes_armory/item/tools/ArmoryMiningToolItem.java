package net.esromethestrange.esromes_armory.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.MaterialHandler;
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArmoryMiningToolItem extends MiningToolItem {
    public static final String NBTKEY_MATERIAL = "material";
    private final ToolType toolType;

    public ArmoryMiningToolItem(ToolType toolType, ToolMaterial material, Settings settings) {
        super(material.getAttackDamage(), 1, material, toolType.effectiveBlocks, settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        this.toolType = toolType;
    }

    public static int getEnchantability(ItemStack stack) {
        return getArmoryMaterial(stack).enchantability;
    }

    public final int getMaxDamage(ItemStack stack){
        return getArmoryMaterial(stack).durability;
    }

    @Override public boolean isSuitableFor(BlockState state) { return false; }
    @Override
    public boolean isSuitableFor(ItemStack stack, BlockState state) {
        return MiningLevelManager.getRequiredMiningLevel(state) <= getArmoryMaterial(stack).miningLevel;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return state.isIn(this.toolType.effectiveBlocks) ? getArmoryMaterial(stack).miningSpeed : 1.0f;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            ArmoryMaterial material = getArmoryMaterial(stack);

            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier",
                    (double)material.attackDamage * toolType.attackDamageMultiplier,
                    EntityAttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier",
                    (double)material.attackSpeed * toolType.attackSpeedMultiplier,
                    EntityAttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(stack, slot);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        String materialId = getArmoryMaterial(stack).translatable_name;
        Text materialText = Text.translatable(materialId);
        tooltip.addAll(materialText.getWithStyle(Style.EMPTY.withColor(getArmoryMaterial(stack).color)));

        if(EsromesArmory.CONFIG.developerMode()){
            if (stack.getNbt() != null) {
                Text debugText = Text.literal("Material Id: "+stack.getNbt().getString(NBTKEY_MATERIAL));
                tooltip.addAll(debugText.getWithStyle(Style.EMPTY.withColor(0xff00ff)));
            }
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    public static ArmoryMaterial getArmoryMaterial(ItemStack stack){
        NbtCompound nbt = stack.getNbt();
        if(nbt != null){
            return MaterialHandler.getMaterial(Identifier.tryParse(nbt.getString(NBTKEY_MATERIAL)));
        }
        return ArmoryMaterial.NONE;
    }

    public enum ToolType{
        PICKAXE(0.25f, 0.75f, BlockTags.PICKAXE_MINEABLE);

        private final float attackDamageMultiplier;
        private final float attackSpeedMultiplier;
        private final TagKey<Block> effectiveBlocks;

        ToolType(float attackDamageMultiplier, float attackSpeedMultiplier, TagKey<Block> effectiveBlocks){
            this.attackDamageMultiplier = attackDamageMultiplier;
            this.attackSpeedMultiplier = attackSpeedMultiplier;
            this.effectiveBlocks = effectiveBlocks;
        }
    }
}
