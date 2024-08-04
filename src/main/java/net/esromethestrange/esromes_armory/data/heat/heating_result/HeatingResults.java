package net.esromethestrange.esromes_armory.data.heat.heating_result;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class HeatingResults {

    public static void registerHeatingResultSerializers(){
        register("item", ItemHeatingResult.Serializer.INSTANCE);
        register("fluid", FluidHeatingResult.Serializer.INSTANCE);

        HeatingResult.initializeCodec();
    }

    private static void register(String name, HeatingResult.HeatingResultSerializer<?> heatingResultSerializer){
        Registry.register(ArmoryRegistries.HEATING_RESULT_SERIALIZERS, Identifier.of(EsromesArmory.MOD_ID, name), heatingResultSerializer);
    }
}
