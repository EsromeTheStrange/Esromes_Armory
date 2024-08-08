package net.esromethestrange.esromes_armory.datagen;

import net.esromethestrange.esromes_armory.registry.ArmoryRegistryKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ArmoryCustomProviders extends FabricDynamicRegistryProvider {
    public ArmoryCustomProviders(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getWrapperOrThrow(ArmoryRegistryKeys.MATERIAL));
        entries.addAll(registries.getWrapperOrThrow(ArmoryRegistryKeys.MATERIAL_INGREDIENT_DATA));
        entries.addAll(registries.getWrapperOrThrow(ArmoryRegistryKeys.HEAT_DATA));
    }

    @Override
    public String getName() {
        return "Materials";
    }
}
