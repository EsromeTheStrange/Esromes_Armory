package net.esromethestrange.esromes_armory.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ForgeBlock extends Block{//TODO extends BlockWithEntity implements BlockEntityProvider {
    VoxelShape BASE_SHAPE = Block.createCuboidShape(0,0,0,16,14,16);
    VoxelShape WALL_1 = Block.createCuboidShape(0,14,0,16, 16,2);
    VoxelShape WALL_2 = Block.createCuboidShape(0,14,0,2,16,16);
    VoxelShape WALL_3 = Block.createCuboidShape(0,14,14,16,16,16);
    VoxelShape WALL_4 = Block.createCuboidShape(14,14,0,16,16,16);
    VoxelShape SHAPE = VoxelShapes.union(BASE_SHAPE, WALL_1, WALL_2, WALL_3, WALL_4);

    protected ForgeBlock(Settings settings) {
        super(settings);
    }

    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }
    @Override public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            //TODO Drops
//            if (blockEntity instanceof ForgeBlockEntity) {
//                ItemScatterer.spawn(world, pos, (ForgeBlockEntity)blockEntity);
//                world.updateComparators(pos,this);
//            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        return super.onUse(state, world, pos, player, hit);

        //TODO on use
    }

    //TODO Ticking
//    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
//        return checkType(type, ModBlockEntities.FORGE_BLOCK_ENTITY,
//                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
//    }
}
