package net.esromethestrange.esromes_armory;

import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.esromethestrange.esromes_armory.block.entity.ArmoryBlockEntities;
import net.esromethestrange.esromes_armory.client.screen.ArmoryScreenHandlers;
import net.esromethestrange.esromes_armory.compat.config.EsromesArmoryConfig;
import net.esromethestrange.esromes_armory.data.ArmoryIngredientLoader;
import net.esromethestrange.esromes_armory.data.ArmoryRegistries;
import net.esromethestrange.esromes_armory.data.HeatDataLoader;
import net.esromethestrange.esromes_armory.data.component.ArmoryComponents;
import net.esromethestrange.esromes_armory.data.heat.heating_result.HeatingResults;
import net.esromethestrange.esromes_armory.data.material.MaterialTypes;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.fluid.ArmoryFluids;
import net.esromethestrange.esromes_armory.item.ArmoryItemGroups;
import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.esromethestrange.esromes_armory.recipe.ArmoryRecipes;
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

		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new ArmoryIngredientLoader());
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new HeatDataLoader());

		ArmoryRegistries.registerRegistries();

		ArmoryComponents.registerComponents();

		MaterialTypes.registerMaterialTypes();

		ArmoryItems.registerModItems();
		ArmoryBlocks.registerModBlocks();
		ArmoryBlockEntities.registerBlockEntities();
		ArmoryFluids.registerFluids();

		ArmoryIngredientLoader.registerIngredients();
		ArmoryRecipes.registerRecipes();

		ArmoryItemGroups.registerItemGroups();
		ArmoryScreenHandlers.registerScreenHandlers();

		HeatingResults.registerHeatingResultSerializers();
		Materials.registerMaterials();
	}
}