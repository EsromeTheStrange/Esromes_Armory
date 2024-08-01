package net.esromethestrange.esromes_armory.data.material;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Materials {
    public static final Material NONE = register("none",
            new Material(0xffffff, 1,
                    0, 1,
                    0, 0,
                    1, 0)
    );

    //METALS
    public static final Material COPPER = registerVanilla("copper",
            new Material(0xE77C56, 6,
                    1, 1,
                    4, 0,
                    5, 0)
    );
    public static final Material IRON = registerVanilla("iron",
            new Material(0xb3b3b3, 8,
                    2, 1,
                    5, 0,
                    5, 0)
    );
    public static final Material GOLD = registerVanilla("gold",
            new Material(0xE9B115, 4,
                    1, 1.2f,
                    4, 0,
                    20, 0)
    );
    public static final Material NETHERITE = registerVanilla("netherite",
            new Material(0x4A2940, 12,
                    4,1,
                    7, 0,
                    5, 0)
    );
    public static final Material STEEL = register("steel",
            new Material(0x787878, 10,
                    2,1,
                    6, 0,
                    5, 0)
    );


    //WOODS
    public static final Material OAK = createVanillaWoodMaterial("oak", 0xC29D62);
    public static final Material ACACIA = createVanillaWoodMaterial("acacia", 0xBA6337);
    public static final Material BAMBOO = createVanillaWoodMaterial("bamboo", 0xD3BB50);
    public static final Material BIRCH = createVanillaWoodMaterial("birch", 0xD7C185);
    public static final Material CHERRY = createVanillaWoodMaterial("cherry", 0xE7C2BB);
    public static final Material CRIMSON = createVanillaWoodMaterial("crimson", 0x7E3A56);
    public static final Material DARK_OAK = createVanillaWoodMaterial("dark_oak", 0x4F3218);
    public static final Material JUNGLE = createVanillaWoodMaterial("jungle", 0xB88764);
    public static final Material MANGROVE = createVanillaWoodMaterial("mangrove", 0x7F4234);
    public static final Material SPRUCE = createVanillaWoodMaterial("spruce", 0x82613A);
    public static final Material WARPED = createVanillaWoodMaterial("warped", 0x398382);

    //BINDINGS
    public static final Material STRING = registerVanilla("string",
            new Material(0xF0F0F0, 4,
                    2, 1,
                    6, 0,
                    20, 0)
    );
    public static final Material SLIME = registerVanilla("slime",
            new Material(0x8CD782, 5,
                    0, 0,
                    0, 0,
                    0, 0)
    );

    private static Material createVanillaWoodMaterial(String materialName, int color){
        return registerVanilla(materialName, new Material(
                color, 2, 0, 0.1f,
                0, 0, 0, 1));
    }

    public static Material registerVanilla(String name, Material material){
        return register("minecraft", name, material);
    }
    public static Material register(String name, Material material){
        return register(EsromesArmory.MOD_ID, name, material);
    }
    public static Material register(String modId, String name, Material material){
        return Registry.register(ArmoryRegistries.MATERIAL, Identifier.of(modId, name), material);
    }

    public static void registerMaterials(){}
}
