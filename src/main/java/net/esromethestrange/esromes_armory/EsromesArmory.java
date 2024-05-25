package net.esromethestrange.esromes_armory;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EsromesArmory implements ModInitializer {
	public static final String MOD_ID = "esromes_armory";
    public static final Logger LOGGER = LoggerFactory.getLogger("esromes_armory");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
	}
}