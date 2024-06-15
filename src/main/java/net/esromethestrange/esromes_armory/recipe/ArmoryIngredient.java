package net.esromethestrange.esromes_armory.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialType;
import net.esromethestrange.esromes_armory.data.MaterialHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.List;

public class ArmoryIngredient {
    private final Identifier materialType;
    private final String itemType;

    public ArmoryIngredient(Identifier materialType, String itemType){
        this.materialType = materialType;
        this.itemType = itemType;
    }

    public boolean test(ItemStack stack){
        List<ArmoryMaterial> materials = MaterialHandler.getMaterialType(materialType).getMaterials();
        for (ArmoryMaterial material : materials){
            if(stack.isOf(material.getItem(itemType))) return true;
        }
        return false;
    }

    public String getMaterial(ItemStack stack){
        List<ArmoryMaterial> materials = MaterialHandler.getMaterialType(materialType).getMaterials();
        for (ArmoryMaterial material : materials){
            if(stack.isOf(material.getItem(itemType))) return material.id.toString();
        }
        return ArmoryMaterial.NONE.id.toString();
    }

    public static ArmoryIngredient fromJson(JsonElement jsonElement){
        JsonObject json = jsonElement.getAsJsonObject();
        Identifier materialId = Identifier.tryParse(json.get("materialType").getAsString());
        String jsonItemType = json.get("itemType").getAsString();
        return new ArmoryIngredient(materialId, jsonItemType);
    }

    public static ArmoryIngredient fromPacket(PacketByteBuf buf){
        Identifier materialId = Identifier.tryParse(buf.readString());
        String packetItemType = buf.readString();
        return new ArmoryIngredient(materialId, packetItemType);
    }

    public void write(PacketByteBuf buf){
        buf.writeString(materialType.toString());
        buf.writeString(itemType);
    }
}
