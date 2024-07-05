package net.esromethestrange.esromes_armory.block.entity;

import io.wispforest.owo.util.ImplementedInventory;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.recipe.ArmoryRecipes;
import net.esromethestrange.esromes_armory.data.recipe.WorkbenchRecipe;
import net.esromethestrange.esromes_armory.data.recipe.WorkbenchRecipeInput;
import net.esromethestrange.esromes_armory.client.screen.WorkbenchData;
import net.esromethestrange.esromes_armory.client.screen.WorkbenchScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
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

    public static final String CONTAINER_TRANSLATION_KEY = "container."+ EsromesArmory.MOD_ID+".workbench";

    public WorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(ArmoryBlockEntities.WORKBENCH_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void markDirty() {
        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
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
        Inventories.readNbt(nbt, inventory.heldStacks, registryLookup);
    }

    @Override
    public DefaultedList<ItemStack> getItems() { return inventory.heldStacks; }

    @Override
    public Text getDisplayName() {
        return Text.translatable(CONTAINER_TRANSLATION_KEY);
    }

    @Override
    public Object getScreenOpeningData(ServerPlayerEntity player) {
        return new WorkbenchData(this.pos);
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
        Optional<RecipeEntry<WorkbenchRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        ItemStack output = getStack(OUTPUT_SLOT);
        ItemStack recipeOutput = recipe.get().value().craft(new WorkbenchRecipeInput(inventory), world.getRegistryManager());

        return  (output.isEmpty() || ItemStack.areItemsEqual(output, recipeOutput)) &&
                output.getCount() + recipeOutput.getCount() <= recipeOutput.getMaxCount();
    }

    private Optional<RecipeEntry<WorkbenchRecipe>> getCurrentRecipe(){
        SimpleInventory inv = new SimpleInventory(this.size());
        for (int i=0; i<this.size(); i++){
            inv.setStack(i, this.getStack(i));
        }
        return getWorld().getRecipeManager().getFirstMatch(ArmoryRecipes.WORKBENCH_RECIPE_TYPE, new WorkbenchRecipeInput(inventory), getWorld());
    }

    private void craftItem(){
        Optional<RecipeEntry<WorkbenchRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        ItemStack recipeOutput = recipe.get().value().craft(new WorkbenchRecipeInput(inventory), world.getRegistryManager());

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
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
