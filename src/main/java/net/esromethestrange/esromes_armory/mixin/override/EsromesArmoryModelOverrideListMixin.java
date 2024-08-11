package net.esromethestrange.esromes_armory.mixin.override;

import net.esromethestrange.esromes_armory.client.model.override.HeatOverridePredicate;
import net.esromethestrange.esromes_armory.client.model.override.HeatStateModelOverride;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverride;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * @author Based on code from Chime by emilyploszaj
 */
@Mixin(ModelOverrideList.class)
public class EsromesArmoryModelOverrideListMixin {
    @Shadow
    @Final
    private ModelOverrideList.BakedOverride[] overrides;

    @Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/client/render/model/Baker;Lnet/minecraft/client/render/model/json/JsonUnbakedModel;Ljava/util/List;)V")
    private void init(Baker baker, JsonUnbakedModel parent, List<ModelOverride> list, CallbackInfo info) {
        for (int i = 0; i < this.overrides.length; i++) {
            ((HeatStateModelOverride) this.overrides[i]).setHeatPredicate(
                    ((HeatStateModelOverride) (list.get(list.size() - i - 1))).getHeatPredicate());
        }
    }

    @Inject(at = @At("HEAD"), method = "apply")
    private void apply(BakedModel model, ItemStack stack, ClientWorld world, LivingEntity entity, int seed,
                       CallbackInfoReturnable<BakedModel> info) {
        HeatOverridePredicate.cachedStack = stack;
    }
}
