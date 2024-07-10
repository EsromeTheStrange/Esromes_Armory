package net.esromethestrange.esromes_armory.block.entity;

import io.wispforest.owo.util.ImplementedInventory;
import net.esromethestrange.esromes_armory.data.heat.HeatData;
import net.esromethestrange.esromes_armory.data.heat.HeatHelper;
import net.esromethestrange.esromes_armory.fluid.ArmoryFluids;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class SmelteryBlockEntity extends BlockEntity implements ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public final SingleFluidStorage fluidStorage = SingleFluidStorage.withFixedCapacity(FluidConstants.BUCKET, this::tryMelt);

    public SmelteryBlockEntity(BlockPos pos, BlockState state) {
        super(ArmoryBlockEntities.SMELTERY_BLOCK_ENTITY, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public boolean receiveStack(ItemStack stack){
        if(!inventory.get(0).isEmpty())
            return false;
        setStack(0, stack.copyWithCount(1));
        stack.setCount(stack.getCount() - 1);
        tryMelt();
        return true;
    }

    private void tryMelt(){
        if(getStack(0).isEmpty()){
            markDirty();
            return;
        }
        HeatData heatData = HeatHelper.getHeatData(inventory.get(0).getItem());
        if(heatData != HeatData.EMPTY){
            FluidVariant fluidVariant = FluidVariant.of(ArmoryFluids.MOLTEN_STEEL);
            if(fluidStorage.getAmount() == 0){
                try(Transaction transaction = Transaction.openOuter()){
                    fluidStorage.insert(fluidVariant, FluidConstants.BUCKET, transaction);
                    transaction.commit();
                    inventory.set(0, ItemStack.EMPTY);
                }
            }
        }
        markDirty();
    }

    //Server Stuff
    @Override
    public void markDirty() {
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        super.markDirty();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        fluidStorage.writeNbt(nbt, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        inventory.clear();
        Inventories.readNbt(nbt, inventory, registryLookup);
        fluidStorage.readNbt(nbt, registryLookup);
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

}
