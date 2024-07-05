package net.esromethestrange.esromes_armory.data.datagen.lang;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.esromethestrange.esromes_armory.data.heat.HeatLevel;
import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.esromethestrange.esromes_armory.data.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.material.ArmoryMaterials;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ArmoryEnglishLangProvider extends FabricLanguageProvider {
    public ArmoryEnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        //Materials
        addTranslation(ArmoryMaterials.NONE, "Blank", translationBuilder);

        addTranslation(ArmoryMaterials.OAK, "Oak", translationBuilder);
        addTranslation(ArmoryMaterials.ACACIA, "Acacia", translationBuilder);
        addTranslation(ArmoryMaterials.BAMBOO, "Bamboo", translationBuilder);
        addTranslation(ArmoryMaterials.BIRCH, "Birch", translationBuilder);
        addTranslation(ArmoryMaterials.CHERRY, "Cherry", translationBuilder);
        addTranslation(ArmoryMaterials.CRIMSON, "Crimson", translationBuilder);
        addTranslation(ArmoryMaterials.DARK_OAK, "Dark Oak", translationBuilder);
        addTranslation(ArmoryMaterials.JUNGLE, "Jungle", translationBuilder);
        addTranslation(ArmoryMaterials.MANGROVE, "Mangrove", translationBuilder);
        addTranslation(ArmoryMaterials.SPRUCE, "Spruce", translationBuilder);
        addTranslation(ArmoryMaterials.WARPED, "Warped", translationBuilder);

        addTranslation(ArmoryMaterials.COPPER, "Copper", translationBuilder);
        addTranslation(ArmoryMaterials.IRON, "Iron", translationBuilder);
        addTranslation(ArmoryMaterials.GOLD, "Gold", translationBuilder);
        addTranslation(ArmoryMaterials.NETHERITE, "Netherite", translationBuilder);
        addTranslation(ArmoryMaterials.STEEL, "Steel", translationBuilder);

        addTranslation(ArmoryMaterials.STRING, "String", translationBuilder);
        addTranslation(ArmoryMaterials.SLIME, "Slime", translationBuilder);

        //Items
        translationBuilder.add(ArmoryItems.SHOVEL, " Shovel");
        translationBuilder.add(ArmoryItems.PICKAXE, " Pickaxe");
        translationBuilder.add(ArmoryItems.AXE, " Axe");
        translationBuilder.add(ArmoryItems.HOE, " Hoe");
        translationBuilder.add(ArmoryItems.SWORD, " Sword");

        translationBuilder.add(ArmoryItems.TOOL_HANDLE, " Tool Handle");
        translationBuilder.add(ArmoryItems.TOOL_BINDING, " Tool Binding");

        translationBuilder.add(ArmoryItems.SHOVEL_HEAD, " Shovel Head");
        translationBuilder.add(ArmoryItems.PICKAXE_HEAD, " Pickaxe Head");
        translationBuilder.add(ArmoryItems.AXE_HEAD, " Axe Head");
        translationBuilder.add(ArmoryItems.HOE_HEAD, " Hoe Head");

        translationBuilder.add(ArmoryItems.SWORD_GRIP, " Sword Grip");
        translationBuilder.add(ArmoryItems.SWORD_GUARD, " Sword Guard");
        translationBuilder.add(ArmoryItems.SWORD_BLADE, " Sword Blade");

        translationBuilder.add(ArmoryItems.STEEL_INGOT, "Steel Ingot");

        //Blocks
        translationBuilder.add(ArmoryBlocks.FORGE, "Forge");
        translationBuilder.add(ArmoryBlocks.WORKBENCH, "Workbench");

        translationBuilder.add(ArmoryBlocks.STEEL_BLOCK, "Block of Steel");
        translationBuilder.add(ArmoryBlocks.CHARCOAL_BLOCK, "Block of Charcoal");

        //Config
        addConfigTranslation("title", "Esrome's Armory", translationBuilder);

        addConfigTranslation("section.developerSettings", "Developer Settings", translationBuilder);
        addConfigTranslation("option.materialTooltips", "Material Tooltips", translationBuilder);
        addConfigTranslation("option.componentTooltips", "Material Tooltips", translationBuilder);

        //Compat
        addRecipeTypeTranslation("forging", "Forging", translationBuilder);

        //Heat Levels
        addTranslation(HeatLevel.ROOM_TEMPERATURE, "Room Temperature", translationBuilder);
        addTranslation(HeatLevel.HOT, "Hot", translationBuilder);
        addTranslation(HeatLevel.VERY_HOT, "Very Hot", translationBuilder);
        addTranslation(HeatLevel.RED, "Red", translationBuilder);
        addTranslation(HeatLevel.ORANGE, "Orange", translationBuilder);
        addTranslation(HeatLevel.YELLOW, "Yellow", translationBuilder);
        addTranslation(HeatLevel.WHITE, "White", translationBuilder);

        //Other
        translationBuilder.add("itemGroup.esromes_armory.esromes_armory", "Esrome's Armory");
        translationBuilder.add("itemGroup.esromes_armory.esromes_armory.tab.default", "Esrome's Armory");
        translationBuilder.add("itemGroup.esromes_armory.esromes_armory.tab.tools", "Tools");
        translationBuilder.add("itemGroup.esromes_armory.esromes_armory.tab.tool_components", "Components");
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

    public void addTranslation(HeatLevel heatLevel, String translation, TranslationBuilder translationBuilder){
        translationBuilder.add(heatLevel.translation_key, translation);
    }
}
