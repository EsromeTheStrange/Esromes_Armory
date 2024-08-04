package net.esromethestrange.esromes_armory.data.component;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistries;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class ItemPartsComponent {
    /** Takes in the identifier of the {@link net.esromethestrange.esromes_armory.item.material.MaterialItem MaterialItem}
     * and gives the identifier of the {@link Material Material}.*/
     HashMap<Identifier, Identifier> parts = new HashMap<>();

    public static final Codec<ItemPartsComponent> CODEC = Codec.STRING.xmap(
        code ->{
            ItemPartsComponent itemPartsComponent = new ItemPartsComponent();

            JsonObject jsonObject = JsonParser.parseString(code).getAsJsonObject();
            for(String key : jsonObject.keySet()){
                itemPartsComponent.parts.put(Identifier.of(key), Identifier.of(jsonObject.get(key).getAsString()));
            }

            return itemPartsComponent;
        },
        partsComponent -> {
            JsonObject json = new JsonObject();
            for(Identifier entry : partsComponent.parts.keySet()){
                json.addProperty(entry.toString(), partsComponent.parts.get(entry).toString());
            }
            return json.toString();
        }
    );

    public static final PacketCodec<PacketByteBuf, ItemPartsComponent> PACKET_CODEC = PacketCodec.of(
            (value, buf) -> {
                buf.writeInt(value.parts.size());
                value.parts.forEach((part,material) ->{
                    buf.writeIdentifier(part);
                    buf.writeIdentifier(material);
                });
            },
            buf -> {
                ItemPartsComponent partsComponent = new ItemPartsComponent();

                int numParts = buf.readInt();
                for(int i=0; i<numParts; i++){
                    Identifier partId = buf.readIdentifier();
                    Identifier materialId = buf.readIdentifier();
                    partsComponent.parts.put(partId, materialId);
                }

                return partsComponent;
            }
    );

    public Identifier getPart(MaterialItem materialItem){
        if(!parts.containsKey(materialItem.getRawIdentifier()))
            return ArmoryRegistries.MATERIAL.getId(Materials.NONE);
        return parts.get(materialItem.getRawIdentifier());
    }

    public ItemPartsComponent withPart(MaterialItem materialItem, Material material){
        parts.put(materialItem.getRawIdentifier(), ArmoryRegistries.MATERIAL.getId(material));
        return this;
    }
}
