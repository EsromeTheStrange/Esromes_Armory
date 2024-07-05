package net.esromethestrange.esromes_armory.block;

import com.mojang.serialization.MapCodec;
import net.esromethestrange.esromes_armory.block.entity.ArmoryBlockEntities;
import net.esromethestrange.esromes_armory.block.entity.ForgeBlockEntity;
import net.esromethestrange.esromes_armory.block.entity.WorkbenchBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ForgeBlock extends BlockWithEntity implements BlockEntityProvider {
    VoxelShape BASE_SHAPE = Block.createCuboidShape(0,0,0,16,14,16);
    VoxelShape WALL_1 = Block.createCuboidShape(0,14,0,16, 16,2);
    VoxelShape WALL_2 = Block.createCuboidShape(0,14,0,2,16,16);
    VoxelShape WALL_3 = Block.createCuboidShape(0,14,14,16,16,16);
    VoxelShape WALL_4 = Block.createCuboidShape(14,14,0,16,16,16);
    VoxelShape SHAPE = VoxelShapes.union(BASE_SHAPE, WALL_1, WALL_2, WALL_3, WALL_4);

    protected ForgeBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(ForgeBlock::new);
        //TODO I have no idea what this is supposed to be doing.
    }

    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }
    @Override public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }

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

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        return ActionResult.SUCCESS;
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
