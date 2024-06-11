package net.esromethestrange.esromes_armory.block;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block FORGE = registerBlock("forge",
            new ForgeBlock(FabricBlockSettings.copyOf(Blocks.TERRACOTTA)));

    public static final Block STEEL_BLOCK = registerBlock("steel_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));
    public static final Block CHARCOAL_BLOCK = registerBlock("charcoal_block",
            new Block(FabricBlockSettings.copyOf(Blocks.COAL_BLOCK).nonOpaque()));

    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM, new Identifier(EsromesArmory.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(EsromesArmory.MOD_ID, name), block);
    }

    public static void registerModBlocks(){
        EsromesArmory.LOGGER.info("Registering blocks...");
    }
}
