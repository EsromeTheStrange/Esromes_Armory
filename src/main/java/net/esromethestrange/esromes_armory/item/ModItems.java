package net.esromethestrange.esromes_armory.item;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.MaterialTypes;
import net.esromethestrange.esromes_armory.item.tools.ArmoryPickaxeItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item TOOL_HANDLE = registerItem("tool_handle", new ComponentItem(new FabricItemSettings(), MaterialTypes.WOOD));
    public static final Item PICKAXE_HEAD = registerItem("pickaxe_head", new ComponentItem(new FabricItemSettings(), MaterialTypes.METAL));

    public static final Item STEEL_INGOT = registerItem("steel_ingot", new Item(new FabricItemSettings()));

    public static final Item PICKAXE = registerItem("pickaxe", new ArmoryPickaxeItem(new FabricItemSettings().maxCount(1)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(EsromesArmory.MOD_ID, name), item);
    }

    public static void registerModItems(){ }
}
