package net.esromethestrange.esromes_armory.item;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item STEEL_INGOT = registerItem("steel_ingot", new Item(new FabricItemSettings()));

    private static void AddItemsToItemGroup(FabricItemGroupEntries entries){
        entries.add(STEEL_INGOT);
    }

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(EsromesArmory.MOD_ID, name), item);
    }

    public static void registerModItems(){
        EsromesArmory.LOGGER.info("Registering items...");
    }
}