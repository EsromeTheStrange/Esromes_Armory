package net.esromethestrange.esromes_armory.data;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ArmoryMaterialType {
    public static final ArmoryMaterialType NONE = new ArmoryMaterialType(EsromesArmory.MOD_ID, "none", new ArrayList<String>());

    public final String translatable_name;
    public final Identifier id;
    public final String modId;
    public final String materialName;

    public final List<String> materials;

    public ArmoryMaterialType(String modId, String materialName, List<String> materials){
        this.modId = modId;
        this.materialName = materialName;
        translatable_name = modId + ".material_type." + materialName;
        id = new Identifier(modId, materialName);

        this.materials = materials;
    }

    public boolean contains(ArmoryMaterial material){
        return materials.contains(material.id.toString());
    }
}
