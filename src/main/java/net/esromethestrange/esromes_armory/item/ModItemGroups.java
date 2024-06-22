package net.esromethestrange.esromes_armory.item;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ModBlocks;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialHandler;
import net.esromethestrange.esromes_armory.item.material.ComponentBasedItem;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final String ESROMES_ARMORY_GROUP_TRANSLATION_KEY = "itemGroup.esromes_armory";

    public static final ItemGroup ESROMES_ARMORY_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(EsromesArmory.MOD_ID, "esromes_armory"),
            FabricItemGroup.builder().displayName(Text.translatable(ESROMES_ARMORY_GROUP_TRANSLATION_KEY))
                    .icon(()->new ItemStack(ModItems.STEEL_INGOT)).entries((displayContext, entries) -> {
                        entries.add(ModBlocks.FORGE);
                        entries.add(ModBlocks.WORKBENCH);

                        entries.add(ModBlocks.STEEL_BLOCK);
                        entries.add(ModBlocks.CHARCOAL_BLOCK);

                        entries.addAll(((ComponentBasedItem)ModItems.PICKAXE).getDefaultStacks());

                        entries.addAll(((MaterialItem)ModItems.TOOL_HANDLE).getDefaultStacks());
                        entries.addAll(((MaterialItem)ModItems.PICKAXE_HEAD).getDefaultStacks());

                        entries.add(ModItems.STEEL_INGOT);
                    }).build());

    public static void RegisterItemGroups() { }
}
