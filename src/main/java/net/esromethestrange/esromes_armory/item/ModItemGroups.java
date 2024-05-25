package net.esromethestrange.esromes_armory.item;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup ESROMES_ARMORY_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(EsromesArmory.MOD_ID, "esromes_armory"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.esromes_armory"))
                    .icon(()->new ItemStack(ModItems.STEEL_INGOT)).entries((displayContext, entries) -> {
                        entries.add(ModItems.STEEL_INGOT);
                        entries.add(ModBlocks.STEEL_BLOCK);
                    }).build());

    public static void RegisterItemGroups(){
        EsromesArmory.LOGGER.info(EsromesArmory.MOD_ID+": registering item groups...");
    }
}
