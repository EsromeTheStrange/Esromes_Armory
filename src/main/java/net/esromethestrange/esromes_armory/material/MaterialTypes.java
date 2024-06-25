package net.esromethestrange.esromes_armory.material;

import java.util.ArrayList;
import java.util.List;

public class MaterialTypes {
    public static List<ArmoryMaterial> WOOD = new ArrayList<>();
    public static List<ArmoryMaterial> METAL = new ArrayList<>();
    public static List<ArmoryMaterial> BINDING = new ArrayList<>();

    public static void registerMaterialTypes(){
        WOOD.add(ArmoryMaterials.OAK);

        METAL.add(ArmoryMaterials.COPPER);
        METAL.add(ArmoryMaterials.IRON);
        METAL.add(ArmoryMaterials.GOLD);
        METAL.add(ArmoryMaterials.STEEL);

        BINDING.add(ArmoryMaterials.STRING);
        BINDING.add(ArmoryMaterials.SLIME);
    }
}
