package net.esromethestrange.esromes_armory;

import net.esromethestrange.esromes_armory.block.ModBlocks;
import net.esromethestrange.esromes_armory.block.entity.forge.ModBlockEntities;
import net.esromethestrange.esromes_armory.config.EsromesArmoryConfig;
import net.esromethestrange.esromes_armory.data.MaterialHandler;
import net.esromethestrange.esromes_armory.item.ModItemGroups;
import net.esromethestrange.esromes_armory.item.ModItems;
import net.esromethestrange.esromes_armory.recipe.ModRecipes;
import net.esromethestrange.esromes_armory.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class EsromesArmory implements ModInitializer {
	public static final String MOD_ID = "esromes_armory";
    public static final Logger LOGGER = LoggerFactory.getLogger("Esrome's Armory");

	public static final EsromesArmoryConfig CONFIG = EsromesArmoryConfig.createAndLoad();

	@Override
	public void onInitialize() {
		LOGGER.info("Loading Esrome's Armory...");
		ModItemGroups.RegisterItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();

		ModScreenHandlers.registerScreenHandlers();

		ModRecipes.registerRecipes();

		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new MaterialHandler());
	}
}