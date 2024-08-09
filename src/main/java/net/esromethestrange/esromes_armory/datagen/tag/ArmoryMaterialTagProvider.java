package net.esromethestrange.esromes_armory.datagen.tag;

import net.esromethestrange.esromes_armory.data.ArmoryTags;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistryKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ArmoryMaterialTagProvider extends FabricTagProvider<Material> {
    public ArmoryMaterialTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, ArmoryRegistryKeys.MATERIAL, registriesFuture);
    }

    public static final List<RegistryKey<Material>> WOODS = List.of(
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

    public static final List<RegistryKey<Material>> METALS = List.of(
            Materials.COPPER,
            Materials.GOLD,
            Materials.IRON,
            Materials.NETHERITE,
            Materials.STEEL
    );

    public static final List<RegistryKey<Material>> BINDINGS = List.of(
            Materials.SLIME,
            Materials.STRING
    );

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ArmoryTags.Materials.WOOD).add(WOODS);
        getOrCreateTagBuilder(ArmoryTags.Materials.METAL).add(METALS);
        getOrCreateTagBuilder(ArmoryTags.Materials.BINDING).add(BINDINGS);
    }
}
