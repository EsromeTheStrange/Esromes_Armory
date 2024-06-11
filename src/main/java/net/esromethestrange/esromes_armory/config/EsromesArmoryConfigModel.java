package net.esromethestrange.esromes_armory.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import net.esromethestrange.esromes_armory.EsromesArmory;

@Modmenu(modId=EsromesArmory.MOD_ID)
@Config(name="esromes-armory", wrapperName = "EsromesArmoryConfig")
public class EsromesArmoryConfigModel {
    public int sillyNumber;
}
