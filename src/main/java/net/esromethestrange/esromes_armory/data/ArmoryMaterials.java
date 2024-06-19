package net.esromethestrange.esromes_armory.data;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class ArmoryMaterials {
    public static final ArmoryMaterialInfo STEEL = new ArmoryMaterialInfo(new Identifier(EsromesArmory.MOD_ID, "steel"),
            0x787878, 200, 2,100,
            8, 0, 50)
            .addItem("ingot", ModItems.STEEL_INGOT);
}
