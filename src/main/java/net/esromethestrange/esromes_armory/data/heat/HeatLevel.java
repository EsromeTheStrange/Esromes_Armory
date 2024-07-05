package net.esromethestrange.esromes_armory.data.heat;

import net.esromethestrange.esromes_armory.EsromesArmory;

public enum HeatLevel {
    ROOM_TEMPERATURE(25),
    HOT(100),
    VERY_HOT(500),
    RED(800),
    ORANGE(1000),
    YELLOW(1200),
    WHITE(1400);

    public final float temperature;
    public final String translation_key;

    HeatLevel(float temperature){
        this.temperature = temperature;
        translation_key = EsromesArmory.MOD_ID + ".temperature." + this;
        EsromesArmory.LOGGER.info("Added new temperature: "+translation_key);
    }

    public static HeatLevel getHeatLevel(float temperature){
        for(HeatLevel heatLevel : HeatLevel.values()){
            if(temperature <= heatLevel.temperature)
                return heatLevel;
        }
        return HeatLevel.ROOM_TEMPERATURE;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
