package net.esromethestrange.esromes_armory.material;

import net.minecraft.util.Arm;

import java.util.ArrayList;
import java.util.List;

public class MaterialTypes {
    public static List<ArmoryMaterial> WOOD = new ArrayList<>();
    public static List<ArmoryMaterial> METAL = new ArrayList<>();
    public static List<ArmoryMaterial> BINDING = new ArrayList<>();

    public static void registerMaterialTypes(){
        WOOD.add(ArmoryMaterials.OAK);
        WOOD.add(ArmoryMaterials.ACACIA);
        WOOD.add(ArmoryMaterials.BAMBOO);
        WOOD.add(ArmoryMaterials.BIRCH);
        WOOD.add(ArmoryMaterials.CHERRY);
        WOOD.add(ArmoryMaterials.CRIMSON);
        WOOD.add(ArmoryMaterials.DARK_OAK);
        WOOD.add(ArmoryMaterials.JUNGLE);
        WOOD.add(ArmoryMaterials.MANGROVE);
        WOOD.add(ArmoryMaterials.SPRUCE);
        WOOD.add(ArmoryMaterials.WARPED);

        METAL.add(ArmoryMaterials.COPPER);
        METAL.add(ArmoryMaterials.IRON);
        METAL.add(ArmoryMaterials.GOLD);
        METAL.add(ArmoryMaterials.STEEL);
        METAL.add(ArmoryMaterials.NETHERITE);

        BINDING.add(ArmoryMaterials.STRING);
        BINDING.add(ArmoryMaterials.SLIME);
    }
}
