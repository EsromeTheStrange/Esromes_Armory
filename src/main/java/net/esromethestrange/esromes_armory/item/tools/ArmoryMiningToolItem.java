package net.esromethestrange.esromes_armory.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.item.ModItems;
import net.esromethestrange.esromes_armory.item.material.ComponentBasedItem;
import net.esromethestrange.esromes_armory.item.material.ComponentItem;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArmoryMiningToolItem extends MiningToolItem implements ComponentBasedItem {
    private final ToolType toolType;
    private List<MaterialItem> components = new ArrayList<>();

    public ArmoryMiningToolItem(ToolType toolType, ToolMaterial material, Settings settings, MaterialItem... components) {
        super(material.getAttackDamage(), 1, material, toolType.effectiveBlocks, settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        this.toolType = toolType;
        this.components.addAll(Arrays.asList(components));
    }

    public static int getEnchantability(ItemStack stack) {
        return 0;//TODO ((ArmoryMiningToolItem)stack.getItem()).getMaterial(stack).enchantability;
    }

    public final int getMaxDamage(ItemStack stack){
        return 0;//TODO getMaterial(stack).durability;
    }

    @Override
    public Text getName() {
        return super.getName();
    }

    @Override
    public Text getName(ItemStack stack) {
        ArmoryMaterial material = getMaterial(stack, 0);
        String key = "item." + material.modId + "." +
                material.materialName + "_" +
                Registries.ITEM.getId(stack.getItem()).getPath();
        return Text.translatable(key);
    }

    @Override public boolean isSuitableFor(BlockState state) { return false; }
    @Override
    public boolean isSuitableFor(ItemStack stack, BlockState state) {
        return false;//TODO MiningLevelManager.getRequiredMiningLevel(state) <= getMaterial(stack).miningLevel;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return 0;//TODO state.isIn(this.toolType.effectiveBlocks) ? getMaterial(stack).miningSpeed : 1.0f;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            ArmoryMaterial material = ArmoryMaterial.NONE;//TODO getMaterial(stack);

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
        super.appendTooltip(stack, world, tooltip, context);

        String materialId = "";//TODO getMaterial(stack).translatable_name;
        Text materialText = Text.translatable(materialId);
        //TODO tooltip.addAll(materialText.getWithStyle(Style.EMPTY.withColor(getMaterial(stack).color)));

        if(EsromesArmory.CONFIG.developerMode()){
            //TODO Add Developer Tooltip
        }
    }

    @Override
    public List<MaterialItem> getComponents() {
        return components;
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
