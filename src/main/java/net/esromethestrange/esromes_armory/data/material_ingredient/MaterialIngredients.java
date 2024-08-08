package net.esromethestrange.esromes_armory.data.material_ingredient;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.fluid.ArmoryFluids;
import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistryKeys;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class MaterialIngredients {
    public static final RegistryKey<MaterialIngredientData> BINDING = RegistryKey.of(ArmoryRegistryKeys.MATERIAL_INGREDIENT_DATA, Identifier.of(EsromesArmory.MOD_ID, "binding"));

    public static final RegistryKey<MaterialIngredientData> BLOCK = RegistryKey.of(ArmoryRegistryKeys.MATERIAL_INGREDIENT_DATA, Identifier.of(EsromesArmory.MOD_ID, "block"));
    public static final RegistryKey<MaterialIngredientData> INGOT = RegistryKey.of(ArmoryRegistryKeys.MATERIAL_INGREDIENT_DATA, Identifier.of(EsromesArmory.MOD_ID, "ingot"));

    public static final RegistryKey<MaterialIngredientData> LOG = RegistryKey.of(ArmoryRegistryKeys.MATERIAL_INGREDIENT_DATA, Identifier.of(EsromesArmory.MOD_ID, "log"));
    public static final RegistryKey<MaterialIngredientData> PLANKS = RegistryKey.of(ArmoryRegistryKeys.MATERIAL_INGREDIENT_DATA, Identifier.of(EsromesArmory.MOD_ID, "planks"));

    public static final RegistryKey<MaterialIngredientData> MOLTEN_METAL = RegistryKey.of(ArmoryRegistryKeys.MATERIAL_INGREDIENT_DATA, Identifier.of(EsromesArmory.MOD_ID, "molten_metal"));

    public static void bootstrap(Registerable<MaterialIngredientData> context){
        RegistryEntryLookup<Material> materialLookup = context.getRegistryLookup(ArmoryRegistryKeys.MATERIAL);
        context.register(BINDING, createMaterialIngredientData(materialLookup,
                new Pair<>(Materials.STRING, Items.STRING),
                new Pair<>(Materials.SLIME, Items.SLIME_BALL)
        ));

        context.register(BLOCK, createMaterialIngredientData(materialLookup,
                new Pair<>(Materials.COPPER, Blocks.COPPER_BLOCK),
                new Pair<>(Materials.GOLD, Blocks.GOLD_BLOCK),
                new Pair<>(Materials.IRON, Blocks.IRON_BLOCK),
                new Pair<>(Materials.NETHERITE, Blocks.NETHERITE_BLOCK),
                new Pair<>(Materials.STEEL, ArmoryBlocks.STEEL_BLOCK)
        ));
        context.register(INGOT, createMaterialIngredientData(materialLookup,
                new Pair<>(Materials.COPPER, Items.COPPER_INGOT),
                new Pair<>(Materials.GOLD, Items.GOLD_INGOT),
                new Pair<>(Materials.IRON, Items.IRON_INGOT),
                new Pair<>(Materials.NETHERITE, Items.NETHERITE_INGOT),
                new Pair<>(Materials.STEEL, ArmoryItems.STEEL_INGOT)
        ));

        context.register(LOG, createMaterialIngredientData(materialLookup,
                new Pair<>(Materials.ACACIA, Blocks.ACACIA_LOG),
                new Pair<>(Materials.BAMBOO, Blocks.BAMBOO_BLOCK),
                new Pair<>(Materials.BIRCH, Blocks.BIRCH_LOG),
                new Pair<>(Materials.CHERRY, Blocks.CHERRY_LOG),
                new Pair<>(Materials.CRIMSON, Blocks.CRIMSON_STEM),
                new Pair<>(Materials.DARK_OAK, Blocks.DARK_OAK_LOG),
                new Pair<>(Materials.JUNGLE, Blocks.JUNGLE_LOG),
                new Pair<>(Materials.MANGROVE, Blocks.MANGROVE_LOG),
                new Pair<>(Materials.OAK, Blocks.OAK_LOG),
                new Pair<>(Materials.SPRUCE, Blocks.SPRUCE_LOG),
                new Pair<>(Materials.WARPED, Blocks.WARPED_STEM)
        ));
        context.register(PLANKS, createMaterialIngredientData(materialLookup,
                new Pair<>(Materials.ACACIA, Blocks.ACACIA_PLANKS),
                new Pair<>(Materials.BAMBOO, Blocks.BAMBOO_PLANKS),
                new Pair<>(Materials.BIRCH, Blocks.BIRCH_PLANKS),
                new Pair<>(Materials.CHERRY, Blocks.CHERRY_PLANKS),
                new Pair<>(Materials.CRIMSON, Blocks.CRIMSON_PLANKS),
                new Pair<>(Materials.DARK_OAK, Blocks.DARK_OAK_PLANKS),
                new Pair<>(Materials.JUNGLE, Blocks.JUNGLE_PLANKS),
                new Pair<>(Materials.MANGROVE, Blocks.MANGROVE_PLANKS),
                new Pair<>(Materials.OAK, Blocks.OAK_PLANKS),
                new Pair<>(Materials.SPRUCE, Blocks.SPRUCE_PLANKS),
                new Pair<>(Materials.WARPED, Blocks.WARPED_PLANKS)
        ));

        context.register(MOLTEN_METAL, createMaterialIngredientData(materialLookup,
                new Pair<>(Materials.COPPER, ArmoryFluids.MOLTEN_COPPER),
                new Pair<>(Materials.GOLD, ArmoryFluids.MOLTEN_GOLD),
                new Pair<>(Materials.IRON, ArmoryFluids.MOLTEN_IRON),
                new Pair<>(Materials.NETHERITE, ArmoryFluids.MOLTEN_NETHERITE),
                new Pair<>(Materials.STEEL, ArmoryFluids.MOLTEN_STEEL)
        ));
    }

    @SafeVarargs
    private static MaterialIngredientData createMaterialIngredientData(RegistryEntryLookup<Material> materialLookup, Pair<RegistryKey<Material>, Object>... pairs){
        List<MaterialIngredientEntry<?>> entries = new ArrayList<>();
        for(Pair<RegistryKey<Material>, Object> pair : pairs){
            RegistryEntry<Material> material = materialLookup.getOrThrow(pair.getLeft());

            MaterialIngredientEntry<?> ingredientEntry = null;
            if(pair.getRight() instanceof ItemConvertible itemConvertible)
                ingredientEntry = new MaterialIngredientIngredientEntry(material, itemConvertible);
            if(pair.getRight() instanceof Fluid fluid)
                ingredientEntry = new MaterialIngredientFluidEntry(material, fluid);

            if(ingredientEntry != null)
                entries.add(ingredientEntry);
        }
        return new MaterialIngredientData(entries);
    }
}
