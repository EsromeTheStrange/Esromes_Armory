package net.esromethestrange.esromes_armory.mixin;

import net.esromethestrange.esromes_armory.client.ArmoryColorProvider;
import net.esromethestrange.esromes_armory.client.model.PartBasedItemModel;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.item.material.PartBasedItem;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemColors.class)
public class EsromesArmoryItemColorsMixin {
    @Inject(method = "getColor", at = @At("RETURN"), cancellable = true)
    private void getColor(ItemStack stack, int tintIndex, CallbackInfoReturnable<Integer> cir){
        if(
                !(stack.getItem() instanceof PartBasedItem) ||
                !(PartBasedItemModel.cacheStack.getItem() instanceof MaterialItem materialItem)
        )
            return;

        cir.setReturnValue(ArmoryColorProvider.getColor(materialItem, PartBasedItemModel.cacheStack, tintIndex));
    }
}
