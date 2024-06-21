package net.esromethestrange.esromes_armory.data;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.util.ArmoryResourceHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class ArmoryMaterialHandler implements SimpleSynchronousResourceReloadListener {
    private static HashMap<Identifier, ArmoryMaterial> materials = new HashMap<>();

    private static HashMap<Identifier, ArmoryMaterialType> materialTypes = new HashMap<>();

    @Override
    public Identifier getFabricId() {
        return new Identifier(EsromesArmory.MOD_ID, "material");
    }

    @Override
    public void reload(ResourceManager manager) {
        EsromesArmory.LOGGER.info("Loading Materials...");

        materials.clear();
        for(Identifier id : manager.findResources("esrome/materials", i -> i.toString().endsWith(".json")).keySet()){
            ArmoryMaterial material = ArmoryResourceHelper.readMaterial(id, manager);
            materials.put(material.id, material);
        }

        materialTypes.clear();
        for(Identifier id : manager.findResources("esrome/material_types", i -> i.toString().endsWith(".json")).keySet()){
            ArmoryMaterialType materialType = ArmoryResourceHelper.readMaterialType(id, manager);
            materialTypes.put(materialType.id, materialType);
        }

    }

    public static ArmoryMaterial getMaterial(Identifier id) {
        ArmoryMaterial material = materials.get(id);
        return material == null ? ArmoryMaterial.NONE : material;
    }
    public static ArmoryMaterialType getMaterialType(Identifier id){
        ArmoryMaterialType materialType = materialTypes.get(id);
        return materialType == null ? ArmoryMaterialType.NONE : materialType;
    }
}
