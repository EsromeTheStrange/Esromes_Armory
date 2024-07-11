package net.esromethestrange.esromes_armory.fluid.metals;

import net.esromethestrange.esromes_armory.fluid.ArmoryFluids;
import net.esromethestrange.esromes_armory.fluid.MetalFluid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.property.Properties;

public class CopperFluid {
    public static class Still extends MetalFluid.Still {
        @Override
        public Fluid getStill() { return ArmoryFluids.MOLTEN_COPPER; }

        @Override
        public Fluid getFlowing() {
            return ArmoryFluids.MOLTEN_COPPER_FLOWING;
        }

        @Override
        public Item getBucketItem() {
            return ArmoryFluids.MOLTEN_COPPER_BUCKET;
        }

        @Override
        protected BlockState toBlockState(FluidState fluidState) {
            return ArmoryFluids.MOLTEN_COPPER_BLOCK.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
        }

        @Override
        protected BlockState getExtinguishBlock() {
            return Blocks.COPPER_BLOCK.getDefaultState();
        }
    }

    public static class Flowing extends MetalFluid.Flowing{
        @Override
        public Fluid getStill() {
            return ArmoryFluids.MOLTEN_COPPER;
        }

        @Override
        public Fluid getFlowing() {
            return ArmoryFluids.MOLTEN_COPPER_FLOWING;
        }

        @Override
        public Item getBucketItem() {
            return ArmoryFluids.MOLTEN_COPPER_BUCKET;
        }

        @Override
        protected BlockState toBlockState(FluidState fluidState) {
            return ArmoryFluids.MOLTEN_COPPER_BLOCK.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
        }

        @Override
        protected BlockState getExtinguishBlock() { return Blocks.COPPER_BLOCK.getDefaultState(); }
    }
}
