package net.esromethestrange.esromes_armory.data;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class ArmoryMaterials {
    //METALS
    public static final ArmoryMaterialInfo IRON = new ArmoryMaterialInfo(new Identifier("minecraft", "iron"),
            0xb3b3b3, 100, 2, 1,
            6, 0, 20)
            .addItem("ingot", Items.IRON_INGOT);

    public static final ArmoryMaterialInfo STEEL = new ArmoryMaterialInfo(new Identifier(EsromesArmory.MOD_ID, "steel"),
            0x787878, 200, 2,100,
            8, 0, 50)
            .addItem("ingot", ModItems.STEEL_INGOT);


    //WOODS
    public static final ArmoryMaterialInfo OAK = new ArmoryMaterialInfo(new Identifier("minecraft", "oak"),
            0xC29D62, 100, 2, 1,
            6, 0, 20)
            .addItem("log", Blocks.OAK_LOG.asItem());
}
