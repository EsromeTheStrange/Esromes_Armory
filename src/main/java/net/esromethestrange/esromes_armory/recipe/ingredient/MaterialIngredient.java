package net.esromethestrange.esromes_armory.recipe.ingredient;

import com.google.gson.JsonObject;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.MaterialHandler;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class MaterialIngredient implements CustomIngredient {
    public static final Identifier ID = new Identifier(EsromesArmory.MOD_ID, "material");
    private final Identifier materialType;
    private final String itemType;

    public MaterialIngredient(Identifier materialType, String itemType){
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

    @Override
    public List<ItemStack> getMatchingStacks() {List<ArmoryMaterial> materials = MaterialHandler.getMaterialType(materialType).getMaterials();
        List<ItemStack> stacks = new ArrayList<>();
        for (ArmoryMaterial material : materials)
            stacks.add(material.getItem(itemType).getDefaultStack());
        return stacks;
    }

    @Override
    public boolean requiresTesting() { return true; }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return null;
    }

    public ArmoryMaterial getMaterial(ItemStack stack){
        List<ArmoryMaterial> materials = MaterialHandler.getMaterialType(materialType).getMaterials();
        for (ArmoryMaterial material : materials){
            if(stack.isOf(material.getItem(itemType))) return material;
        }
        return ArmoryMaterial.NONE;
    }

    public static class Serializer implements CustomIngredientSerializer<MaterialIngredient>{
        public static final Serializer INSTANCE = new Serializer();
        public final String KEY_MATERIALTYPE = "materialType";
        public final String KEY_ITEMTYPE = "itemType";

        @Override
        public Identifier getIdentifier() { return MaterialIngredient.ID; }

        @Override
        public MaterialIngredient read(JsonObject json) {
            Identifier materialId = Identifier.tryParse(json.get(KEY_MATERIALTYPE).getAsString());
            String jsonItemType = json.get(KEY_ITEMTYPE).getAsString();
            return new MaterialIngredient(materialId, jsonItemType);
        }

        @Override
        public void write(JsonObject json, MaterialIngredient ingredient) {
            json.addProperty(KEY_MATERIALTYPE, ingredient.materialType.toString());
            json.addProperty(KEY_ITEMTYPE, ingredient.itemType);
        }

        @Override
        public MaterialIngredient read(PacketByteBuf buf) {
            Identifier materialId = Identifier.tryParse(buf.readString());
            String packetItemType = buf.readString();
            return new MaterialIngredient(materialId, packetItemType);
        }

        @Override
        public void write(PacketByteBuf buf, MaterialIngredient ingredient) {
            buf.writeString(ingredient.materialType.toString());
            buf.writeString(ingredient.itemType);
        }
    }
}
