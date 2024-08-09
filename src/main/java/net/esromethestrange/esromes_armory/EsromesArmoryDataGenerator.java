package net.esromethestrange.esromes_armory;

import net.esromethestrange.esromes_armory.data.heat.HeatDatas;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.data.material_ingredient.MaterialIngredients;
import net.esromethestrange.esromes_armory.datagen.ArmoryBlockLootTableProvider;
import net.esromethestrange.esromes_armory.datagen.ArmoryCustomProviders;
import net.esromethestrange.esromes_armory.datagen.ArmoryModelProvider;
import net.esromethestrange.esromes_armory.datagen.ArmoryRecipeProvider;
import net.esromethestrange.esromes_armory.datagen.lang.ArmoryEnglishLangProvider;
import net.esromethestrange.esromes_armory.datagen.tag.ArmoryBlockTagProvider;
import net.esromethestrange.esromes_armory.datagen.tag.ArmoryFluidTagProvider;
import net.esromethestrange.esromes_armory.datagen.tag.ArmoryItemTagProvider;
import net.esromethestrange.esromes_armory.datagen.tag.ArmoryMaterialTagProvider;
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
		pack.addProvider(ArmoryMaterialTagProvider::new);

		pack.addProvider(ArmoryModelProvider::new);
		pack.addProvider(ArmoryRecipeProvider::new);

		pack.addProvider(ArmoryEnglishLangProvider::new);

		pack.addProvider(ArmoryCustomProviders::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(ArmoryRegistryKeys.MATERIAL, Materials::bootstrap);
		registryBuilder.addRegistry(ArmoryRegistryKeys.MATERIAL_INGREDIENT_DATA, MaterialIngredients::bootstrap);
		registryBuilder.addRegistry(ArmoryRegistryKeys.HEAT_DATA, HeatDatas::bootstrap);
	}
}
