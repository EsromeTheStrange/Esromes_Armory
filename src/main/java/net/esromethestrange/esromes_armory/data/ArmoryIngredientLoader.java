package net.esromethestrange.esromes_armory.data;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.material_ingredient.MaterialIngredientData;
import net.esromethestrange.esromes_armory.util.ResourceHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class ArmoryIngredientLoader implements SimpleSynchronousResourceReloadListener {
    private static final HashMap<Identifier, MaterialIngredientData> materialIngredients = new HashMap<>();

    @Override
    public Identifier getFabricId() {
        return Identifier.of(EsromesArmory.MOD_ID, "ingredient");
    }

    @Override
    public void reload(ResourceManager manager) {
        materialIngredients.clear();
        for(Identifier id : manager.findResources(ArmoryData.MATERIAL_INGREDIENT_PATH, i -> i.toString().endsWith(".json")).keySet()){
            MaterialIngredientData ingredientData = ResourceHelper.readMaterialIngredient(id, manager);
            materialIngredients.put(ingredientData.id, ingredientData);
        }

        EsromesArmory.LOGGER.info("Loaded " + materialIngredients.size() + " material ingredients");
    }

    public static MaterialIngredientData getMaterialIngredient(Identifier id){
        if(materialIngredients.containsKey(id))
            return materialIngredients.get(id);
        return MaterialIngredientData.EMPTY;
    }
}
