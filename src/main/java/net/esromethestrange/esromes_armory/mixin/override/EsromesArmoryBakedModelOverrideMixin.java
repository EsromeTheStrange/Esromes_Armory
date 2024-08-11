package net.esromethestrange.esromes_armory.mixin.override;

import net.esromethestrange.esromes_armory.client.model.override.HeatOverridePredicate;
import net.esromethestrange.esromes_armory.client.model.override.HeatStateModelOverride;
import net.minecraft.client.render.model.json.ModelOverrideList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Based on code from Chime by emilyploszaj
 */
@Mixin(ModelOverrideList.BakedOverride.class)
public class EsromesArmoryBakedModelOverrideMixin implements HeatStateModelOverride {
    @Unique
    private HeatOverridePredicate heatOverridePredicate = null;

    @Inject(method = "test", at = @At("RETURN"), cancellable = true)
    private void test(float[] fs, CallbackInfoReturnable<Boolean> info) {
        if (info.getReturnValue() && heatOverridePredicate != null) {
            info.setReturnValue(HeatStateModelOverride.test(heatOverridePredicate));
        }
    }

    @Override
    public void setHeatPredicate(HeatOverridePredicate predicate) {
        heatOverridePredicate = predicate;
    }

    @Override
    public HeatOverridePredicate getHeatPredicate() {
        return heatOverridePredicate;
    }
}
