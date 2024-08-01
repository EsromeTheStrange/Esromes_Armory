package net.esromethestrange.esromes_armory.datagen;

import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ArmoryBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ArmoryBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ArmoryBlocks.WORKBENCH);
        addDrop(ArmoryBlocks.FORGE);
        addDrop(ArmoryBlocks.ANVIL);

        addDrop(ArmoryBlocks.STEEL_BLOCK);
        addDrop(ArmoryBlocks.CHARCOAL_BLOCK);
    }
}
