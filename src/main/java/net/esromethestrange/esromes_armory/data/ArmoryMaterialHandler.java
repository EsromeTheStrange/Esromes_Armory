package net.esromethestrange.esromes_armory.data;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.util.ResourceHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArmoryMaterialHandler implements SimpleSynchronousResourceReloadListener {
    private static HashMap<Identifier, ArmoryMaterialIngredientInfo> materialIngredients = new HashMap<>();

    @Override
    public Identifier getFabricId() {
        return new Identifier(EsromesArmory.MOD_ID, "material");
    }

    @Override
    public void reload(ResourceManager manager) {
        EsromesArmory.LOGGER.info("Loading Materials...");

        materialIngredients.clear();
        for(Identifier id : manager.findResources("esrome/material_ingredients", i -> i.toString().endsWith(".json")).keySet()){
            ArmoryMaterialIngredientInfo materialIngredientInfo = ResourceHelper.readMaterialIngredient(id, manager);
            materialIngredients.put(materialIngredientInfo.id, materialIngredientInfo);
        }
    }

    public static ArmoryMaterialIngredientInfo getMaterialIngredient(Identifier id){
        ArmoryMaterialIngredientInfo materialIngredient = materialIngredients.get(id);
        return materialIngredient == null ? ArmoryMaterialIngredientInfo.NONE : materialIngredient;
    }
}
