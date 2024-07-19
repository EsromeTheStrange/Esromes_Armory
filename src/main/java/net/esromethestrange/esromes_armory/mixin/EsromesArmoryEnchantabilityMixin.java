package net.esromethestrange.esromes_armory.mixin;

import net.esromethestrange.esromes_armory.item.tools.ArmoryMiningToolItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentHelper.class)
public class EsromesArmoryEnchantabilityMixin {
    @Redirect(method = "calculateRequiredExperienceLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getEnchantability()I"))
    private static int calculateRequiredExperienceLevelRedirect(Item item, Random random, int slotIndex, int bookshelfCount, ItemStack stack) {
        return getEnchantabilityOf(item, stack);
    }
    @Redirect(method = "generateEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getEnchantability()I"))
    private static int generateEnchantmentsRedirect(Item item, Random random, ItemStack stack) {
        return getEnchantabilityOf(item, stack);
    }

    @Unique
    private static int getEnchantabilityOf(Item item, ItemStack stack){
        if(item instanceof ArmoryMiningToolItem){
            return ((ArmoryMiningToolItem)item).getEnchantability(stack);
        }
        return item.getEnchantability();
    }
}
