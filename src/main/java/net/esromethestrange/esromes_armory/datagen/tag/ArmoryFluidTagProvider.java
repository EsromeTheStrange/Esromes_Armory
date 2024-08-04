package net.esromethestrange.esromes_armory.datagen.tag;

import net.esromethestrange.esromes_armory.fluid.ArmoryFluids;
import net.esromethestrange.esromes_armory.data.ArmoryTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalFluidTags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.FluidTags;

import java.util.concurrent.CompletableFuture;

public class ArmoryFluidTagProvider extends FabricTagProvider.FluidTagProvider {
    public ArmoryFluidTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ArmoryTags.Fluids.MOLTEN_METALS).add(
                ArmoryFluids.MOLTEN_COPPER, ArmoryFluids.MOLTEN_COPPER_FLOWING,
                ArmoryFluids.MOLTEN_GOLD, ArmoryFluids.MOLTEN_GOLD_FLOWING,
                ArmoryFluids.MOLTEN_IRON, ArmoryFluids.MOLTEN_IRON_FLOWING,
                ArmoryFluids.MOLTEN_NETHERITE, ArmoryFluids.MOLTEN_NETHERITE_FLOWING,
                ArmoryFluids.MOLTEN_STEEL, ArmoryFluids.MOLTEN_STEEL_FLOWING
        );
        getOrCreateTagBuilder(FluidTags.LAVA).addTag(ArmoryTags.Fluids.MOLTEN_METALS);
        getOrCreateTagBuilder(ConventionalFluidTags.LAVA).addTag(ArmoryTags.Fluids.MOLTEN_METALS);
    }
}
