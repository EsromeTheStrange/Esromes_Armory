package net.esromethestrange.esromes_armory.data;

import com.mojang.serialization.Lifecycle;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.heat.heating_result.HeatingResult;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class ArmoryRegistries {
    public static final Registry<Material> MATERIAL = new SimpleRegistry<>(
            RegistryKey.ofRegistry(Identifier.of(EsromesArmory.MOD_ID, "material")), Lifecycle.stable());
    public static final Registry<HeatingResult.HeatingResultSerializer<? extends HeatingResult>> HEATING_RESULT_SERIALIZERS = new SimpleRegistry<>(
            RegistryKey.ofRegistry(Identifier.of(EsromesArmory.MOD_ID, "heating_result_serializer")), Lifecycle.stable());

    public static void registerRegistries() {}
}
