package net.esromethestrange.esromes_armory.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import net.esromethestrange.esromes_armory.EsromesArmory;

@Modmenu(modId=EsromesArmory.MOD_ID)
@Config(name=EsromesArmory.MOD_ID, wrapperName = "EsromesArmoryConfig")
public class EsromesArmoryConfigModel {
    public boolean developerMode = false;
}
