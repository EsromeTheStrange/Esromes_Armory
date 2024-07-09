package net.esromethestrange.esromes_armory.data.heat;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.recipe.ingredient.MaterialIngredient;
import net.esromethestrange.esromes_armory.recipe.ingredient.MaterialIngredientData;
import net.esromethestrange.esromes_armory.util.ResourceHelper;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class HeatResourceLoader implements SimpleSynchronousResourceReloadListener {
    private static HashMap<Identifier, HeatData> heatDataMap = new HashMap<>();

    @Override public Identifier getFabricId() { return Identifier.of(EsromesArmory.MOD_ID, "heat"); }

    @Override
    public void reload(ResourceManager manager) {
        EsromesArmory.LOGGER.info("Loading Heat Data...");

        heatDataMap.clear();
        for(Identifier id : manager.findResources("esrome/heat_states", i -> i.toString().endsWith(".json")).keySet()){
            HeatData heatData = ResourceHelper.readHeatData(id, manager);
            heatDataMap.put(heatData.id, heatData);
        }

        EsromesArmory.LOGGER.info(getHeatData(Identifier.of("esromes_armory:steel")).toString());
    }

    public static HeatData getHeatData(Identifier id){
        if(heatDataMap.containsKey(id))
            return heatDataMap.get(id);
        return HeatData.EMPTY;
    }
}
