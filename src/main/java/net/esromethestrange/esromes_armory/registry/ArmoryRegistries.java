package net.esromethestrange.esromes_armory.registry;

import com.mojang.serialization.Lifecycle;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.heat.HeatData;
import net.esromethestrange.esromes_armory.data.heat.heating_result.HeatingResult;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material_ingredient.MaterialIngredientData;
import net.esromethestrange.esromes_armory.data.material_ingredient.MaterialIngredientEntrySerializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class ArmoryRegistries {
    public static final Registry<HeatingResult.HeatingResultSerializer<? extends HeatingResult>> HEATING_RESULT_SERIALIZERS = new SimpleRegistry<>(
            RegistryKey.ofRegistry(Identifier.of(EsromesArmory.MOD_ID, "heating_result_serializer")), Lifecycle.stable());
    public static final Registry<MaterialIngredientEntrySerializer<?>> MATERIAL_INGREDIENT_ENTRY_SERIALIZERS = new SimpleRegistry<>(
            RegistryKey.ofRegistry(Identifier.of(EsromesArmory.MOD_ID, "material_ingredient_entry_serializer")), Lifecycle.stable());

    public static void registerRegistries() {
        DynamicRegistries.registerSynced(ArmoryRegistryKeys.MATERIAL, Material.CODEC);
        DynamicRegistries.registerSynced(ArmoryRegistryKeys.MATERIAL_INGREDIENT_DATA, MaterialIngredientData.CODEC);
        DynamicRegistries.registerSynced(ArmoryRegistryKeys.HEAT_DATA, HeatData.CODEC);
    }
}
