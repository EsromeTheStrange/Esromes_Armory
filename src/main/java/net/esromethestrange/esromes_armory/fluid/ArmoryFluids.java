package net.esromethestrange.esromes_armory.fluid;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.fluid.metals.GoldFluid;
import net.esromethestrange.esromes_armory.fluid.metals.SteelFluid;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ArmoryFluids {
    public static FlowableFluid MOLTEN_STEEL;
    public static FlowableFluid MOLTEN_STEEL_FLOWING;
    public static Item MOLTEN_STEEL_BUCKET;
    public static Block MOLTEN_STEEL_BLOCK;

    public static FlowableFluid MOLTEN_GOLD;
    public static FlowableFluid MOLTEN_GOLD_FLOWING;
    public static Item MOLTEN_GOLD_BUCKET;
    public static Block MOLTEN_GOLD_BLOCK;

    public static void registerFluids(){
        MOLTEN_STEEL = Registry.register(Registries.FLUID, Identifier.of(EsromesArmory.MOD_ID, "molten_steel"), new SteelFluid.Still());
        MOLTEN_STEEL_FLOWING = Registry.register(Registries.FLUID, Identifier.of(EsromesArmory.MOD_ID, "molten_steel_flowing"), new SteelFluid.Flowing());
        MOLTEN_STEEL_BUCKET = Registry.register(Registries.ITEM, Identifier.of(EsromesArmory.MOD_ID, "molten_steel_bucket"),
                new BucketItem(MOLTEN_STEEL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
        MOLTEN_STEEL_BLOCK = Registry.register(Registries.BLOCK, Identifier.of(EsromesArmory.MOD_ID, "molten_steel"),
                new FluidBlock(MOLTEN_STEEL, AbstractBlock.Settings.copy(Blocks.LAVA)){});

        MOLTEN_GOLD = Registry.register(Registries.FLUID, Identifier.of(EsromesArmory.MOD_ID, "molten_gold"), new GoldFluid.Still());
        MOLTEN_GOLD_FLOWING = Registry.register(Registries.FLUID, Identifier.of(EsromesArmory.MOD_ID, "molten_gold_flowing"), new GoldFluid.Flowing());
        MOLTEN_GOLD_BUCKET = Registry.register(Registries.ITEM, Identifier.of(EsromesArmory.MOD_ID, "molten_gold_bucket"),
                new BucketItem(MOLTEN_GOLD, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
        MOLTEN_GOLD_BLOCK = Registry.register(Registries.BLOCK, Identifier.of(EsromesArmory.MOD_ID, "molten_gold"),
                new FluidBlock(MOLTEN_GOLD, AbstractBlock.Settings.copy(Blocks.LAVA)){});
    }
}
