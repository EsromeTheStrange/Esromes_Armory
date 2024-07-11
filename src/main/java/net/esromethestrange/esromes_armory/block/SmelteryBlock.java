package net.esromethestrange.esromes_armory.block;

import com.mojang.serialization.MapCodec;
import net.esromethestrange.esromes_armory.block.entity.ForgeBlockEntity;
import net.esromethestrange.esromes_armory.block.entity.SmelteryBlockEntity;
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

public class SmelteryBlock extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape BOTTOM = VoxelShapes.cuboid(0,0,0,1,0.5,1);
    private static final VoxelShape TOP = VoxelShapes.cuboid((1/8f),0.5f,(1/8f),(7/8f),1,(7/8f));
    private static final VoxelShape SHAPE = VoxelShapes.union(BOTTOM, TOP);

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    protected SmelteryBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() { return SmelteryBlock.createCodec(SmelteryBlock::new); }
    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }
    @Override public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }

    //Block State Stuff
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx)
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
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

    //Block Entity Stuff
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SmelteryBlockEntity) {
                ItemScatterer.spawn(world, pos, (SmelteryBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        SmelteryBlockEntity smelteryBlockEntity = (SmelteryBlockEntity) world.getBlockEntity(pos);
        if(world.isClient)
            return ItemActionResult.success(world.isClient);
        if(smelteryBlockEntity == null)
            return ItemActionResult.FAIL;

        if(smelteryBlockEntity.hasFluid())
            if(smelteryBlockEntity.tryCast(stack))
                return ItemActionResult.SUCCESS;
        if(smelteryBlockEntity.containsItem()){
            Vec3d vec3d = Vec3d.add(pos, 0.5, 1.01, 0.5).addRandom(world.random, 0.7f);
            ItemEntity itemEntity = new ItemEntity(world, vec3d.getX(), vec3d.getY(), vec3d.getZ(), smelteryBlockEntity.removeStack(0));
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
            return ItemActionResult.success(world.isClient);
        }
        return smelteryBlockEntity.receiveStack(stack) ? ItemActionResult.success(world.isClient) : ItemActionResult.FAIL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SmelteryBlockEntity(pos, state);
    }
}
