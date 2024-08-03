package net.esromethestrange.esromes_armory.datagen.tag;

import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.esromethestrange.esromes_armory.util.ArmoryGenericTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ArmoryBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ArmoryBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        addPickaxeTag(ArmoryBlocks.STEEL_BLOCK, 1);

        //Generic Tags
        getOrCreateTagBuilder(ArmoryGenericTags.Blocks.STEEL_BLOCKS).add(ArmoryBlocks.STEEL_BLOCK);
    }

    /**
     * Sets a block to require a pickaxe in order to be mined.
     * @param block The block to add the tag to.
     * @param level The pickaxe.json level required (0 - wood, 1 - stone, 2 - iron, 3 - diamond).
     */
    private void addPickaxeTag(Block block, int level){
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(block);
        switch(level){
            case 0:
                break;
            case 1:
                getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(block);
                break;
            case 2:
                getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(block);
                break;
            case 3:
                getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(block);
                break;
        }
    }
}
