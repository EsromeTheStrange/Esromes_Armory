package net.esromethestrange.esromes_armory.block.entity;

import io.wispforest.owo.util.ImplementedInventory;
import net.esromethestrange.esromes_armory.data.heat.HeatLevel;
import net.esromethestrange.esromes_armory.item.component.ArmoryComponents;
import net.esromethestrange.esromes_armory.item.component.HeatComponent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
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

public class ForgeBlockEntity extends BlockEntity implements ImplementedInventory {
    public final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    SimpleInventory inventory = new SimpleInventory(1);

    public ForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ArmoryBlockEntities.FORGE_BLOCK_ENTITY, pos, state);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient) return;

        if(inventory.getStack(0).isEmpty())
            return;

        HeatComponent currentHeat = inventory.getStack(0).get(ArmoryComponents.HEAT);
        if(currentHeat == null) currentHeat = new HeatComponent(HeatLevel.ROOM_TEMPERATURE.temperature);
        inventory.getStack(0).set(ArmoryComponents.HEAT, new HeatComponent(
                (int)Math.clamp(currentHeat.getTemperature() + 1, 0, HeatLevel.SPARKLING.temperature)
        ));
        markDirty();
    }

    public ItemStack getRenderStack(){ return this.getStack(0); }
    public float getRotation(){ return getCachedState().get(FACING).asRotation(); }
    public boolean containsItem(){ return !this.getStack(0).isEmpty(); }

    public boolean receiveStack(ItemStack stack){
        if(containsItem())
            return false;
        setStack(0, stack.copyAndEmpty());
        return true;
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
}
