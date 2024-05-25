package net.esromethestrange.esromes_armory;

import net.esromethestrange.esromes_armory.block.ModBlocks;
import net.esromethestrange.esromes_armory.item.ModItemGroups;
import net.esromethestrange.esromes_armory.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EsromesArmory implements ModInitializer {
	public static final String MOD_ID = "esromes_armory";
    public static final Logger LOGGER = LoggerFactory.getLogger("esromes_armory");

	@Override
	public void onInitialize() {
		ModItemGroups.RegisterItemGroups();

		ModItems.RegisterModItems();
		ModBlocks.registerModBlocks();
	}
}