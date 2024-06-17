package net.esromethestrange.esromes_armory.block.entity;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.entity.ImplementedInventory;
import net.esromethestrange.esromes_armory.block.entity.ModBlockEntities;
import net.esromethestrange.esromes_armory.recipe.ModRecipes;
import net.esromethestrange.esromes_armory.recipe.WorkbenchRecipe;
import net.esromethestrange.esromes_armory.screen.WorkbenchScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WorkbenchBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final SimpleInventory inventory = new SimpleInventory(WorkbenchRecipe.NUM_INPUTS + 1);

    public static final int NUM_INPUTS = WorkbenchRecipe.NUM_INPUTS;
    public static final int OUTPUT_SLOT = WorkbenchRecipe.NUM_INPUTS;

    public WorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WORKBENCH_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void markDirty() {
        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        super.markDirty();
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory.stacks);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory.stacks);
    }

    @Override
    public DefaultedList<ItemStack> getItems() { return inventory.stacks; }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container."+ EsromesArmory.MOD_ID+".workbench");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new WorkbenchScreenHandler(syncId, playerInventory, this);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient) return;

        if(isOutputSlotEmptyOrReceivable() && this.hasRecipe())
            craftItem();

        markDirty(world, pos, state);
    }

    private boolean isOutputSlotEmptyOrReceivable(){
        ItemStack output = getStack(OUTPUT_SLOT);
        return  output.isEmpty() ||
                output.getCount() < output.getMaxCount();
    }

    private boolean hasRecipe(){
        Optional<WorkbenchRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        ItemStack output = getStack(OUTPUT_SLOT);
        ItemStack recipeOutput = recipe.get().craft(inventory, world.getRegistryManager());

        return  (output.isEmpty() || ItemStack.areItemsEqual(output, recipeOutput)) &&
                output.getCount() + recipeOutput.getCount() <= recipeOutput.getMaxCount();
    }

    private Optional<WorkbenchRecipe> getCurrentRecipe(){
        SimpleInventory inv = new SimpleInventory(this.size());
        for (int i=0; i<this.size(); i++){
            inv.setStack(i, this.getStack(i));
        }
        return getWorld().getRecipeManager().getFirstMatch(ModRecipes.WORKBENCH_RECIPE_TYPE, inv, getWorld());
    }

    private void craftItem(){
        Optional<WorkbenchRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        ItemStack recipeOutput = recipe.get().craft(inventory, world.getRegistryManager());

        for(int i=0; i<NUM_INPUTS; i++)
            this.removeStack(i, 1);

        if(this.getStack(OUTPUT_SLOT).isEmpty())
            this.setStack(OUTPUT_SLOT, recipeOutput);
        else
            this.getStack(OUTPUT_SLOT).increment(recipeOutput.getCount());
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
