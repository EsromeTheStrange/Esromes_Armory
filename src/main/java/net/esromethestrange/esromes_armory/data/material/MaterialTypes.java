package net.esromethestrange.esromes_armory.data.material;

import java.util.ArrayList;
import java.util.List;

public class MaterialTypes {
    public static List<Material> WOOD = new ArrayList<>();
    public static List<Material> METAL = new ArrayList<>();
    public static List<Material> BINDING = new ArrayList<>();

    public static void registerMaterialTypes(){
        WOOD.add(Materials.OAK);
        WOOD.add(Materials.ACACIA);
        WOOD.add(Materials.BAMBOO);
        WOOD.add(Materials.BIRCH);
        WOOD.add(Materials.CHERRY);
        WOOD.add(Materials.CRIMSON);
        WOOD.add(Materials.DARK_OAK);
        WOOD.add(Materials.JUNGLE);
        WOOD.add(Materials.MANGROVE);
        WOOD.add(Materials.SPRUCE);
        WOOD.add(Materials.WARPED);

        METAL.add(Materials.COPPER);
        METAL.add(Materials.IRON);
        METAL.add(Materials.GOLD);
        METAL.add(Materials.STEEL);
        METAL.add(Materials.NETHERITE);

        BINDING.add(Materials.STRING);
        BINDING.add(Materials.SLIME);
    }
}
