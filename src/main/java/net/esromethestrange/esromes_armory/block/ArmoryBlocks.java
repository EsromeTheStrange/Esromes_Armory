package net.esromethestrange.esromes_armory.block;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ArmoryBlocks {
    public static final Block WORKBENCH = registerBlock("workbench",
            new WorkbenchBlock(AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE).nonOpaque()));
    public static final Block FORGE = registerBlock("forge",
            new ForgeBlock(AbstractBlock.Settings.copy(Blocks.BRICKS).nonOpaque()));
    public static final Block ANVIL = registerBlock("anvil",
            new ArmoryAnvilBlock(AbstractBlock.Settings.copy(Blocks.ANVIL).nonOpaque()));
    public static final Block SMELTERY = registerBlock("smeltery",
            new SmelteryBlock(AbstractBlock.Settings.copy(Blocks.STONE).nonOpaque()));

    public static final Block STEEL_BLOCK = registerBlock("steel_block",
            new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    public static final Block CHARCOAL_BLOCK = registerBlock("charcoal_block",
            new Block(AbstractBlock.Settings.copy(Blocks.COAL_BLOCK)));

    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM, Identifier.of(EsromesArmory.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(EsromesArmory.MOD_ID, name), block);
    }

    public static void registerModBlocks(){ }
}
