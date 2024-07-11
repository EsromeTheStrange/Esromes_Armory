package net.esromethestrange.esromes_armory.block;

import com.mojang.serialization.MapCodec;
import net.esromethestrange.esromes_armory.block.entity.ArmoryAnvilBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
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
import org.jetbrains.annotations.Nullable;

public class ArmoryAnvilBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public static final VoxelShape NORTH_SHAPE = GeneratedVoxels.ANVIL_SHAPE;
    public static final VoxelShape EAST_SHAPE = rotateShape(Direction.NORTH, Direction.EAST, GeneratedVoxels.ANVIL_SHAPE);
    public static final VoxelShape SOUTH_SHAPE = rotateShape(Direction.NORTH, Direction.SOUTH, GeneratedVoxels.ANVIL_SHAPE);
    public static final VoxelShape WEST_SHAPE = rotateShape(Direction.NORTH, Direction.WEST, GeneratedVoxels.ANVIL_SHAPE);

    protected ArmoryAnvilBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() { return ArmoryAnvilBlock.createCodec(ArmoryAnvilBlock::new); }
    @Override public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }

    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch(state.get(FACING)){
            case NORTH -> NORTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            default -> VoxelShapes.cuboid(0,0,0,1,1,1);
        };
    }
    public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{ shape, VoxelShapes.empty() };

        int times = (to.getHorizontal() - from.getHorizontal() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.union(buffer[1], VoxelShapes.cuboid(1-maxZ, minY, minX, 1-minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = VoxelShapes.empty();
        }

        return buffer[0];
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
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
        builder.add(FACING);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ArmoryAnvilBlockEntity) {
                ItemScatterer.spawn(world, pos, (ArmoryAnvilBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ArmoryAnvilBlockEntity armoryAnvilBlockEntity = (ArmoryAnvilBlockEntity) world.getBlockEntity(pos);
        if(world.isClient)
            return ItemActionResult.success(world.isClient);
        if(armoryAnvilBlockEntity == null)
            return ItemActionResult.FAIL;
        if(armoryAnvilBlockEntity.outputFilled() || armoryAnvilBlockEntity.full() || stack.isEmpty()){
            Vec3d vec3d = Vec3d.add(pos, 0.5, 1.01, 0.5).addRandom(world.random, 0.7f);
            ItemEntity itemEntity = new ItemEntity(world, vec3d.getX(), vec3d.getY(), vec3d.getZ(), armoryAnvilBlockEntity.removeTopStack());
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
            return ItemActionResult.success(world.isClient);
        }
        return armoryAnvilBlockEntity.receiveStack(stack) ? ItemActionResult.success(world.isClient) : ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ArmoryAnvilBlockEntity(pos, state);
    }
}
