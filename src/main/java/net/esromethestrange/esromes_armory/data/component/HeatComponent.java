package net.esromethestrange.esromes_armory.data.component;

import com.mojang.serialization.Codec;
import net.esromethestrange.esromes_armory.data.heat.HeatLevel;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class HeatComponent implements TooltipAppender {
    private int temperature;

    public HeatComponent(int temperature){
        this.temperature = temperature;
    }

    public static final Codec<HeatComponent> CODEC = Codec.INT.xmap(
            HeatComponent::new,
            partsComponent -> partsComponent.temperature
    );

    public static final PacketCodec<PacketByteBuf, HeatComponent> PACKET_CODEC = PacketCodec.of(
            (value, buf) -> buf.writeInt(value.temperature),
            buf -> new HeatComponent(buf.readInt())
    );

    public HeatLevel getHeatLevel(){
        return HeatLevel.getHeatLevel(temperature);
    }

    public float getTemperature(){
        return temperature;
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        if(getHeatLevel() == HeatLevel.ROOM_TEMPERATURE)
            return;
        tooltip.accept(Text.translatable(getHeatLevel().translation_key).withColor(getHeatLevel().color));
    }
}