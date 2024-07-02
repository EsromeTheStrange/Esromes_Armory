package net.esromethestrange.esromes_armory.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class WorkbenchBlock extends Block {//TODO extends BlockWithEntity implements BlockEntityProvider {
    VoxelShape LEG_MXMZ = Block.createCuboidShape(0,0,0,2, 14,2);
    VoxelShape LEG_MXPZ = Block.createCuboidShape(0,0,14,2, 14,16);
    VoxelShape LEG_PXPZ = Block.createCuboidShape(14,0,14,16, 14,16);
    VoxelShape LEG_PXMZ = Block.createCuboidShape(14,0,0,16, 14,2);
    VoxelShape TOP = Block.createCuboidShape(0,14,0,16,16,16);
    VoxelShape SHAPE = VoxelShapes.union(LEG_MXMZ, LEG_MXPZ, LEG_PXPZ, LEG_PXMZ, TOP);

    protected WorkbenchBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }
    @Override public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            //TODO Drops
//            if (blockEntity instanceof WorkbenchBlockEntity) {
//                ItemScatterer.spawn(world, pos, (WorkbenchBlockEntity)blockEntity);
//                world.updateComparators(pos,this);
//            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        return super.onUse(state, world, pos, player, hit);
        //TODO On Use
    }

    //TODO Ticker
//    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
//        return checkType(type, ModBlockEntities.WORKBENCH_BLOCK_ENTITY,
//                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
//    }
}
