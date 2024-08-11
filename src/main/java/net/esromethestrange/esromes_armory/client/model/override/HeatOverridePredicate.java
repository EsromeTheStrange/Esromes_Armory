package net.esromethestrange.esromes_armory.client.model.override;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.esromethestrange.esromes_armory.data.heat.HeatLevel;
import net.esromethestrange.esromes_armory.item.component.ArmoryComponents;
import net.esromethestrange.esromes_armory.item.component.HeatComponent;
import net.minecraft.item.ItemStack;

import java.util.Optional;

/**
 * @author Based on code from Chime by emilyploszaj
 */
public class HeatOverridePredicate {
    public static ItemStack cachedStack = ItemStack.EMPTY;

    public static final Codec<HeatOverridePredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            HeatLevel.CODEC.optionalFieldOf("minimumHeat").forGetter(pred -> Optional.ofNullable(pred.minimumHeat)),
            HeatLevel.CODEC.optionalFieldOf("maximumHeat").forGetter(pred -> Optional.ofNullable(pred.maximumHeat))
    ).apply(instance, HeatOverridePredicate::new));

    public HeatOverridePredicate(Optional<HeatLevel> minimumHeat, Optional<HeatLevel> maximumHeat){
        this.minimumHeat = minimumHeat.orElse(null);
        this.maximumHeat = maximumHeat.orElse(null);
    }

    private final HeatLevel minimumHeat;
    private final HeatLevel maximumHeat;

    public boolean test(){
        HeatComponent heatComponent = cachedStack.get(ArmoryComponents.HEAT);
        if(heatComponent != null){
            float itemTemp = heatComponent.getTemperature();
            return  (minimumHeat == null || itemTemp >= minimumHeat.temperature) &&
                    (maximumHeat == null || itemTemp <= maximumHeat.temperature);
        }
        return false;
    }
}
