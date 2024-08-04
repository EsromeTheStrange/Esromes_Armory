package net.esromethestrange.esromes_armory;

import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.datagen.*;
import net.esromethestrange.esromes_armory.datagen.lang.ArmoryEnglishLangProvider;
import net.esromethestrange.esromes_armory.datagen.tag.ArmoryBlockTagProvider;
import net.esromethestrange.esromes_armory.datagen.tag.ArmoryFluidTagProvider;
import net.esromethestrange.esromes_armory.datagen.tag.ArmoryItemTagProvider;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistryKeys;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;

public class EsromesArmoryDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ArmoryBlockLootTableProvider::new);

		pack.addProvider(ArmoryBlockTagProvider::new);
		pack.addProvider(ArmoryItemTagProvider::new);
		pack.addProvider(ArmoryFluidTagProvider::new);

		pack.addProvider(ArmoryModelProvider::new);
		pack.addProvider(ArmoryRecipeProvider::new);

		pack.addProvider(ArmoryEnglishLangProvider::new);

		pack.addProvider(ArmoryMaterialProvider::new);
		pack.addProvider(ArmoryHeatStateProvider::new);
		pack.addProvider(ArmoryMaterialIngredientProvider::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(ArmoryRegistryKeys.MATERIAL, Materials::bootstrap);
	}
}
