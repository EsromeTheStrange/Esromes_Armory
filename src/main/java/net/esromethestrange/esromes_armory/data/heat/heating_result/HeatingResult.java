package net.esromethestrange.esromes_armory.data.heat.heating_result;

import com.mojang.serialization.MapCodec;

public abstract class HeatingResult{
    public abstract boolean matches(Object o);
    public abstract HeatingResultSerializer<? extends HeatingResult> getSerializer();

    public abstract static class HeatingResultSerializer<T extends HeatingResult> {
        public abstract MapCodec<T> createCodec();
    }
}
