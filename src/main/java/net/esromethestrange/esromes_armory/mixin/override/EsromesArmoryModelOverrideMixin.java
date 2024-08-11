package net.esromethestrange.esromes_armory.mixin.override;

import net.esromethestrange.esromes_armory.client.model.override.HeatOverridePredicate;
import net.esromethestrange.esromes_armory.client.model.override.HeatStateModelOverride;
import net.minecraft.client.render.model.json.ModelOverride;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * @author Based on code from Chime by emilyploszaj
 */
@Mixin(ModelOverride.class)
public class EsromesArmoryModelOverrideMixin implements HeatStateModelOverride {
    @Unique
    private HeatOverridePredicate heatOverridePredicate = null;

    @Override
    public void setHeatPredicate(HeatOverridePredicate predicate) {
        heatOverridePredicate = predicate;
    }

    @Override
    public HeatOverridePredicate getHeatPredicate() {
        return heatOverridePredicate;
    }
}
