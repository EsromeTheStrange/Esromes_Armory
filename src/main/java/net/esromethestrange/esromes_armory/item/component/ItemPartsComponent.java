package net.esromethestrange.esromes_armory.item.component;

import com.mojang.serialization.Codec;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.material.ArmoryMaterials;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class ItemPartsComponent {
    /** Takes in the identifier of the {@link net.esromethestrange.esromes_armory.item.material.MaterialItem MaterialItem}
     * and gives the identifier of the {@link net.esromethestrange.esromes_armory.material.ArmoryMaterial Material}.*/
     HashMap<Identifier, Identifier> parts = new HashMap<>();

    public static final Codec<ItemPartsComponent> CODEC = Codec.STRING.xmap(
        code ->{
            String[] components = code.split("|");
            ItemPartsComponent itemPartsComponent = new ItemPartsComponent();

            if(components.length == 1 & components[0].isEmpty())
                return itemPartsComponent;

            for(String component : components){
                String[] subparts = component.split("~");
                itemPartsComponent.parts.put(Identifier.of(subparts[0]), Identifier.of(subparts[1]));
            }
            return itemPartsComponent;
        },
        partsComponent -> {
            String code = "";
            for(Identifier entry : partsComponent.parts.keySet()){
                code = code + entry.toString() + "~" +
                        partsComponent.parts.get(entry).toString() + "|";
            }
            return code;
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
            return ArmoryMaterials.NONE.id;
        return parts.get(materialItem.getRawIdentifier());
    }

    public ItemPartsComponent withPart(MaterialItem materialItem, ArmoryMaterial armoryMaterial){
        parts.put(materialItem.getRawIdentifier(), armoryMaterial.id);
        return this;
    }
}
