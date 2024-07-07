package net.esromethestrange.esromes_armory.data.heat;

import net.esromethestrange.esromes_armory.EsromesArmory;

public enum HeatLevel {
    ROOM_TEMPERATURE    (25,    0xFFFFFF),
    HOT                 (100,   0x941900),
    VERY_HOT            (500,   0xCC2200),
    RED                 (800,   0xFF2a00),
    ORANGE              (1000,  0xFF7B00),
    YELLOW              (1200,  0xFFdd00),
    WHITE               (1400,  0xFFF5B5);

    public final int temperature;
    public final int color;
    public final String translation_key;

    HeatLevel(int temperature, int color){
        this.temperature = temperature;
        this.color = color;
        translation_key = EsromesArmory.MOD_ID + ".temperature." + this;
    }

    public static HeatLevel getHeatLevel(float temperature){
        for(int i=0; i<values().length; i++){
            HeatLevel heatLevel = values()[i];
            if(temperature < heatLevel.temperature){
                if(i == 0) break;
                return values()[i-1];
            }
        }
        return HeatLevel.ROOM_TEMPERATURE;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
