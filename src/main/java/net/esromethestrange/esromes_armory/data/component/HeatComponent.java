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
    private float heatLevel;

    public HeatComponent(float heatLevel){
        this.heatLevel = heatLevel;
    }

    public static final Codec<HeatComponent> CODEC = Codec.FLOAT.xmap(
            HeatComponent::new,
            partsComponent -> partsComponent.heatLevel
    );

    public static final PacketCodec<PacketByteBuf, HeatComponent> PACKET_CODEC = PacketCodec.of(
            (value, buf) -> buf.writeFloat(value.heatLevel),
            buf -> new HeatComponent(buf.readFloat())
    );

    public HeatLevel getHeatLevel(){
        return HeatLevel.getHeatLevel(heatLevel);
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        if(getHeatLevel() == HeatLevel.ROOM_TEMPERATURE)
            return;
        tooltip.accept(Text.translatable(getHeatLevel().translation_key));
    }
}