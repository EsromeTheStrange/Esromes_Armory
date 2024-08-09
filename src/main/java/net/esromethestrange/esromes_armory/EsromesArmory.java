package net.esromethestrange.esromes_armory;

import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.esromethestrange.esromes_armory.block.entity.ArmoryBlockEntities;
import net.esromethestrange.esromes_armory.client.screen.ArmoryScreenHandlers;
import net.esromethestrange.esromes_armory.compat.config.EsromesArmoryConfig;
import net.esromethestrange.esromes_armory.data.heat.HeatDatas;
import net.esromethestrange.esromes_armory.data.material_ingredient.MaterialIngredientEntrySerializers;
import net.esromethestrange.esromes_armory.fluid.ArmoryFluids;
import net.esromethestrange.esromes_armory.item.ArmoryItemGroups;
import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.esromethestrange.esromes_armory.item.component.ArmoryComponents;
import net.esromethestrange.esromes_armory.recipe.ArmoryRecipes;
import net.esromethestrange.esromes_armory.recipe.ingredient.ArmoryIngredients;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistries;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EsromesArmory implements ModInitializer {
	public static final String MOD_ID = "esromes_armory";
    public static final Logger LOGGER = LoggerFactory.getLogger("Esrome's Armory");

	public static final EsromesArmoryConfig CONFIG = EsromesArmoryConfig.createAndLoad();

	@Override
	public void onInitialize() {
		LOGGER.info("Loading Esrome's Armory...");

		ArmoryRegistries.registerRegistries();

		ArmoryComponents.registerComponents();

		ArmoryItems.registerModItems();
		ArmoryBlocks.registerModBlocks();
		ArmoryBlockEntities.registerBlockEntities();
		ArmoryFluids.registerFluids();

		ArmoryRecipes.registerRecipes();
		ArmoryIngredients.registerIngredients();

		ArmoryItemGroups.registerItemGroups();
		ArmoryScreenHandlers.registerScreenHandlers();

		HeatDatas.registerHeatingResultSerializers();
		MaterialIngredientEntrySerializers.registerMaterialIngredientEntrySerializers();
	}
}