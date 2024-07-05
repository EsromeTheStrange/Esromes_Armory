package net.esromethestrange.esromes_armory.compat.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.SectionHeader;
import net.esromethestrange.esromes_armory.EsromesArmory;

@Modmenu(modId=EsromesArmory.MOD_ID)
@Config(name=EsromesArmory.MOD_ID, wrapperName = "EsromesArmoryConfig")
public class EsromesArmoryConfigModel {
    @SectionHeader("developerSettings")
    public boolean materialTooltips = false;
    public boolean componentTooltips = false;
}
