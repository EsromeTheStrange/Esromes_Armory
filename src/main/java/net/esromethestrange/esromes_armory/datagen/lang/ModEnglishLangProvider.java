package net.esromethestrange.esromes_armory.datagen.lang;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ForgeBlock;
import net.esromethestrange.esromes_armory.block.ModBlocks;
import net.esromethestrange.esromes_armory.block.entity.ForgeBlockEntity;
import net.esromethestrange.esromes_armory.block.entity.WorkbenchBlockEntity;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialInfo;
import net.esromethestrange.esromes_armory.data.ArmoryMaterials;
import net.esromethestrange.esromes_armory.item.ModItemGroups;
import net.esromethestrange.esromes_armory.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import java.nio.file.Path;

public class ModEnglishLangProvider extends FabricLanguageProvider {
    public ModEnglishLangProvider(FabricDataOutput dataOutput) { super(dataOutput, "en_us");  }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        //Materials
        addTranslation(ArmoryMaterial.NONE, "Blank", translationBuilder);

        addTranslation(ArmoryMaterials.OAK, "Oak", translationBuilder);

        addTranslation(ArmoryMaterials.IRON, "Iron", translationBuilder);
        addTranslation(ArmoryMaterials.STEEL, "Steel", translationBuilder);

        addTranslation(ArmoryMaterials.STRING, "String", translationBuilder);
        addTranslation(ArmoryMaterials.SLIME, "Slime", translationBuilder);

        //Items
        translationBuilder.add(ModItems.SHOVEL, " Shovel");
        translationBuilder.add(ModItems.PICKAXE, " Pickaxe");
        translationBuilder.add(ModItems.AXE, " Axe");
        translationBuilder.add(ModItems.HOE, " Hoe");
        translationBuilder.add(ModItems.SWORD, " Sword");

        translationBuilder.add(ModItems.TOOL_HANDLE, " Tool Handle");
        translationBuilder.add(ModItems.TOOL_BINDING, " Tool Binding");

        translationBuilder.add(ModItems.SHOVEL_HEAD, " Shovel Head");
        translationBuilder.add(ModItems.PICKAXE_HEAD, " Pickaxe Head");
        translationBuilder.add(ModItems.AXE_HEAD, " Axe Head");
        translationBuilder.add(ModItems.HOE_HEAD, " Hoe Head");
        translationBuilder.add(ModItems.SWORD_BLADE, " Sword Blade");

        translationBuilder.add(ModItems.STEEL_INGOT, "Steel Ingot");

        //Blocks
        translationBuilder.add(ModBlocks.FORGE, "Forge");
        translationBuilder.add(ModBlocks.WORKBENCH, "Workbench");

        translationBuilder.add(ModBlocks.STEEL_BLOCK, "Block of Steel");
        translationBuilder.add(ModBlocks.CHARCOAL_BLOCK, "Block of Charcoal");

        //Config
        addConfigTranslation("title", "Esrome's Armory", translationBuilder);

        addConfigTranslation("section.developerSettings", "Developer Settings", translationBuilder);
        addConfigTranslation("option.materialTooltips", "Material Tooltips", translationBuilder);
        addConfigTranslation("option.componentTooltips", "Material Tooltips", translationBuilder);

        //Compat
        addRecipeTypeTranslation("forging", "Forging", translationBuilder);

        //Other
        translationBuilder.add("itemGroup.esromes_armory.esromes_armory", "Esrome's Armory");
        translationBuilder.add("itemGroup.esromes_armory.esromes_armory.tab.default", "Esrome's Armory");
        translationBuilder.add("itemGroup.esromes_armory.esromes_armory.tab.tools", "Tools");
        translationBuilder.add("itemGroup.esromes_armory.esromes_armory.tab.tool_components", "Tool Components");

        translationBuilder.add(ForgeBlockEntity.CONTAINER_TRANSLATION_KEY, "Forge");
        translationBuilder.add(WorkbenchBlockEntity.CONTAINER_TRANSLATION_KEY, "Workbench");
    }

    public void addTranslation(ArmoryMaterialInfo material, String materialName, TranslationBuilder translationBuilder){
        translationBuilder.add(material.translatable_name, materialName);
    }
    public void addTranslation(ArmoryMaterial material, String materialName, TranslationBuilder translationBuilder){
        translationBuilder.add(material.translatable_name, materialName);
    }
    public void addConfigTranslation(String configKey, String translation, TranslationBuilder translationBuilder){
        translationBuilder.add("text.config." + EsromesArmory.MOD_ID + "." + configKey, translation);
    }
    public void addRecipeTypeTranslation(String recipeType, String translation, TranslationBuilder translationBuilder){
        translationBuilder.add("rei." + EsromesArmory.MOD_ID + "." + recipeType, translation);
        translationBuilder.add("emi.category." + EsromesArmory.MOD_ID + "." + recipeType, translation);
    }
}
