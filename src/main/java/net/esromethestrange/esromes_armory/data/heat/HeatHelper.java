package net.esromethestrange.esromes_armory.data.heat;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.util.ResourceHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class HeatHelper implements SimpleSynchronousResourceReloadListener {
    private static HashMap<Identifier, HeatData> heatDataMap = new HashMap<>();

    @Override public Identifier getFabricId() { return Identifier.of(EsromesArmory.MOD_ID, "heat"); }

    @Override
    public void reload(ResourceManager manager) {
        heatDataMap.clear();
        for(Identifier id : manager.findResources("esrome/heat_states", i -> i.toString().endsWith(".json")).keySet()){
            HeatData heatData = ResourceHelper.readHeatData(id, manager);
            heatDataMap.put(heatData.id, heatData);
        }

        EsromesArmory.LOGGER.info("Loaded " + heatDataMap.size() + " heat states");
    }

    public static HeatData getHeatData(Item item){
        for(HeatData heatData : heatDataMap.values()){
            if (heatData.matches(item))
                return heatData;
        }
        return HeatData.EMPTY;
    }
}
