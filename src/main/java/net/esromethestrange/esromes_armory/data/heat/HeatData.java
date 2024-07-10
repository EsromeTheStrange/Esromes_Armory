package net.esromethestrange.esromes_armory.data.heat;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class HeatData {
    public static HeatData EMPTY = new HeatData(Identifier.of(EsromesArmory.MOD_ID, "empty"));

    private HashMap<HeatLevel, HeatingResult> heatingResults = new HashMap();
    public final Identifier id;

    public HeatData(Identifier id){
        this.id = id;
    }

    public void addEntry(HeatLevel heatLevel, HeatingResult heatingResult){
        heatingResults.put(heatLevel, heatingResult);
    }

    public HeatingResult getResultFor(HeatLevel heatLevel){
        HeatingResult heatingResult = new ItemHeatingResult(Items.AIR);
        for(HeatLevel compareLevel : HeatLevel.values()){
            if(compareLevel.temperature > heatLevel.temperature)
                break;
            if(heatingResults.containsKey(compareLevel))
                heatingResult = heatingResults.get(compareLevel);
        }
        return heatingResult;
    }

    public boolean matches(Object object){
        for(HeatingResult heatingResult : heatingResults.values())
            if(heatingResult.matches(object))
                return true;
        return false;
    }

    @Override
    public String toString() {
        if(heatingResults.isEmpty())
            return "\"" + id.toString() + "\": {}";

        StringBuilder output = new StringBuilder();
        for(HeatLevel heatLevel : heatingResults.keySet()){
            output.append(", ").append(heatLevel).append(": ").append(heatingResults.get(heatLevel));
        }
        output = new StringBuilder("{" + output.substring(2) + "}");
        return "\"" + id.toString() + "\": " + output.toString();
    }
}
