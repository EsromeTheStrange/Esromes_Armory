package net.esromethestrange.esromes_armory.data;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ArmoryTags {
    public static class Blocks{


        private static TagKey<Block> createTag(String name){
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(EsromesArmory.MOD_ID, name));
        }
    }

    public static class Items{
        public static final TagKey<Item> BUCKETS = createTag("buckets");

        private static TagKey<Item> createTag(String name){
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(EsromesArmory.MOD_ID, name));
        }
    }

    public static class Fluids{
        public static final TagKey<Fluid> MOLTEN_METALS = createTag("molten_metals");

        private static TagKey<Fluid> createTag(String name){
            return TagKey.of(RegistryKeys.FLUID, Identifier.of(EsromesArmory.MOD_ID, name));
        }
    }
}
