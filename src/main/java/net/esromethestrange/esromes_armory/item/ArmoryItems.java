package net.esromethestrange.esromes_armory.item;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.item.tools.*;
import net.esromethestrange.esromes_armory.data.material.MaterialTypes;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ArmoryItems {
    public static final Item STEEL_INGOT = registerItem("steel_ingot", new Item(new Item.Settings()));

    public static final Item SHOVEL_HEAD_MOLD = registerItem("shovel_head_mold", new Item(new Item.Settings()));
    public static final Item AXE_HEAD_MOLD = registerItem("axe_head_mold", new Item(new Item.Settings()));
    public static final Item HOE_HEAD_MOLD = registerItem("hoe_head_mold", new Item(new Item.Settings()));
    public static final Item SWORD_GUARD_MOLD = registerItem("sword_guard_mold", new Item(new Item.Settings()));
    public static final Item SWORD_BLADE_MOLD = registerItem("sword_blade_mold", new Item(new Item.Settings()));

    public static final Item TOOL_HANDLE = registerItem("tool_handle", new PartItem(new Item.Settings(), MaterialTypes.WOOD).setBaseFuelTime(100));
    public static final Item TOOL_BINDING = registerItem("tool_binding", new PartItem(new Item.Settings(), MaterialTypes.BINDING).setBaseFuelTime(100));

    public static final Item SHOVEL_HEAD = registerItem("shovel_head", new PartItem(new Item.Settings(), MaterialTypes.METAL));
    public static final Item PICKAXE_HEAD = registerItem("pickaxe_head", new PartItem(new Item.Settings(), MaterialTypes.METAL));
    public static final Item AXE_HEAD = registerItem("axe_head", new PartItem(new Item.Settings(), MaterialTypes.METAL));
    public static final Item HOE_HEAD = registerItem("hoe_head", new PartItem(new Item.Settings(), MaterialTypes.METAL));

    public static final Item SWORD_GRIP = registerItem("sword_grip", new PartItem(new Item.Settings(), MaterialTypes.WOOD));
    public static final Item SWORD_GUARD = registerItem("sword_guard", new PartItem(new Item.Settings(), MaterialTypes.METAL));
    public static final Item SWORD_BLADE = registerItem("sword_blade", new PartItem(new Item.Settings(), MaterialTypes.METAL));

    public static final Item SHOVEL = registerItem("shovel", new ArmoryShovelItem(new Item.Settings().maxCount(1)));
    public static final Item PICKAXE = registerItem("pickaxe", new ArmoryPickaxeItem(new Item.Settings().maxCount(1)));
    public static final Item AXE = registerItem("axe", new ArmoryAxeItem(new Item.Settings().maxCount(1)));
    public static final Item HOE = registerItem("hoe", new ArmoryHoeItem(new Item.Settings().maxCount(1)));
    public static final Item SWORD = registerItem("sword", new ArmorySwordItem(new Item.Settings().maxCount(1)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(EsromesArmory.MOD_ID, name), item);
    }

    public static void registerModItems() { }
}
