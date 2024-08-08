package net.esromethestrange.esromes_armory.data.heat.heating_result;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistries;

public abstract class HeatingResult{
    public static Codec<HeatingResult> CODEC = ArmoryRegistries.HEATING_RESULT_SERIALIZERS.getCodec().dispatch(
            "type",
            HeatingResult::getSerializer,
            HeatingResult.HeatingResultSerializer::createCodec
    );

    public abstract boolean matches(Object o);
    public abstract HeatingResultSerializer<? extends HeatingResult> getSerializer();

    public abstract static class HeatingResultSerializer<T extends HeatingResult> {
        public abstract MapCodec<T> createCodec();
    }
}
