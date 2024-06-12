package net.esromethestrange.esromes_armory.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.MaterialHandler;
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.World;

public class ArmoryMiningToolItem extends MiningToolItem {
    public static final String NBTKEY_MATERIAL = "material";
    private final ToolType toolType;

    public ArmoryMiningToolItem(ToolType toolType, ToolMaterial material, Settings settings) {
        super(material.getAttackDamage(), 1, material, toolType.effectiveBlocks, settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        this.toolType = toolType;
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        super.onCraft(stack, world, player);
        NbtCompound nbt = new NbtCompound();
        nbt.putString(NBTKEY_MATERIAL, "esromes_armory.steel");
        stack.setNbt(nbt);
    }

    @Override public boolean isSuitableFor(BlockState state) { return false; }

    public final int getMaxDamage(ItemStack stack){
        return getArmoryMaterial(stack).durability;
    }

    @Override
    public boolean isSuitableFor(ItemStack stack, BlockState state) {
        ArmoryMaterial material = getArmoryMaterial(stack);

        return MiningLevelManager.getRequiredMiningLevel(state) <= material.miningLevel;
    }

    public ArmoryMaterial getArmoryMaterial(ItemStack stack){
        NbtCompound nbt = stack.getNbt();
        return MaterialHandler.getMaterial(nbt.getString(NBTKEY_MATERIAL));
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            ArmoryMaterial material = this.getArmoryMaterial(stack);

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
