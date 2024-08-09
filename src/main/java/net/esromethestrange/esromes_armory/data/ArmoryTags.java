package net.esromethestrange.esromes_armory.data;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistryKeys;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ArmoryTags {
    public static class Materials{
        public static final TagKey<Material> WOOD = createTag("wood");
        public static final TagKey<Material> METAL = createTag("metal");
        public static final TagKey<Material> BINDING = createTag("binding");

        public static TagKey<Material> createTag(String name){
            return TagKey.of(ArmoryRegistryKeys.MATERIAL, Identifier.of(EsromesArmory.MOD_ID, name));
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
