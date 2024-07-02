package net.esromethestrange.esromes_armory.material;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialHandler;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.*;

public class ArmoryMaterials {
    private static final HashMap<Identifier, ArmoryMaterial> materials = new HashMap<>();
    private static final List<Identifier> materialIds = new ArrayList<>();
    private static final List<ArmoryMaterial> sortedMaterials = new ArrayList<>();

    public static final ArmoryMaterial NONE = new ArmoryMaterial(
            EsromesArmory.MOD_ID, "none", 0xffffff,
            10, 0, 1,
            0, 0, 1);

    //METALS
    public static final ArmoryMaterial COPPER = new ArmoryMaterial("minecraft", "copper",
            0xE77C56, 100, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterial IRON = new ArmoryMaterial("minecraft", "iron",
            0xb3b3b3, 100, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterial GOLD = new ArmoryMaterial("minecraft", "gold",
            0xE9B115, 100, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterial NETHERITE = new ArmoryMaterial("minecraft", "netherite",
            0x4A2940, 200, 2,100,
            8, 0, 50);
    public static final ArmoryMaterial STEEL = new ArmoryMaterial(EsromesArmory.MOD_ID, "steel",
            0x787878, 200, 2,100,
            8, 0, 50);


    //WOODS
    public static final ArmoryMaterial OAK = new ArmoryMaterial("minecraft", "oak",
            0xC29D62, 100, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterial ACACIA = new ArmoryMaterial("minecraft", "acacia",
            0xBA6337, 100, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterial BAMBOO = new ArmoryMaterial("minecraft", "bamboo",
            0xD3BB50, 100, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterial BIRCH = new ArmoryMaterial("minecraft", "birch",
            0xD7C185, 100, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterial CHERRY = new ArmoryMaterial("minecraft", "cherry",
            0xE7C2BB, 100, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterial CRIMSON = new ArmoryMaterial("minecraft", "crimson",
            0x7E3A56, 100, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterial DARK_OAK = new ArmoryMaterial("minecraft", "dark_oak",
            0x4F3218, 100, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterial JUNGLE = new ArmoryMaterial("minecraft", "jungle",
            0xB88764, 100, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterial MANGROVE = new ArmoryMaterial("minecraft", "mangrove",
            0x7F4234, 100, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterial SPRUCE = new ArmoryMaterial("minecraft", "spruce",
            0x82613A, 100, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterial WARPED = new ArmoryMaterial("minecraft", "warped",
            0x398382, 100, 2, 1,
            6, 0, 20);

    //BINDINGS
    public static final ArmoryMaterial STRING = new ArmoryMaterial("minecraft", "string",
            0xF0F0F0, 100, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterial SLIME = new ArmoryMaterial("minecraft", "slime",
            0x8CD782, 100, 2, 1,
            6, 0, 20);

    public static void addMaterial(ArmoryMaterial material){
        materialIds.add(material.id);
        materials.put(material.id, material);
        sortedMaterials.add(material);
    }
    public static ArmoryMaterial getMaterial(Identifier id){
        ArmoryMaterial material = materials.get(id);
        return material == null ? NONE : material;
    }
    public static List<Identifier> getMaterialIds(){
        return materialIds;
    }
    public static List<ArmoryMaterial> getMaterials() { return List.copyOf(sortedMaterials); }
}
