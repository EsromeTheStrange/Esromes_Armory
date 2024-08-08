package net.esromethestrange.esromes_armory.data.heat;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.esromethestrange.esromes_armory.data.heat.heating_result.FluidHeatingResult;
import net.esromethestrange.esromes_armory.data.heat.heating_result.HeatingResult;
import net.esromethestrange.esromes_armory.data.heat.heating_result.ItemHeatingResult;
import net.esromethestrange.esromes_armory.fluid.ArmoryFluids;
import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistries;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistryKeys;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.List;

/**
 * Fuck you datas is a real word
 */
public class HeatDatas {
    public static final RegistryKey<HeatData> COPPER_INGOT = of("copper_ingot");
    public static final RegistryKey<HeatData> COPPER_BLOCK = of("copper_block");
    public static final RegistryKey<HeatData> GOLD_INGOT = of("gold_ingot");
    public static final RegistryKey<HeatData> GOLD_BLOCK = of("gold_block");
    public static final RegistryKey<HeatData> IRON_INGOT = of("iron_ingot");
    public static final RegistryKey<HeatData> IRON_BLOCK = of("iron_block");
    public static final RegistryKey<HeatData> NETHERITE_INGOT = of("netherite_ingot");
    public static final RegistryKey<HeatData> NETHERITE_BLOCK = of("netherite_block");
    public static final RegistryKey<HeatData> STEEL_INGOT = of("steel_ingot");
    public static final RegistryKey<HeatData> STEEL_BLOCK = of("steel_block");

    public static void bootstrap(Registerable<HeatData> context){
        registerMetal(context, COPPER_INGOT, COPPER_BLOCK, Items.COPPER_INGOT, Items.COPPER_BLOCK, ArmoryFluids.MOLTEN_COPPER, HeatLevel.LIGHT_YELLOW);
        registerMetal(context, GOLD_INGOT, GOLD_BLOCK, Items.GOLD_INGOT, Items.GOLD_BLOCK, ArmoryFluids.MOLTEN_GOLD, HeatLevel.LIGHT_YELLOW);
        registerMetal(context, IRON_INGOT, IRON_BLOCK, Items.IRON_INGOT, Items.IRON_BLOCK, ArmoryFluids.MOLTEN_IRON, HeatLevel.WHITE);
        registerMetal(context, NETHERITE_INGOT, NETHERITE_BLOCK, Items.NETHERITE_INGOT, Items.NETHERITE_BLOCK, ArmoryFluids.MOLTEN_NETHERITE, HeatLevel.SPARKLING);
        registerMetal(context, STEEL_INGOT, STEEL_BLOCK, ArmoryItems.STEEL_INGOT, ArmoryBlocks.STEEL_BLOCK.asItem(), ArmoryFluids.MOLTEN_STEEL, HeatLevel.SPARKLING);
    }

    public static void registerHeatingResultSerializers(){
        register("item", ItemHeatingResult.Serializer.INSTANCE);
        register("fluid", FluidHeatingResult.Serializer.INSTANCE);
    }

    private static void register(String name, HeatingResult.HeatingResultSerializer<?> heatingResultSerializer){
        Registry.register(ArmoryRegistries.HEATING_RESULT_SERIALIZERS, Identifier.of(EsromesArmory.MOD_ID, name), heatingResultSerializer);
    }

    private static void registerMetal(Registerable<HeatData> context, RegistryKey<HeatData> ingotKey, RegistryKey<HeatData> blockKey,
                                      Item item, Item block, Fluid fluid, HeatLevel meltingTemperature){
        context.register(ingotKey, new HeatData(List.of(
                new Pair<>(HeatLevel.ROOM_TEMPERATURE, new ItemHeatingResult(item)),
                new Pair<>(meltingTemperature, new FluidHeatingResult(fluid, 9000))
        )));
        context.register(blockKey, new HeatData(List.of(
                new Pair<>(HeatLevel.ROOM_TEMPERATURE, new ItemHeatingResult(block)),
                new Pair<>(meltingTemperature, new FluidHeatingResult(fluid, 81000))
        )));
    }

    private static RegistryKey<HeatData> of(String name){
        return RegistryKey.of(ArmoryRegistryKeys.HEAT_DATA, Identifier.of(EsromesArmory.MOD_ID, name));
    }
}
