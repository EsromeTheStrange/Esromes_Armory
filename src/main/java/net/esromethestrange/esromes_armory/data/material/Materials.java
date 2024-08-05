package net.esromethestrange.esromes_armory.data.material;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistryKeys;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class Materials {
    public static Materials INSTANCE = null;
    public final Registry<Material> registry;

    public static final RegistryKey<Material> NONE = of("none");

    public static final RegistryKey<Material> COPPER = of("copper");
    public static final RegistryKey<Material> GOLD = of("gold");
    public static final RegistryKey<Material> IRON = of("iron");
    public static final RegistryKey<Material> NETHERITE = of("netherite");
    public static final RegistryKey<Material> STEEL = of("steel");

    public static final RegistryKey<Material> ACACIA = of("acacia");
    public static final RegistryKey<Material> BAMBOO = of("bamboo");
    public static final RegistryKey<Material> BIRCH = of("birch");
    public static final RegistryKey<Material> CHERRY = of("cherry");
    public static final RegistryKey<Material> CRIMSON = of("crimson");
    public static final RegistryKey<Material> DARK_OAK = of("dark_oak");
    public static final RegistryKey<Material> JUNGLE = of("jungle");
    public static final RegistryKey<Material> MANGROVE = of("mangrove");
    public static final RegistryKey<Material> OAK = of("oak");
    public static final RegistryKey<Material> SPRUCE = of("spruce");
    public static final RegistryKey<Material> WARPED = of("warped");

    public static final RegistryKey<Material> STRING = of("string");
    public static final RegistryKey<Material> SLIME = of("slime");

    public Materials(Registry<Material> registry) {
        this.registry = registry;
    }

    public static void bootstrap(Registerable<Material> context){
        context.register(NONE, new Material(
                0xffffff, 1, 0, 1,
                0, 0, 1, 0
        ));

        context.register(COPPER, new Material(
                0xE77C56, 6, 1, 1,
                4, 0, 5, 0
        ));
        context.register(GOLD, new Material(
                0xE9B115, 4, 1, 1.2f,
                4, 0, 20, 0
        ));
        context.register(IRON, new Material(
                0xb3b3b3, 8, 2, 1,
                5, 0, 5, 0
        ));
        context.register(NETHERITE, new Material(
                0x4A2940, 12, 4, 1,
                7, 0, 5, 0
        ));
        context.register(STEEL, new Material(
                0x787878, 10, 2, 1,
                6, 0, 5, 0
        ));

        context.register(ACACIA, createVanillaWoodMaterial(0xBA6337));
        context.register(BAMBOO, createVanillaWoodMaterial(0xD3BB50));
        context.register(BIRCH, createVanillaWoodMaterial(0xD7C185));
        context.register(CHERRY, createVanillaWoodMaterial(0xE7C2BB));
        context.register(CRIMSON, createVanillaWoodMaterial(0x7E3A56));
        context.register(DARK_OAK, createVanillaWoodMaterial(0x4F3218));
        context.register(JUNGLE, createVanillaWoodMaterial(0xB88764));
        context.register(MANGROVE, createVanillaWoodMaterial(0x7F4234));
        context.register(OAK, createVanillaWoodMaterial(0xC29D62));
        context.register(SPRUCE, createVanillaWoodMaterial(0x82613A));
        context.register(WARPED, createVanillaWoodMaterial(0x398382));

        context.register(STRING, new Material(
                0xF0F0F0, 4, 0, 0,
                0, 0, 5, 0
        ));
        context.register(SLIME, new Material(
                0x8CD782, 5, 0, 0,
                0, 0, 5, 0
        ));
    }

    private static RegistryKey<Material> of(String id) {
        return RegistryKey.of(ArmoryRegistryKeys.MATERIAL, Identifier.of(EsromesArmory.MOD_ID, id));
    }

    private static Material createVanillaWoodMaterial(int color){
        return new Material(
                color, 2, 0, 0.1f,
                0, 0, 0, 1);
    }

    public static RegistryEntry<Material> get(RegistryKey<Material> key){
        if(INSTANCE == null) return null;
        return INSTANCE.getMaterial(key);
    }

    public RegistryEntry<Material> getMaterial(RegistryKey<Material> key){
        return this.registry.entryOf(key);
    }

    public static Identifier getId(Material material){
        if(INSTANCE == null) return Identifier.of(EsromesArmory.MOD_ID, "invalid");
        return INSTANCE.getMaterialId(material);
    }

    public Identifier getMaterialId(Material material){
        return registry.getId(material);
    }
}
