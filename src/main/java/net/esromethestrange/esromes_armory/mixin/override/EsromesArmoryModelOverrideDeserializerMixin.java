package net.esromethestrange.esromes_armory.mixin.override;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import net.esromethestrange.esromes_armory.client.model.override.HeatOverridePredicate;
import net.esromethestrange.esromes_armory.client.model.override.HeatStateModelOverride;
import net.minecraft.client.render.model.json.ModelOverride;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author Based on code from Chime by emilyploszaj
 */
@Mixin(ModelOverride.Deserializer.class)
public class EsromesArmoryModelOverrideDeserializerMixin {
    private static final String HEAT_PREDICATE_KEY = "heat_level";
    @Unique
    private ThreadLocal<HeatOverridePredicate> heatPredicate = new ThreadLocal<>();

    @Inject(method = "deserialize", at = @At("RETURN"), cancellable = true)
    public void deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext, CallbackInfoReturnable<ModelOverride> cir) throws JsonParseException {
        ((HeatStateModelOverride) cir.getReturnValue()).setHeatPredicate(heatPredicate.get());
    }

    @Inject(method = "deserializeMinPropertyValues", at = @At("HEAD"))
    private void deserializeMinPropertyValues(JsonObject object, CallbackInfoReturnable<List<ModelOverride.Condition>> cir) {
        JsonObject pred = object.getAsJsonObject("predicate");
        parseCustomPredicates(pred);
    }

    @Unique
    private void parseCustomPredicates(JsonObject pred) {
        boolean remove = false;
        for (Map.Entry<String, JsonElement> entry : pred.entrySet()) {
            if (entry.getValue().isJsonObject() && entry.getKey().equals(HEAT_PREDICATE_KEY)) {
                heatPredicate.set(HeatOverridePredicate.CODEC.decode(JsonOps.INSTANCE, entry.getValue().getAsJsonObject()).getOrThrow().getFirst());
                remove = true;
            }
        }
        if(remove)
            pred.remove(HEAT_PREDICATE_KEY);
    }
}
