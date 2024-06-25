package net.esromethestrange.esromes_armory.item;

import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ModBlocks;
import net.esromethestrange.esromes_armory.item.material.ComponentBasedItem;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final OwoItemGroup ESROMES_ARMORY = OwoItemGroup
            .builder(new Identifier(EsromesArmory.MOD_ID, "esromes_armory"), () -> Icon.of(ModItems.STEEL_INGOT))
            // additional builder configuration goes between these lines
            .build();

    public static void registerItemGroups() {
        ESROMES_ARMORY.addCustomTab(Icon.of(ModBlocks.FORGE), "default", (context, entries) ->{
            entries.add(ModBlocks.FORGE);
            entries.add(ModBlocks.WORKBENCH);

            entries.add(ModBlocks.CHARCOAL_BLOCK);
            entries.add(ModBlocks.STEEL_BLOCK);

            entries.add(ModItems.STEEL_INGOT);
        }, true);

        ESROMES_ARMORY.addCustomTab(Icon.of(ModItems.PICKAXE), "tools", (context, entries) ->{
            entries.addAll(((ComponentBasedItem)ModItems.SHOVEL).getDefaultStacks());
            entries.addAll(((ComponentBasedItem)ModItems.PICKAXE).getDefaultStacks());
            entries.addAll(((ComponentBasedItem)ModItems.AXE).getDefaultStacks());
            entries.addAll(((ComponentBasedItem)ModItems.HOE).getDefaultStacks());
            entries.addAll(((ComponentBasedItem)ModItems.SWORD).getDefaultStacks());
        }, false);

        ESROMES_ARMORY.addCustomTab(Icon.of(ModItems.PICKAXE_HEAD), "tool_components", (context, entries) ->{
            entries.addAll(((MaterialItem)ModItems.TOOL_HANDLE).getDefaultStacks());
            entries.addAll(((MaterialItem)ModItems.TOOL_BINDING).getDefaultStacks());

            entries.addAll(((MaterialItem)ModItems.SHOVEL_HEAD).getDefaultStacks());
            entries.addAll(((MaterialItem)ModItems.PICKAXE_HEAD).getDefaultStacks());
            entries.addAll(((MaterialItem)ModItems.AXE_HEAD).getDefaultStacks());
            entries.addAll(((MaterialItem)ModItems.HOE_HEAD).getDefaultStacks());
            entries.addAll(((MaterialItem)ModItems.SWORD_BLADE).getDefaultStacks());
        }, false);

        ESROMES_ARMORY.initialize();
    }
}
