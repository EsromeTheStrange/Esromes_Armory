package net.esromethestrange.esromes_armory;

import net.esromethestrange.esromes_armory.block.ModBlocks;
import net.esromethestrange.esromes_armory.block.entity.forge.ModBlockEntities;
import net.esromethestrange.esromes_armory.config.EsromesArmoryConfig;
import net.esromethestrange.esromes_armory.item.ModItemGroups;
import net.esromethestrange.esromes_armory.item.ModItems;
import net.esromethestrange.esromes_armory.recipe.ModRecipes;
import net.esromethestrange.esromes_armory.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EsromesArmory implements ModInitializer {
	public static final String MOD_ID = "esromes_armory";
    public static final Logger LOGGER = LoggerFactory.getLogger("Esrome's Armory");

	public static final EsromesArmoryConfig CONFIG = EsromesArmoryConfig.createAndLoad();

	@Override
	public void onInitialize() {
		ModItemGroups.RegisterItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();

		ModScreenHandlers.registerScreenHandlers();

		ModRecipes.registerRecipes();
	}
}