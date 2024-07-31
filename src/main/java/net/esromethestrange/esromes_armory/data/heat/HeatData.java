package net.esromethestrange.esromes_armory.data.heat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.heat.heating_result.FluidHeatingResult;
import net.esromethestrange.esromes_armory.data.heat.heating_result.HeatingResult;
import net.esromethestrange.esromes_armory.data.heat.heating_result.HeatingResults;
import net.esromethestrange.esromes_armory.data.heat.heating_result.ItemHeatingResult;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HeatData {
    public static final String HEAT_STATE_PATH = "armory/heat_states";

    public static final Codec<Pair<HeatLevel, HeatingResult>> ENTRY_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            HeatLevel.CODEC.fieldOf("temperature").forGetter(Pair::getLeft),
            HeatingResults.CODEC.fieldOf("result").forGetter(Pair::getRight)
    ).apply(instance, Pair::new));
    public static final Codec<HeatData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ENTRY_CODEC.listOf().fieldOf("entries").forGetter(HeatData::getPairedEntries)
    ).apply(instance, HeatData::new));

    public static HeatData EMPTY = new HeatData(Identifier.of(EsromesArmory.MOD_ID, "empty"));

    private final HashMap<HeatLevel, HeatingResult> heatingResults = new HashMap<>();
    public final Identifier id;

    public HeatData(Identifier id){
        this.id = id;
    }
    public HeatData(List<Pair<HeatLevel, HeatingResult>> entries){
        this(Identifier.of("esromes_armory:this"));
        for(Pair<HeatLevel, HeatingResult> entry : entries)
            addEntry(entry);
    }

    public List<Pair<HeatLevel, HeatingResult>> getPairedEntries(){
        List<Pair<HeatLevel, HeatingResult>> pairs = new ArrayList<>();
        for(HeatLevel level : heatingResults.keySet())
            pairs.add(new Pair<>(level, heatingResults.get(level)));
        return pairs;
    }

    public HeatData addEntry(Pair<HeatLevel, HeatingResult> entry){
        heatingResults.put(entry.getLeft(), entry.getRight());
        return this;
    }

    public HeatData addEntry(HeatLevel level, HeatingResult result){
        return addEntry(new Pair<>(level, result));
    }

    public HeatData addEntry(HeatLevel level, Item item){
        return addEntry(level, new ItemHeatingResult(item));
    }

    public HeatData addEntry(HeatLevel level, Fluid fluid, long amount){
        return addEntry(level, new FluidHeatingResult(fluid, amount));
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
