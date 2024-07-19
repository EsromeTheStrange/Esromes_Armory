package net.esromethestrange.esromes_armory.mixin;

import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.item.material.PartBasedItem;
import net.esromethestrange.esromes_armory.util.MaterialHelper;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public class EsromesArmoryAbstractFurnaceBlockEntityMixin {
    @Inject(method = "getFuelTime", at = @At(value = "RETURN"), cancellable = true)
    protected void esromes_armory$onGetFuelTime(ItemStack fuel, CallbackInfoReturnable<Integer> cir){
        if(fuel.getItem() instanceof MaterialItem materialItem)
            cir.setReturnValue(materialItem.getFuelTime(fuel));
        if(fuel.getItem() instanceof PartBasedItem partBasedItem)
            cir.setReturnValue(partBasedItem.getFuelTime(fuel));
    }

    @Inject(method = "isValid", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    protected void esromes_armory$onIsValid(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if(MaterialHelper.isSpecialFuel(stack)) cir.setReturnValue(true);
    }
}
