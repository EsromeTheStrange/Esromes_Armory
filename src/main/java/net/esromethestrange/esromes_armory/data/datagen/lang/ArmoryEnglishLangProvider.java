package net.esromethestrange.esromes_armory.data.datagen.lang;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.esromethestrange.esromes_armory.compat.emi.EmiAnvilRecipe;
import net.esromethestrange.esromes_armory.data.heat.HeatLevel;
import net.esromethestrange.esromes_armory.fluid.ArmoryFluids;
import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
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
        addTranslation(Materials.NONE, "Blank", translationBuilder);

        addTranslation(Materials.OAK, "Oak", translationBuilder);
        addTranslation(Materials.ACACIA, "Acacia", translationBuilder);
        addTranslation(Materials.BAMBOO, "Bamboo", translationBuilder);
        addTranslation(Materials.BIRCH, "Birch", translationBuilder);
        addTranslation(Materials.CHERRY, "Cherry", translationBuilder);
        addTranslation(Materials.CRIMSON, "Crimson", translationBuilder);
        addTranslation(Materials.DARK_OAK, "Dark Oak", translationBuilder);
        addTranslation(Materials.JUNGLE, "Jungle", translationBuilder);
        addTranslation(Materials.MANGROVE, "Mangrove", translationBuilder);
        addTranslation(Materials.SPRUCE, "Spruce", translationBuilder);
        addTranslation(Materials.WARPED, "Warped", translationBuilder);

        addTranslation(Materials.COPPER, "Copper", translationBuilder);
        addTranslation(Materials.IRON, "Iron", translationBuilder);
        addTranslation(Materials.GOLD, "Gold", translationBuilder);
        addTranslation(Materials.NETHERITE, "Netherite", translationBuilder);
        addTranslation(Materials.STEEL, "Steel", translationBuilder);

        addTranslation(Materials.STRING, "String", translationBuilder);
        addTranslation(Materials.SLIME, "Slime", translationBuilder);

        //Items
        translationBuilder.add(ArmoryItems.SHOVEL, " Shovel");
        translationBuilder.add(ArmoryItems.PICKAXE, " Pickaxe");
        translationBuilder.add(ArmoryItems.AXE, " Axe");
        translationBuilder.add(ArmoryItems.HOE, " Hoe");
        translationBuilder.add(ArmoryItems.SWORD, " Sword");

        translationBuilder.add(ArmoryItems.BAR, " Bar");

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
        translationBuilder.add(ArmoryBlocks.WORKBENCH, "Workbench");
        translationBuilder.add(ArmoryBlocks.FORGE, "Forge");
        translationBuilder.add(ArmoryBlocks.ANVIL, "Anvil");
        translationBuilder.add(ArmoryBlocks.SMELTERY, "Smeltery");

        translationBuilder.add(ArmoryBlocks.STEEL_BLOCK, "Block of Steel");
        translationBuilder.add(ArmoryBlocks.CHARCOAL_BLOCK, "Block of Charcoal");

        //Fluids
        translationBuilder.add(ArmoryFluids.MOLTEN_STEEL_BUCKET, "Bucket of Molten Steel");
        translationBuilder.add(ArmoryFluids.MOLTEN_STEEL_BLOCK, "Molten Steel");
        translationBuilder.add(ArmoryFluids.MOLTEN_GOLD_BUCKET, "Bucket of Molten Gold");
        translationBuilder.add(ArmoryFluids.MOLTEN_GOLD_BLOCK, "Molten Gold");

        //Config
        addConfigTranslation("title", "Esrome's Armory", translationBuilder);

        addConfigTranslation("section.developerSettings", "Developer Settings", translationBuilder);
        addConfigTranslation("option.materialTooltips", "Material Tooltips", translationBuilder);
        addConfigTranslation("option.componentTooltips", "Material Tooltips", translationBuilder);

        //Compat
        addRecipeTypeTranslation("casting", "Casting", translationBuilder);
        addRecipeTypeTranslation("forging", "Forging", translationBuilder);
        addRecipeTypeTranslation("workbench", "Workbench", translationBuilder);
        addRecipeTypeTranslation("anvil", "Anvil", translationBuilder);

        translationBuilder.add(EmiAnvilRecipe.HEATING_TRANSLATION_KEY, "This is the required heat level of the input items. Heating can be done at the Forge.");

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
        translationBuilder.add("itemGroup.esromes_armory.esromes_armory.tab.fluids", "Fluids");
    }

    public void addTranslation(Material material, String materialName, TranslationBuilder translationBuilder){
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
