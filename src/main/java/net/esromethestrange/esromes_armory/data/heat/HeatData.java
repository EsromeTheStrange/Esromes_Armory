package net.esromethestrange.esromes_armory.data.heat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.esromethestrange.esromes_armory.data.heat.heating_result.HeatingResult;
import net.esromethestrange.esromes_armory.data.heat.heating_result.ItemHeatingResult;
import net.minecraft.item.Items;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HeatData {
    public static final Codec<Pair<HeatLevel, HeatingResult>> PAIR_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            HeatLevel.CODEC.fieldOf("temperature").forGetter(Pair::getLeft),
            HeatingResult.CODEC.fieldOf("result").forGetter(Pair::getRight)
    ).apply(instance, Pair::new));
    public static final Codec<HeatData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            PAIR_CODEC.listOf().fieldOf("entries").forGetter(HeatData::getPairedEntries)
    ).apply(instance, HeatData::new));

    private final HashMap<HeatLevel, HeatingResult> heatingResults = new HashMap<>();

    public HeatData(List<Pair<HeatLevel, HeatingResult>> entries){
        for(Pair<HeatLevel, HeatingResult> entry : entries)
            addEntry(entry);
    }

    public List<Pair<HeatLevel, HeatingResult>> getPairedEntries(){
        List<Pair<HeatLevel, HeatingResult>> pairs = new ArrayList<>();
        for(HeatLevel level : heatingResults.keySet())
            pairs.add(new Pair<>(level, heatingResults.get(level)));
        return pairs;
    }

    public void addEntry(Pair<HeatLevel, HeatingResult> entry){
        heatingResults.put(entry.getLeft(), entry.getRight());
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
}
