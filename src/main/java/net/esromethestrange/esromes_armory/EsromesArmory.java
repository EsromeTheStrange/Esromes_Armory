package net.esromethestrange.esromes_armory;

import net.esromethestrange.esromes_armory.block.ModBlocks;
import net.esromethestrange.esromes_armory.block.entity.ModBlockEntities;
import net.esromethestrange.esromes_armory.config.EsromesArmoryConfig;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialHandler;
import net.esromethestrange.esromes_armory.item.ModItemGroups;
import net.esromethestrange.esromes_armory.item.ModItems;
import net.esromethestrange.esromes_armory.recipe.ModRecipes;
import net.esromethestrange.esromes_armory.recipe.ingredient.ModIngredients;
import net.esromethestrange.esromes_armory.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EsromesArmory implements ModInitializer {
	public static final String MOD_ID = "esromes_armory";
    public static final Logger LOGGER = LoggerFactory.getLogger("Esrome's Armory");

	public static final EsromesArmoryConfig CONFIG = EsromesArmoryConfig.createAndLoad();

	@Override
	public void onInitialize() {
		LOGGER.info("Loading Esrome's Armory...");
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new ArmoryMaterialHandler());

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();

		ModIngredients.registerIngredients();
		ModRecipes.registerRecipes();

		ModItemGroups.RegisterItemGroups();
		ModScreenHandlers.registerScreenHandlers();
	}
}