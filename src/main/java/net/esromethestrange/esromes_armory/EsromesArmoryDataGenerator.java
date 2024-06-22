package net.esromethestrange.esromes_armory;

import net.esromethestrange.esromes_armory.datagen.*;
import net.esromethestrange.esromes_armory.datagen.armory.ModMaterialProvider;
import net.esromethestrange.esromes_armory.datagen.lang.ModEnglishLangProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class EsromesArmoryDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModMaterialProvider::new);

		pack.addProvider(ModBlockLootTableProvider::new);
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ArmoryModelProvider::new);
		pack.addProvider(ModRecipeProvider::new);

		pack.addProvider(ModEnglishLangProvider::new);
	}
}
