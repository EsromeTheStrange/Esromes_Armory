package net.esromethestrange.esromes_armory.material;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArmoryMaterials {
    private static final HashMap<Identifier, ArmoryMaterial> materials = new HashMap<>();
    private static final List<Identifier> materialIds = new ArrayList<>();

    public static final ArmoryMaterial NONE = new ArmoryMaterial(
            EsromesArmory.MOD_ID, "none", 0xffffff,
            10, 0, 1,
            0, 0, 1);

    //METALS
    public static final ArmoryMaterial IRON = new ArmoryMaterial("minecraft", "iron",
            0xb3b3b3, 100, 2, 1,
            6, 0, 20);

    public static final ArmoryMaterial STEEL = new ArmoryMaterial(EsromesArmory.MOD_ID, "steel",
            0x787878, 200, 2,100,
            8, 0, 50);


    //WOODS
    public static final ArmoryMaterial OAK = new ArmoryMaterial("minecraft", "oak",
            0xC29D62, 100, 2, 1,
            6, 0, 20);

    //BINDINGS
    public static final ArmoryMaterial STRING = new ArmoryMaterial("minecraft", "string",
            0xF0F0F0, 100, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterial SLIME = new ArmoryMaterial("minecraft", "slime",
            0x8CD782, 100, 2, 1,
            6, 0, 20);

    public static void registerMaterials(){
        addMaterial(NONE);

        addMaterial(IRON);
        addMaterial(STEEL);

        addMaterial(OAK);

        addMaterial(STRING);
        addMaterial(SLIME);
    }

    public static void addMaterial(ArmoryMaterial material){
        materialIds.add(material.id);
        materials.put(material.id, material);
    }
    public static ArmoryMaterial getMaterial(Identifier id){
        ArmoryMaterial material = materials.get(id);
        return material == null ? NONE : material;
    }
    public static List<Identifier> getMaterialIds(){
        return materialIds;
    }
}
