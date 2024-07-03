package net.esromethestrange.esromes_armory.mixin;

import net.esromethestrange.esromes_armory.item.tools.ArmoryMiningToolItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
abstract class EsromesArmoryItemStackMixin {
    @Shadow public abstract Item getItem();

    //TODO Replace this with components system.
    @Inject(method = "getMaxDamage", at = @At("HEAD"), cancellable = true)
    private void esromes_armory$onGetMaxDamage(CallbackInfoReturnable<Integer> info) {
        if (this.getItem() instanceof ArmoryMiningToolItem){
            info.setReturnValue(((ArmoryMiningToolItem)this.getItem()).getMaxDamage((ItemStack) (Object) this));
        }
    }
}