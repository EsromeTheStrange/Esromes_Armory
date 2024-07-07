package net.esromethestrange.esromes_armory.fluid;

import net.esromethestrange.esromes_armory.EsromesArmory;
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
    public static FlowableFluid STEEL_STILL;
    public static FlowableFluid STEEL_FLOWING;
    public static Item STEEL_BUCKET;
    public static Block STEEL_FLUID_BLOCK;

    public static void registerFluids(){
        STEEL_STILL = Registry.register(Registries.FLUID, Identifier.of(EsromesArmory.MOD_ID, "molten_steel"), new SteelFluid.Still());
        STEEL_FLOWING = Registry.register(Registries.FLUID, Identifier.of(EsromesArmory.MOD_ID, "molten_steel_flowing"), new SteelFluid.Flowing());
        STEEL_BUCKET = Registry.register(Registries.ITEM, Identifier.of(EsromesArmory.MOD_ID, "molten_steel_bucket"),
                new BucketItem(STEEL_STILL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
        STEEL_FLUID_BLOCK = Registry.register(Registries.BLOCK, Identifier.of(EsromesArmory.MOD_ID, "molten_steel"),
                new FluidBlock(STEEL_STILL, AbstractBlock.Settings.copy(Blocks.LAVA)){});
    }
}
