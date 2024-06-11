package net.esromethestrange.esromes_armory.block;

import net.esromethestrange.esromes_armory.block.entity.forge.ForgeBlockEntity;
import net.esromethestrange.esromes_armory.block.entity.forge.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ForgeBlock extends BlockWithEntity implements BlockEntityProvider {
    VoxelShape BASE_SHAPE = Block.createCuboidShape(0,0,0,16,7,16);
    VoxelShape WALL_1 = Block.createCuboidShape(0,7,0,16,8,2);
    VoxelShape WALL_2 = Block.createCuboidShape(0,7,0,2,8,16);
    VoxelShape WALL_3 = Block.createCuboidShape(0,7,14,16,8,16);
    VoxelShape WALL_4 = Block.createCuboidShape(14,7,0,16,8,16);
    VoxelShape SHAPE = VoxelShapes.union(BASE_SHAPE, WALL_1, WALL_2, WALL_3, WALL_4);

    protected ForgeBlock(Settings settings) {
        super(settings);
    }

    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }
    @Override public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ForgeBlockEntity(pos, state);
    }

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
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = ((ForgeBlockEntity) world.getBlockEntity(pos));

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.FORGE_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
