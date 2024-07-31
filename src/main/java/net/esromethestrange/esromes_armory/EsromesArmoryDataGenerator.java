package net.esromethestrange.esromes_armory;

import net.esromethestrange.esromes_armory.data.datagen.*;
import net.esromethestrange.esromes_armory.data.datagen.lang.ArmoryEnglishLangProvider;
import net.esromethestrange.esromes_armory.data.datagen.tag.ArmoryBlockTagProvider;
import net.esromethestrange.esromes_armory.data.datagen.tag.ArmoryFluidTagProvider;
import net.esromethestrange.esromes_armory.data.datagen.tag.ArmoryItemTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

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

		pack.addProvider(ArmoryHeatStateProvider::new);
	}
}
