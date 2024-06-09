package net.esromethestrange.esromes_armory.block.entity;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.recipe.ForgeRecipe;
import net.esromethestrange.esromes_armory.recipe.ModRecipes;
import net.esromethestrange.esromes_armory.screen.ForgeScreenHandler;
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
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ForgeBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;

    private static final String NBTKEY_PROGRESS = "forge.progress";

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int max_progress = 72;

    public ForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FORGE_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch(index){
                    case 0 -> ForgeBlockEntity.this.progress;
                    case 1 -> ForgeBlockEntity.this.max_progress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch(index){
                    case 0 -> ForgeBlockEntity.this.progress = value;
                    case 1 -> ForgeBlockEntity.this.max_progress = value;
                };
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt(NBTKEY_PROGRESS, progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt(NBTKEY_PROGRESS);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container."+EsromesArmory.MOD_ID+".forge");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ForgeScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient) return;

        if(isOutputSlotEmptyOrReceivable() && this.hasRecipe()){
            progress++;
            if (progress >= max_progress)
                craftItem();
        }
        else progress = 0;
        markDirty(world, pos, state);
    }

    private boolean isOutputSlotEmptyOrReceivable(){
        ItemStack output = getStack(OUTPUT_SLOT);
        return  output.isEmpty() ||
                output.getCount() < output.getMaxCount();
    }

    private boolean hasRecipe(){
        Optional<ForgeRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        ItemStack output = getStack(OUTPUT_SLOT);
        ItemStack recipeOutput = recipe.get().getOutput(null);

        return  (output.isEmpty() || output.isOf(recipeOutput.getItem()) ) &&
                output.getCount() + recipeOutput.getCount() <= recipeOutput.getMaxCount();
    }

    private Optional<ForgeRecipe> getCurrentRecipe(){
        SimpleInventory inv = new SimpleInventory(this.size());
        for (int i=0; i<this.size(); i++){
            inv.setStack(i, this.getStack(i));
        }
        return getWorld().getRecipeManager().getFirstMatch(ModRecipes.FORGE_RECIPE_TYPE, inv, getWorld());
    }

    private void craftItem(){
        Optional<ForgeRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        ItemStack recipeOutput = recipe.get().getOutput(getWorld().getRegistryManager());

        this.removeStack(INPUT_SLOT, 1);
        this.setStack(OUTPUT_SLOT, new ItemStack(recipeOutput.getItem(), getStack(OUTPUT_SLOT).getCount() + recipeOutput.getCount()));
        progress = 0;
    }
}
