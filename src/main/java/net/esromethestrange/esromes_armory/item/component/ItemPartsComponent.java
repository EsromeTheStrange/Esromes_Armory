package net.esromethestrange.esromes_armory.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemPartsComponent {
     HashMap<Identifier, RegistryEntry<Material>> parts = new HashMap<>();

    public static final Codec<Pair<Identifier, RegistryEntry<Material>>> PART_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("tool_part").forGetter(Pair::getLeft),
            Material.ENTRY_CODEC.fieldOf("material").forGetter(Pair::getRight)
    ).apply(instance, Pair::new));
    public static final Codec<ItemPartsComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            PART_CODEC.listOf().fieldOf("parts").forGetter(ItemPartsComponent::pairedParts)
    ).apply(instance, ItemPartsComponent::new));

    public static final PacketCodec<RegistryByteBuf, ItemPartsComponent> PACKET_CODEC = PacketCodec.of(
            (value, buf) -> {
                buf.writeInt(value.parts.size());
                value.parts.forEach((part,material) ->{
                    buf.writeIdentifier(part);
                    Material.ENTRY_PACKET_CODEC.encode(buf, material);
                });
            },
            buf -> {
                ItemPartsComponent partsComponent = new ItemPartsComponent();

                int numParts = buf.readInt();
                for(int i=0; i<numParts; i++){
                    Identifier partId = buf.readIdentifier();
                    RegistryEntry<Material> material = Material.ENTRY_PACKET_CODEC.decode(buf);
                    partsComponent.parts.put(partId, material);
                }

                return partsComponent;
            }
    );

    public ItemPartsComponent(){}
    public ItemPartsComponent(List<Pair<Identifier, RegistryEntry<Material>>> pairedParts){
        for(Pair<Identifier, RegistryEntry<Material>> pair : pairedParts)
            parts.put(pair.getLeft(), pair.getRight());
    }

    public RegistryEntry<Material> getPart(MaterialItem materialItem){
        if(!parts.containsKey(materialItem.getRawIdentifier()))
            return null;
        return parts.get(materialItem.getRawIdentifier());
    }

    public ItemPartsComponent withPart(MaterialItem materialItem, RegistryEntry<Material> material){
        parts.put(materialItem.getRawIdentifier(), material);
        return this;
    }

    private List<Pair<Identifier, RegistryEntry<Material>>> pairedParts(){
        List<Pair<Identifier, RegistryEntry<Material>>> pairedParts = new ArrayList<>();
        for(Identifier part : parts.keySet())
            pairedParts.add(new Pair<>(part, parts.get(part)));
        return pairedParts;
    }
}
