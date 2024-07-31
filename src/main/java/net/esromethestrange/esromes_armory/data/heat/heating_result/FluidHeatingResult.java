package net.esromethestrange.esromes_armory.data.heat.heating_result;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class FluidHeatingResult extends HeatingResult{
    public final Fluid fluid;
    public final long amount;

    public FluidHeatingResult(Identifier fluid, long amount){
        this(Registries.FLUID.get(fluid), amount);
    }

    public FluidHeatingResult(Fluid fluid, long amount){
        this.fluid = fluid;
        this.amount = amount;
    }

    @Override
    public boolean matches(Object o) {
        if(o instanceof Fluid compareFluid)
            return fluid.matchesType(compareFluid);
        return false;
    }

    public Identifier fluidId(){ return Registries.FLUID.getId(this.fluid); }
    public long amount(){ return this.amount; }

    @Override
    public HeatingResultSerializer<FluidHeatingResult> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Serializer extends HeatingResultSerializer<FluidHeatingResult>{
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public MapCodec<FluidHeatingResult> createCodec() {
            return RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Identifier.CODEC.fieldOf("fluid").forGetter(FluidHeatingResult::fluidId),
                    Codec.LONG.fieldOf("amount").forGetter(FluidHeatingResult::amount)
            ).apply(instance, FluidHeatingResult::new));
        }
    }
}
