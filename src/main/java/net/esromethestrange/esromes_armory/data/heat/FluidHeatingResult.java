package net.esromethestrange.esromes_armory.data.heat;

import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;

public class FluidHeatingResult extends HeatingResult{
    public final Fluid fluid;
    public final long amount;

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

    @Override
    public String toString() {
        return "fluid-" + Registries.FLUID.getId(fluid);
    }
}
