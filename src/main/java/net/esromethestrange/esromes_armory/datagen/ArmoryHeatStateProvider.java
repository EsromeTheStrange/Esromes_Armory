package net.esromethestrange.esromes_armory.datagen;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.esromethestrange.esromes_armory.datagen.custom.HeatStateProvider;
import net.esromethestrange.esromes_armory.data.heat.HeatLevel;
import net.esromethestrange.esromes_armory.fluid.ArmoryFluids;
import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ArmoryHeatStateProvider extends HeatStateProvider {
    public ArmoryHeatStateProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        registerMetal(Identifier.of(EsromesArmory.MOD_ID, "copper"), HeatLevel.ORANGE, Items.COPPER_INGOT, Blocks.COPPER_BLOCK, ArmoryFluids.MOLTEN_COPPER);
        registerMetal(Identifier.of(EsromesArmory.MOD_ID, "gold"), HeatLevel.ORANGE, Items.GOLD_INGOT, Blocks.GOLD_BLOCK, ArmoryFluids.MOLTEN_GOLD);
        registerMetal(Identifier.of(EsromesArmory.MOD_ID, "iron"), HeatLevel.WHITE, Items.IRON_INGOT, Blocks.IRON_BLOCK, ArmoryFluids.MOLTEN_IRON);
        registerMetal(Identifier.of(EsromesArmory.MOD_ID, "netherite"), HeatLevel.WHITE, Items.NETHERITE_INGOT, Blocks.NETHERITE_BLOCK, ArmoryFluids.MOLTEN_NETHERITE);
        registerMetal(Identifier.of(EsromesArmory.MOD_ID, "steel"), HeatLevel.WHITE, ArmoryItems.STEEL_INGOT, ArmoryBlocks.STEEL_BLOCK, ArmoryFluids.MOLTEN_STEEL);
    }
}
