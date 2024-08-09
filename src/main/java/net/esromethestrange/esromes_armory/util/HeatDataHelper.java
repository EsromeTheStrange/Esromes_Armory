package net.esromethestrange.esromes_armory.util;

import net.esromethestrange.esromes_armory.data.heat.HeatData;
import net.esromethestrange.esromes_armory.data.heat.HeatLevel;
import net.esromethestrange.esromes_armory.data.heat.heating_result.HeatingResult;
import net.esromethestrange.esromes_armory.data.heat.heating_result.ItemHeatingResult;
import net.esromethestrange.esromes_armory.item.component.ArmoryComponents;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistryKeys;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;

public class HeatDataHelper {
    public static boolean hasTransition(DynamicRegistryManager registryManager, ItemStack stack){
        Registry<HeatData> registry = registryManager.get(ArmoryRegistryKeys.HEAT_DATA);
        for(HeatData heatData : registry.stream().toList()){
            if(!heatData.matches(stack.getItem()))
                continue;
            if(!(heatData.getResultFor(stack.get(ArmoryComponents.HEAT).getHeatLevel()) instanceof ItemHeatingResult itemHeatingResult))
                return true;
            if(itemHeatingResult.item.equals(stack.getItem()))
                return true;
        }
        return false;
    }

    public static HeatingResult getTransition(DynamicRegistryManager registryManager, ItemStack itemStack, HeatLevel heatLevel){
        Registry<HeatData> registry = registryManager.get(ArmoryRegistryKeys.HEAT_DATA);
        for(HeatData heatData : registry.stream().toList()){
            if(heatData.matches(itemStack.getItem()))
                return heatData.getResultFor(heatLevel);
        }
        return null;
    }
}
