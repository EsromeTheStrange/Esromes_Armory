package net.esromethestrange.esromes_armory.data.heat.heating_result;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class HeatingResults {
    public static Codec<HeatingResult> CODEC;

    public static final Registry<HeatingResult.HeatingResultSerializer<? extends HeatingResult>> REGISTRY = new SimpleRegistry<>(
            RegistryKey.ofRegistry(Identifier.of(EsromesArmory.MOD_ID, "heating_result_serializer")), Lifecycle.stable());

    public static void registerHeatingResultSerializers(){
        Registry.register(REGISTRY, Identifier.of(EsromesArmory.MOD_ID, "item"), ItemHeatingResult.Serializer.INSTANCE);
        Registry.register(REGISTRY, Identifier.of(EsromesArmory.MOD_ID, "fluid"), FluidHeatingResult.Serializer.INSTANCE);

        CODEC = REGISTRY.getCodec().dispatch(
                "type",
                HeatingResult::getSerializer,
                HeatingResult.HeatingResultSerializer::createCodec
        );
    }
}
