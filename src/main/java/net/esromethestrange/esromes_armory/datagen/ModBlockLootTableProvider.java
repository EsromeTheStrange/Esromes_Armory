package net.esromethestrange.esromes_armory.datagen;

import net.esromethestrange.esromes_armory.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ModBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.FORGE);
        addDrop(ModBlocks.WORKBENCH);

        addDrop(ModBlocks.STEEL_BLOCK);
        addDrop(ModBlocks.CHARCOAL_BLOCK);
    }
}
