package net.esromethestrange.esromes_armory.block.entity;

import io.wispforest.owo.util.ImplementedInventory;
import net.esromethestrange.esromes_armory.recipe.AnvilRecipe;
import net.esromethestrange.esromes_armory.recipe.ArmoryRecipes;
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
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ArmoryAnvilBlockEntity extends BlockEntity implements ImplementedInventory {

    public final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    DefaultedList<ItemStack> inventory = DefaultedList.ofSize(AnvilRecipe.NUM_INPUTS + 1, ItemStack.EMPTY);
    public static final int OUTPUT_SLOT = AnvilRecipe.NUM_INPUTS;

    public ArmoryAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ArmoryBlockEntities.ANVIL_BLOCK_ENTITY, pos, state);
    }

    public ItemStack getRenderStack(int slot){ return this.getStack(slot); }
    public float getRotation(){ return getCachedState().get(FACING).asRotation(); }
    public boolean full(){ return !this.getStack(0).isEmpty() && !this.getStack(1).isEmpty(); }
    public boolean outputFilled(){ return !this.getStack(OUTPUT_SLOT).isEmpty(); }

    public boolean receiveStack(ItemStack stack, int index){
        if(full())
            return false;
        if(getStack(index).isEmpty())
            setStack(index, stack.copyWithCount(1));
        stack.setCount(stack.getCount() - 1);
        tryCraft();
        markDirty();
        return true;
    }

    public ItemStack removeOutputOrStack(int index){
        markDirty();
        if(outputFilled())
            return removeStack(OUTPUT_SLOT);
        return removeStack(index);
    }

    protected void tryCraft(){
        if(isOutputSlotEmptyOrReceivable() && this.hasRecipe())
            craftItem();
    }

    private boolean isOutputSlotEmptyOrReceivable(){
        ItemStack output = getStack(OUTPUT_SLOT);
        return  output.isEmpty() ||
                output.getCount() < output.getMaxCount();
    }

    private boolean hasRecipe(){
        Optional<RecipeEntry<AnvilRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        ItemStack output = getStack(OUTPUT_SLOT);
        ItemStack recipeOutput = recipe.get().value().craft(AnvilRecipe.inputFrom(inventory), world.getRegistryManager());

        return  (output.isEmpty() || ItemStack.areItemsEqual(output, recipeOutput)) &&
                output.getCount() + recipeOutput.getCount() <= recipeOutput.getMaxCount();
    }

    private Optional<RecipeEntry<AnvilRecipe>> getCurrentRecipe(){
        SimpleInventory inv = new SimpleInventory(this.size());
        for (int i=0; i<this.size(); i++){
            inv.setStack(i, this.getStack(i));
        }
        return getWorld().getRecipeManager().getFirstMatch(ArmoryRecipes.ANVIL_RECIPE_TYPE, AnvilRecipe.inputFrom(inventory), getWorld());
    }

    private void craftItem(){
        Optional<RecipeEntry<AnvilRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        ItemStack recipeOutput = recipe.get().value().craft(AnvilRecipe.inputFrom(inventory), world.getRegistryManager());

        for(int i=0; i<AnvilRecipe.NUM_INPUTS; i++)
            this.removeStack(i, 1);

        if(this.getStack(OUTPUT_SLOT).isEmpty())
            this.setStack(OUTPUT_SLOT, recipeOutput);
        else
            this.getStack(OUTPUT_SLOT).increment(recipeOutput.getCount());
    }

    //Inventory Stuff
    @Override public DefaultedList<ItemStack> getItems() { return inventory; }
    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if(slot == OUTPUT_SLOT) return false;
        return ImplementedInventory.super.isValid(slot, stack);
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
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        inventory.clear();
        Inventories.readNbt(nbt, inventory, registryLookup);
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
