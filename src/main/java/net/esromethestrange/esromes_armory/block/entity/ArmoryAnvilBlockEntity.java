package net.esromethestrange.esromes_armory.block.entity;

import io.wispforest.owo.util.ImplementedInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ArmoryAnvilBlockEntity extends BlockEntity implements ImplementedInventory {

    public final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    SimpleInventory inventory = new SimpleInventory(2);

    public ArmoryAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ArmoryBlockEntities.ANVIL_BLOCK_ENTITY, pos, state);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient) return;
        markDirty();
    }

    public ItemStack getRenderStack(int slot){ return this.getStack(slot); }
    public float getRotation(){ return getCachedState().get(FACING).asRotation(); }
    public boolean full(){ return !this.getStack(0).isEmpty() && !this.getStack(1).isEmpty(); }

    public boolean receiveStack(ItemStack stack){
        if(full())
            return false;
        if(getStack(0).isEmpty())
            setStack(0, stack.copyAndEmpty());
        else
            setStack(1, stack.copyAndEmpty());
        return true;
    }

    public ItemStack removeTopStack(){
        if(!getStack(1).isEmpty())
            return removeStack(1);
        return removeStack(0);
    }

    @Override public DefaultedList<ItemStack> getItems() { return inventory.heldStacks; }

    @Override
    public void markDirty() {
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        super.markDirty();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory.heldStacks, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        inventory.clear();
        Inventories.readNbt(nbt, inventory.heldStacks, registryLookup);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override public BlockEntityType<?> getType() { return ArmoryBlockEntities.ANVIL_BLOCK_ENTITY; }
}
