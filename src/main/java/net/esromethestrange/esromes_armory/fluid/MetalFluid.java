package net.esromethestrange.esromes_armory.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.*;

public abstract class MetalFluid extends FlowableFluid {
    protected abstract BlockState getExtinguishBlock();

    public static abstract class Flowing extends MetalFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override public int getLevel(FluidState fluidState) { return fluidState.get(LEVEL); }
        @Override public boolean isStill(FluidState fluidState) {return false;}
    }

    public static abstract class Still extends MetalFluid {
        @Override public int getLevel(FluidState fluidState) { return 8; }
        @Override public boolean isStill(FluidState fluidState) { return true; }
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getFlowing() || fluid == getStill();
    }

    @Override protected int getMaxFlowDistance(WorldView world) { return 4; }
    @Override protected boolean isInfinite(World world) { return false; }
    @Override protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {return false;}
    @Override public int getTickRate(WorldView world) { return 10; }
    @Override protected int getLevelDecreasePerBlock(WorldView world) { return 2; }
    @Override protected float getBlastResistance() { return 100; }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        this.playExtinguishEvent(world, pos);
    }

    @Override
    protected void flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState) {
        if (direction == Direction.DOWN) {
            FluidState fluidState2 = world.getFluidState(pos);
            if (this.isIn(FluidTags.LAVA) && fluidState2.isIn(FluidTags.WATER)) {
                if (state.getBlock() instanceof FluidBlock) {
                    world.setBlockState(pos, getExtinguishBlock(), Block.NOTIFY_ALL);
                }
                this.playExtinguishEvent(world, pos);
                return;
            }
        }
        super.flow(world, pos, state, direction, fluidState);
    }

    private void playExtinguishEvent(WorldAccess world, BlockPos pos) {
        world.syncWorldEvent(WorldEvents.LAVA_EXTINGUISHED, pos, 0);
    }
}
