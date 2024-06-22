package net.esromethestrange.esromes_armory.datagen.armory;

import net.esromethestrange.esromes_armory.data.ArmoryMaterials;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

public class ModMaterialProvider extends ArmoryMaterialProvider {
    public ModMaterialProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateMaterials() {
        createMaterial(ArmoryMaterials.IRON);
        createMaterial(ArmoryMaterials.STEEL);

        createMaterial(ArmoryMaterials.OAK);

        createMaterial(ArmoryMaterials.STRING);
        createMaterial(ArmoryMaterials.SLIME);
    }
}
