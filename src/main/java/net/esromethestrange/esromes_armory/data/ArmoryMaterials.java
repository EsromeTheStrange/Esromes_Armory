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
            6, 0, 20);

    public static final ArmoryMaterialInfo STEEL = new ArmoryMaterialInfo(new Identifier(EsromesArmory.MOD_ID, "steel"),
            0x787878, 200, 2,100,
            8, 0, 50);


    //WOODS
    public static final ArmoryMaterialInfo OAK = new ArmoryMaterialInfo(new Identifier("minecraft", "oak"),
            0xC29D62, 100, 2, 1,
            6, 0, 20);

    //BINDINGS
    public static final ArmoryMaterialInfo STRING = new ArmoryMaterialInfo(new Identifier("minecraft", "string"),
            0xF0F0F0, 100, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterialInfo SLIME = new ArmoryMaterialInfo(new Identifier("minecraft", "slime"),
            0x8CD782, 100, 2, 1,
            6, 0, 20);
}
