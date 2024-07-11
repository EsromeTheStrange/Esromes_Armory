package net.esromethestrange.esromes_armory.data.component;

import com.mojang.serialization.Codec;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ArmoryComponents {
    public static final ComponentType<String> MATERIALS = register("materials", (builder) ->
            builder.codec(Codec.STRING).packetCodec(PacketCodecs.STRING));
    public static final ComponentType<ItemPartsComponent> ITEM_PARTS = register("item_parts", (builder) ->
            builder.codec(ItemPartsComponent.CODEC).packetCodec(ItemPartsComponent.PACKET_CODEC));
    public static final ComponentType<HeatComponent> HEAT = register("heat", (builder) ->
            builder.codec(HeatComponent.CODEC).packetCodec(HeatComponent.PACKET_CODEC));

    public static void registerComponents(){ }
    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return (ComponentType)Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(EsromesArmory.MOD_ID, id),
                ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }
}
