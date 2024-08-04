package net.esromethestrange.esromes_armory.datagen;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.datagen.custom.MaterialIngredientProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.concurrent.CompletableFuture;

public class ArmoryMaterialIngredientProvider extends MaterialIngredientProvider {
    public ArmoryMaterialIngredientProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        register("binding",
                new Pair<>(Materials.NONE, Items.STRING)
        );

//        register("binding",
//                new Pair<>(Materials.STRING, Items.STRING),
//                new Pair<>(Materials.SLIME, Items.SLIME_BALL)
//        );
//
//        register("block",
//                new Pair<>(Materials.COPPER, Blocks.COPPER_BLOCK),
//                new Pair<>(Materials.GOLD, Blocks.GOLD_BLOCK),
//                new Pair<>(Materials.IRON, Blocks.IRON_BLOCK),
//                new Pair<>(Materials.NETHERITE, Blocks.NETHERITE_BLOCK),
//                new Pair<>(Materials.STEEL, ArmoryBlocks.STEEL_BLOCK)
//        );
//        register("ingot",
//                new Pair<>(Materials.COPPER, Items.COPPER_INGOT),
//                new Pair<>(Materials.GOLD, Items.GOLD_INGOT),
//                new Pair<>(Materials.IRON, Items.IRON_INGOT),
//                new Pair<>(Materials.NETHERITE, Items.NETHERITE_INGOT),
//                new Pair<>(Materials.STEEL, ArmoryItems.STEEL_INGOT)
//        );
//
//        register("log",
//                new Pair<>(Materials.ACACIA, Blocks.ACACIA_LOG),
//                new Pair<>(Materials.BAMBOO, Blocks.BAMBOO_BLOCK),
//                new Pair<>(Materials.BIRCH, Blocks.BIRCH_LOG),
//                new Pair<>(Materials.CHERRY, Blocks.CHERRY_LOG),
//                new Pair<>(Materials.CRIMSON, Blocks.CRIMSON_STEM),
//                new Pair<>(Materials.DARK_OAK, Blocks.DARK_OAK_LOG),
//                new Pair<>(Materials.JUNGLE, Blocks.JUNGLE_LOG),
//                new Pair<>(Materials.MANGROVE, Blocks.MANGROVE_LOG),
//                new Pair<>(Materials.OAK, Blocks.OAK_LOG),
//                new Pair<>(Materials.SPRUCE, Blocks.SPRUCE_LOG),
//                new Pair<>(Materials.WARPED, Blocks.WARPED_STEM)
//        );
//        register("planks",
//                new Pair<>(Materials.ACACIA, Blocks.ACACIA_PLANKS),
//                new Pair<>(Materials.BAMBOO, Blocks.BAMBOO_PLANKS),
//                new Pair<>(Materials.BIRCH, Blocks.BIRCH_PLANKS),
//                new Pair<>(Materials.CHERRY, Blocks.CHERRY_PLANKS),
//                new Pair<>(Materials.CRIMSON, Blocks.CRIMSON_PLANKS),
//                new Pair<>(Materials.DARK_OAK, Blocks.DARK_OAK_PLANKS),
//                new Pair<>(Materials.JUNGLE, Blocks.JUNGLE_PLANKS),
//                new Pair<>(Materials.MANGROVE, Blocks.MANGROVE_PLANKS),
//                new Pair<>(Materials.OAK, Blocks.OAK_PLANKS),
//                new Pair<>(Materials.SPRUCE, Blocks.SPRUCE_PLANKS),
//                new Pair<>(Materials.WARPED, Blocks.WARPED_PLANKS)
//        );
//
//        register("molten_metal",
//                new Pair<>(Materials.COPPER, ArmoryFluids.MOLTEN_COPPER),
//                new Pair<>(Materials.GOLD, ArmoryFluids.MOLTEN_GOLD),
//                new Pair<>(Materials.IRON, ArmoryFluids.MOLTEN_IRON),
//                new Pair<>(Materials.NETHERITE, ArmoryFluids.MOLTEN_NETHERITE),
//                new Pair<>(Materials.STEEL, ArmoryFluids.MOLTEN_STEEL)
//        );
    }

    @SafeVarargs
    public final void register(String name, Pair<RegistryKey<Material>, Object>... pairs){
        register(Identifier.of(EsromesArmory.MOD_ID, name), pairs);
    }
}
