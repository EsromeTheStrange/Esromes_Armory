package net.esromethestrange.esromes_armory.block.entity;

import io.wispforest.owo.util.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ForgeBlockEntity extends BlockEntity implements ImplementedInventory {
    SimpleInventory inventory = new SimpleInventory(1);

    public ForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ArmoryBlockEntities.FORGE_BLOCK_ENTITY, pos, state);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient) return;

        markDirty(world, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory.heldStacks;
    }

    public boolean containsItem(){
        return !getStack(0).isEmpty();
    }

    public boolean receiveStack(ItemStack stack){
        if(containsItem())
            return false;
        setStack(0, stack.copyAndEmpty());
        return true;
    }
}
