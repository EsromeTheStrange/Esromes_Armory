package net.esromethestrange.esromes_armory.data.material;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.minecraft.util.Identifier;

import java.util.*;

public class Materials {
    private static final HashMap<Identifier, Material> materials = new HashMap<>();
    private static final List<Identifier> materialIds = new ArrayList<>();
    private static final List<Material> sortedMaterials = new ArrayList<>();

    public static final Material NONE = new Material(
            EsromesArmory.MOD_ID, "none", 0xffffff,
            1, 0, 1,
            0, 0, 1, 0);

    //METALS
    public static final Material COPPER = new Material("minecraft", "copper",
            0xE77C56, 6, 1, 1,
            4, 0, 5, 0);
    public static final Material IRON = new Material("minecraft", "iron",
            0xb3b3b3, 8, 2, 1,
            5, 0, 5, 0);
    public static final Material GOLD = new Material("minecraft", "gold",
            0xE9B115, 4, 1, 1.2f,
            4, 0, 20, 0);
    public static final Material NETHERITE = new Material("minecraft", "netherite",
            0x4A2940, 12, 4,1,
            7, 0, 5, 0);
    public static final Material STEEL = new Material(EsromesArmory.MOD_ID, "steel",
            0x787878, 10, 2,1,
            6, 0, 5, 0);


    //WOODS
    public static final Material OAK = createWoodMaterial("oak");
    public static final Material ACACIA = createWoodMaterial("acacia");
    public static final Material BAMBOO = createWoodMaterial("bamboo");
    public static final Material BIRCH = createWoodMaterial("birch");
    public static final Material CHERRY = createWoodMaterial("cherry");
    public static final Material CRIMSON = createWoodMaterial("crimson");
    public static final Material DARK_OAK = createWoodMaterial("dark_oak");
    public static final Material JUNGLE = createWoodMaterial("jungle");
    public static final Material MANGROVE = createWoodMaterial("mangrove");
    public static final Material SPRUCE = createWoodMaterial("spruce");
    public static final Material WARPED = createWoodMaterial("warped");

    //BINDINGS
    public static final Material STRING = new Material("minecraft", "string",
            0xF0F0F0, 4, 2, 1,
            6, 0, 20, 0);
    public static final Material SLIME = new Material("minecraft", "slime",
            0x8CD782, 5, 0, 0,
            0, 0, 0, 0);

    private static Material createWoodMaterial(String materialName){
        return new Material("minecraft", materialName,
                0xC29D62, 2, 0, 0.1f,
                0, 0, 0, 1);
    }

    public static void addMaterial(Material material){
        materialIds.add(material.id);
        materials.put(material.id, material);
        sortedMaterials.add(material);
    }
    public static Material getMaterial(Identifier id){
        Material material = materials.get(id);
        return material == null ? NONE : material;
    }
    public static List<Identifier> getMaterialIds(){
        return materialIds;
    }
    public static List<Material> getMaterials() { return List.copyOf(sortedMaterials); }
}
