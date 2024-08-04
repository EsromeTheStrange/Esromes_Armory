package net.esromethestrange.esromes_armory.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ArmoryGenericTags {
    public static class Items{
        public static final TagKey<Item> STEEL_INGOTS = createTag("steel_ingots");

        private static TagKey<Item> createTag(String name){
            return TagKey.of(RegistryKeys.ITEM, Identifier.of("c", name));
        }
    }

    public static class Blocks{
        public static final TagKey<Block> STEEL_BLOCKS = createTag("steel_blocks");

        private static TagKey<Block> createTag(String name){
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", name));
        }
    }
}