package net.esromethestrange.esromes_armory.block;

import com.mojang.serialization.MapCodec;
import net.esromethestrange.esromes_armory.block.entity.ArmoryBlockEntities;
import net.esromethestrange.esromes_armory.block.entity.WorkbenchBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class WorkbenchBlock extends BlockWithEntity implements BlockEntityProvider {
    VoxelShape LEG_NE = Block.createCuboidShape(12,0,2,14, 14,4);
    VoxelShape LEG_ES = Block.createCuboidShape(12,0,12,14, 14,14);
    VoxelShape LEG_SW = Block.createCuboidShape(2,0,12,4, 14,14);
    VoxelShape LEG_WN = Block.createCuboidShape(2,0,2,4, 14,4);

    VoxelShape CORNER_NE = Block.createCuboidShape(15,12,0,16, 14,1);
    VoxelShape CORNER_ES = Block.createCuboidShape(15,12,15,16, 14,16);
    VoxelShape CORNER_SW = Block.createCuboidShape(0,12,15,1, 14,16);
    VoxelShape CORNER_WN = Block.createCuboidShape(0,12,0,1, 14,1);

    VoxelShape SIDE_N = Block.createCuboidShape(1,12,0, 15, 14, 1);
    VoxelShape SIDE_E = Block.createCuboidShape(15,12,1, 16, 14, 15);
    VoxelShape SIDE_S = Block.createCuboidShape(1,12,15, 15, 14, 16);
    VoxelShape SIDE_W = Block.createCuboidShape(0,12,1, 1, 14, 15);

    VoxelShape TOP = Block.createCuboidShape(1,12,1,15,14,15);

    public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
    public static final BooleanProperty EAST = ConnectingBlock.EAST;
    public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
    public static final BooleanProperty WEST = ConnectingBlock.WEST;
    protected static final Map<Direction, BooleanProperty> FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES.entrySet().stream()
            .filter(entry -> (entry.getKey()).getAxis().isHorizontal())
            .collect(Util.toMap());

    protected WorkbenchBlock(AbstractBlock.Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(WorkbenchBlock::new);
    }

    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        boolean north = state.get(NORTH);
        boolean east = state.get(EAST);
        boolean south = state.get(SOUTH);
        boolean west = state.get(WEST);

        VoxelShape shape = VoxelShapes.union(TOP);

        if(north) shape =   VoxelShapes.union(shape, SIDE_N);
        if(east) shape =    VoxelShapes.union(shape, SIDE_E);
        if(south) shape =   VoxelShapes.union(shape, SIDE_S);
        if(west) shape =    VoxelShapes.union(shape, SIDE_W);

        if(!(north || east)) shape = VoxelShapes.union(shape, LEG_NE);
        if(!(east || south)) shape = VoxelShapes.union(shape, LEG_ES);
        if(!(south || west)) shape = VoxelShapes.union(shape, LEG_SW);
        if(!(west || north)) shape = VoxelShapes.union(shape, LEG_WN);

        if(north && east) shape = VoxelShapes.union(shape, CORNER_NE);
        if(east && south) shape = VoxelShapes.union(shape, CORNER_ES);
        if(south && west) shape = VoxelShapes.union(shape, CORNER_SW);
        if(west && north) shape = VoxelShapes.union(shape, CORNER_WN);

        return shape;
    }
    @Override public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }

    //Block State Stuff
    public boolean canConnect(BlockState state) {
        Block block = state.getBlock();
        return block == this;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World blockView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        BlockState northState = blockView.getBlockState(blockPos.north());
        BlockState eastState = blockView.getBlockState(blockPos.east());
        BlockState southState = blockView.getBlockState(blockPos.south());
        BlockState westState = blockView.getBlockState(blockPos.west());
        return super.getPlacementState(ctx)
                .with(NORTH, this.canConnect(northState))
                .with(EAST, this.canConnect(eastState))
                .with(SOUTH, this.canConnect(southState))
                .with(WEST, this.canConnect(westState));
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getAxis().getType() == Direction.Type.HORIZONTAL) {
            return state.with(FACING_PROPERTIES.get(direction), this.canConnect(neighborState));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH);
    }

    //Block Entity Stuff
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof WorkbenchBlockEntity) {
                ItemScatterer.spawn(world, pos, (WorkbenchBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = ((WorkbenchBlockEntity) world.getBlockEntity(pos));

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WorkbenchBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ArmoryBlockEntities.WORKBENCH_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
