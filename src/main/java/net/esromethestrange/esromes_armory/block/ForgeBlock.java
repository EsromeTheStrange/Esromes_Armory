package net.esromethestrange.esromes_armory.block;

import com.mojang.serialization.MapCodec;
import net.esromethestrange.esromes_armory.block.entity.ArmoryBlockEntities;
import net.esromethestrange.esromes_armory.block.entity.ForgeBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ForgeBlock extends BlockWithEntity implements BlockEntityProvider {
    VoxelShape BASE_SHAPE = Block.createCuboidShape(0,0,0,16,14,16);

    VoxelShape WALL_N = Block.createCuboidShape(2,14,0, 14, 16, 2);
    VoxelShape WALL_E = Block.createCuboidShape(14,14,2, 16, 16, 14);
    VoxelShape WALL_S = Block.createCuboidShape(2,14,14, 14, 16, 16);
    VoxelShape WALL_W = Block.createCuboidShape(0,14,2, 2, 16, 14);

    VoxelShape CORNER_NE = Block.createCuboidShape(14,14,0,16, 16,2);
    VoxelShape CORNER_ES = Block.createCuboidShape(14,14,14,16, 16,16);
    VoxelShape CORNER_SW = Block.createCuboidShape(0,14,14,2, 16,16);
    VoxelShape CORNER_WN = Block.createCuboidShape(0,14,0,2, 16,2);


    public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
    public static final BooleanProperty EAST = ConnectingBlock.EAST;
    public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
    public static final BooleanProperty WEST = ConnectingBlock.WEST;

    public static final BooleanProperty NORTHEAST = BooleanProperty.of("northeast");
    public static final BooleanProperty SOUTHEAST = BooleanProperty.of("southeast");
    public static final BooleanProperty SOUTHWEST = BooleanProperty.of("southwest");
    public static final BooleanProperty NORTHWEST = BooleanProperty.of("northwest");

    protected static final Map<Direction, BooleanProperty> FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES.entrySet().stream()
            .filter(entry -> (entry.getKey()).getAxis().isHorizontal())
            .collect(Util.toMap());

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    protected ForgeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
                .with(NORTHEAST, false)
                .with(SOUTHEAST, false)
                .with(SOUTHWEST, false)
                .with(NORTHWEST, false));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() { return ForgeBlock.createCodec(ForgeBlock::new); }
    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.union(BASE_SHAPE);

        if(!state.get(NORTH)) shape = VoxelShapes.union(shape, WALL_N);
        if(!state.get(EAST))  shape = VoxelShapes.union(shape, WALL_E);
        if(!state.get(SOUTH)) shape = VoxelShapes.union(shape, WALL_S);
        if(!state.get(WEST))  shape = VoxelShapes.union(shape, WALL_W);

        if(!state.get(NORTHEAST)) shape = VoxelShapes.union(shape, CORNER_NE);
        if(!state.get(SOUTHEAST)) shape = VoxelShapes.union(shape, CORNER_ES);
        if(!state.get(SOUTHWEST)) shape = VoxelShapes.union(shape, CORNER_SW);
        if(!state.get(NORTHWEST)) shape = VoxelShapes.union(shape, CORNER_WN);

        return shape;
    }
    @Override public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }

    //Block State Stuff

    public boolean canConnect(BlockState state) {
        Block block = state.getBlock();
        return block == this;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getAxis().getType() == Direction.Type.HORIZONTAL) {
            BlockState updateState = state;
            if(this.canConnect(neighborState)){
                if(direction == Direction.NORTH || direction == Direction.EAST)
                    updateState = updateState.with(NORTHEAST,
                            this.canConnect(world.getBlockState(pos.north())) &&
                            this.canConnect(world.getBlockState(pos.east())) &&
                            this.canConnect(world.getBlockState(pos.north().east())));
                if(direction == Direction.EAST || direction == Direction.SOUTH)
                    updateState = updateState.with(SOUTHEAST,
                            this.canConnect(world.getBlockState(pos.east())) &&
                            this.canConnect(world.getBlockState(pos.south())) &&
                            this.canConnect(world.getBlockState(pos.east().south())));
                if(direction == Direction.SOUTH || direction == Direction.WEST)
                    updateState = updateState.with(SOUTHWEST,
                            this.canConnect(world.getBlockState(pos.south())) &&
                            this.canConnect(world.getBlockState(pos.west())) &&
                            this.canConnect(world.getBlockState(pos.south().west())));
                if(direction == Direction.WEST || direction == Direction.NORTH)
                    updateState = updateState.with(NORTHWEST,
                            this.canConnect(world.getBlockState(pos.west())) &&
                            this.canConnect(world.getBlockState(pos.north())) &&
                            this.canConnect(world.getBlockState(pos.west().north())));
                world.updateNeighbors(pos, this);
            }
            return updateState.with(FACING_PROPERTIES.get(direction), this.canConnect(neighborState));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World blockView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        BlockState northState = blockView.getBlockState(blockPos.north());
        BlockState eastState = blockView.getBlockState(blockPos.east());
        BlockState southState = blockView.getBlockState(blockPos.south());
        BlockState westState = blockView.getBlockState(blockPos.west());
        return super.getPlacementState(ctx)
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(NORTH, this.canConnect(northState))
                .with(EAST, this.canConnect(eastState))
                .with(SOUTH, this.canConnect(southState))
                .with(WEST, this.canConnect(westState));
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, NORTH, EAST, SOUTH, WEST,
                NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST);
    }

    //Block Entity Stuff
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ForgeBlockEntity) {
                ItemScatterer.spawn(world, pos, (ForgeBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ForgeBlockEntity forgeBlockEntity = (ForgeBlockEntity) world.getBlockEntity(pos);
        if(world.isClient)
            return ItemActionResult.success(world.isClient);
        if(forgeBlockEntity == null)
            return ItemActionResult.FAIL;
        if(forgeBlockEntity.containsItem()){
            Vec3d vec3d = Vec3d.add(pos, 0.5, 1.01, 0.5).addRandom(world.random, 0.7f);
            ItemEntity itemEntity = new ItemEntity(world, vec3d.getX(), vec3d.getY(), vec3d.getZ(), forgeBlockEntity.removeStack(0));
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
            return ItemActionResult.success(world.isClient);
        }
        return forgeBlockEntity.receiveStack(stack) ? ItemActionResult.success(world.isClient) : ItemActionResult.FAIL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ForgeBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ArmoryBlockEntities.FORGE_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
