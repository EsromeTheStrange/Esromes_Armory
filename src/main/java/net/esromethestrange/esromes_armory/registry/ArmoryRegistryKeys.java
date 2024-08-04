package net.esromethestrange.esromes_armory.registry;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ArmoryRegistryKeys {
    public static final RegistryKey<Registry<Material>> MATERIAL = of("material");

    private static <T> RegistryKey<Registry<T>> of(String name){
        return RegistryKey.ofRegistry(Identifier.of(EsromesArmory.MOD_ID, name));
    }
}
