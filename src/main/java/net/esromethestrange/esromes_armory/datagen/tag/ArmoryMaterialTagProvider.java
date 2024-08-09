package net.esromethestrange.esromes_armory.datagen.tag;

import net.esromethestrange.esromes_armory.data.ArmoryTags;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistryKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ArmoryMaterialTagProvider extends FabricTagProvider<Material> {
    public ArmoryMaterialTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, ArmoryRegistryKeys.MATERIAL, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ArmoryTags.Materials.WOOD).add(
                Materials.OAK,
                Materials.ACACIA,
                Materials.BAMBOO,
                Materials.BIRCH,
                Materials.CHERRY,
                Materials.CRIMSON,
                Materials.DARK_OAK,
                Materials.JUNGLE,
                Materials.MANGROVE,
                Materials.SPRUCE,
                Materials.WARPED
        );

        getOrCreateTagBuilder(ArmoryTags.Materials.METAL).add(
                Materials.COPPER,
                Materials.GOLD,
                Materials.IRON,
                Materials.NETHERITE,
                Materials.STEEL
        );

        getOrCreateTagBuilder(ArmoryTags.Materials.BINDING).add(
                Materials.SLIME,
                Materials.STRING
        );
    }
}
