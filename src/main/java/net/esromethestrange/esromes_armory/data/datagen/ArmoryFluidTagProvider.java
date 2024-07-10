package net.esromethestrange.esromes_armory.data.datagen;

import net.esromethestrange.esromes_armory.fluid.ArmoryFluids;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.FluidTags;

import java.util.concurrent.CompletableFuture;

public class ArmoryFluidTagProvider extends FabricTagProvider.FluidTagProvider {
    public ArmoryFluidTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(FluidTags.LAVA)
                .add(ArmoryFluids.MOLTEN_STEEL, ArmoryFluids.MOLTEN_STEEL_FLOWING);
    }
}
