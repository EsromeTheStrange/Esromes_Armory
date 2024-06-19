package net.esromethestrange.esromes_armory.datagen.lang;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import java.nio.file.Path;

public class ModEnglishLangProvider extends FabricLanguageProvider {
    public ModEnglishLangProvider(FabricDataOutput dataOutput) { super(dataOutput, "en_us");  }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        try {
            Path existingFilePath = dataOutput.getModContainer().findPath("assets/"+ EsromesArmory.MOD_ID+"/lang/en_us.existing.json").get();
            translationBuilder.add(existingFilePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add existing language file!", e);
        }
    }
}
