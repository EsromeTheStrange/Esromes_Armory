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
            0xE77C56, 6, 1, 1,
            4, 0, 5);
    public static final ArmoryMaterial IRON = new ArmoryMaterial("minecraft", "iron",
            0xb3b3b3, 8, 2, 1,
            5, 0, 5);
    public static final ArmoryMaterial GOLD = new ArmoryMaterial("minecraft", "gold",
            0xE9B115, 4, 1, 1.2f,
            4, 0, 20);
    public static final ArmoryMaterial NETHERITE = new ArmoryMaterial("minecraft", "netherite",
            0x4A2940, 12, 4,1,
            7, 0, 5);
    public static final ArmoryMaterial STEEL = new ArmoryMaterial(EsromesArmory.MOD_ID, "steel",
            0x787878, 10, 2,1,
            6, 0, 5);


    //WOODS
    public static final ArmoryMaterial OAK = createWoodMaterial("oak");
    public static final ArmoryMaterial ACACIA = createWoodMaterial("acacia");
    public static final ArmoryMaterial BAMBOO = createWoodMaterial("bamboo");
    public static final ArmoryMaterial BIRCH = createWoodMaterial("birch");
    public static final ArmoryMaterial CHERRY = createWoodMaterial("cherry");
    public static final ArmoryMaterial CRIMSON = createWoodMaterial("crimson");
    public static final ArmoryMaterial DARK_OAK = createWoodMaterial("dark_oak");
    public static final ArmoryMaterial JUNGLE = createWoodMaterial("jungle");
    public static final ArmoryMaterial MANGROVE = createWoodMaterial("mangrove");
    public static final ArmoryMaterial SPRUCE = createWoodMaterial("spruce");
    public static final ArmoryMaterial WARPED = createWoodMaterial("warped");

    //BINDINGS
    public static final ArmoryMaterial STRING = new ArmoryMaterial("minecraft", "string",
            0xF0F0F0, 4, 2, 1,
            6, 0, 20);
    public static final ArmoryMaterial SLIME = new ArmoryMaterial("minecraft", "slime",
            0x8CD782, 5, 0, 0,
            0, 0, 0);

    private static ArmoryMaterial createWoodMaterial(String materialName){
        return new ArmoryMaterial("minecraft", materialName,
                0xC29D62, 2, 0, 0.1f,
                0, 0, 0);
    }

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
