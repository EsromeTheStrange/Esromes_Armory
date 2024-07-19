package net.esromethestrange.esromes_armory.mixin;

import net.esromethestrange.esromes_armory.util.MaterialHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceScreenHandler.class)
public class EsromesArmoryAbstractFurnaceScreenHandlerMixin {
    @Inject(method = "isFuel", at = @At(value = "RETURN"), cancellable = true)
    protected void esromes_armory$onIsFuel(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir){
        if(MaterialHelper.isSpecialFuel(itemStack)) cir.setReturnValue(true);
    }
}
