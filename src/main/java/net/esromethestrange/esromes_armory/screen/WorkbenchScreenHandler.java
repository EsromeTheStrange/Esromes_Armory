package net.esromethestrange.esromes_armory.screen;

import net.esromethestrange.esromes_armory.block.entity.WorkbenchBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class WorkbenchScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final WorkbenchBlockEntity blockEntity;

    public static final int[][] INPUT_POS = {
            {80,11},
            {60,11},
            {100,11}
    };
    public static final int[] OUTPUT_POS = {80,59};

    private static final int INVENTORY_X = 8;
    private static final int INVENTORY_Y = 84;

    public WorkbenchScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()));
    }

    public WorkbenchScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity){
        super(ModScreenHandlers.WORKBENCH_SCREEN_HANDLER, syncId);
        checkSize((Inventory)blockEntity, 2);
        this.inventory = (Inventory)blockEntity;
        inventory.onOpen(playerInventory.player);
        this.blockEntity = (WorkbenchBlockEntity) blockEntity;

        for(int i = 0; i< WorkbenchBlockEntity.NUM_INPUTS; i++){
            this.addSlot(new Slot(inventory, i, INPUT_POS[i][0], INPUT_POS[i][1]));
        }
        this.addSlot(new Slot(inventory, WorkbenchBlockEntity.OUTPUT_SLOT, OUTPUT_POS[0], OUTPUT_POS[1]));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int column = 0; column < 3; ++column) {
            for (int row = 0; row < 9; ++row) {
                this.addSlot(new Slot(playerInventory, row + column * 9 + 9, INVENTORY_X + + row * 18, INVENTORY_Y + column * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, INVENTORY_X + i * 18, INVENTORY_Y + (3 * 18) + 4));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) { return this.inventory.canPlayerUse(player); }
}
