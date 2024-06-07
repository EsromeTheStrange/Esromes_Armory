package net.esromethestrange.esromes_armory.util;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks{


        private static TagKey<Block> createTag(String name){
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(EsromesArmory.MOD_ID, name));
        }
    }

    public static class Items{


        private static TagKey<Item> createTag(String name){
            return TagKey.of(RegistryKeys.ITEM, new Identifier(EsromesArmory.MOD_ID, name));
        }
    }
}
