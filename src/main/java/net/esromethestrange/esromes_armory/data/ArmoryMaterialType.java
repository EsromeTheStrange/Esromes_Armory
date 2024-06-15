package net.esromethestrange.esromes_armory.data;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ArmoryMaterialType {
    public static final ArmoryMaterialType NONE = new ArmoryMaterialType(EsromesArmory.MOD_ID, "none", new ArrayList<Identifier>());

    public final String translatable_name;
    public final Identifier id;
    public final String modId;
    public final String materialName;

    public final List<Identifier> materials;
    public final List<ArmoryMaterial> armoryMaterials;

    public ArmoryMaterialType(String modId, String materialName, List<Identifier> materials){
        this.modId = modId;
        this.materialName = materialName;
        translatable_name = modId + ".material_type." + materialName;
        id = new Identifier(modId, materialName);

        this.materials = materials;
        armoryMaterials = new ArrayList<>();
        for(Identifier material : materials)
            armoryMaterials.add(MaterialHandler.getMaterial(material));
    }

    public List<ArmoryMaterial> getMaterials() {
        return armoryMaterials;
    }
}
