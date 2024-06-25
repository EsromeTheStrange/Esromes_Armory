package net.esromethestrange.esromes_armory.recipe.ingredient;

import com.google.gson.JsonObject;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialHandler;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.List;

public class MaterialIngredient implements CustomIngredient {
    public static final Identifier ID = new Identifier(EsromesArmory.MOD_ID, "material");
    private final Identifier ingredientType;

    public MaterialIngredient(Identifier ingredientType){
        this.ingredientType = ingredientType;
    }

    public boolean test(ItemStack stack){
        return ArmoryMaterialHandler.getMaterialIngredient(ingredientType).isValid(stack);
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return ArmoryMaterialHandler.getMaterialIngredient(ingredientType).matchingStacks;
    }

    @Override
    public boolean requiresTesting() { return true; }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return null;
    }

    public ArmoryMaterial getMaterial(ItemStack stack){
        return ArmoryMaterialHandler.getMaterialIngredient(ingredientType).getMaterial(stack);
    }

    public static class Serializer implements CustomIngredientSerializer<MaterialIngredient>{
        public static final Serializer INSTANCE = new Serializer();
        public final String KEY_INGREDIENT = "ingredient";

        @Override
        public Identifier getIdentifier() { return MaterialIngredient.ID; }

        @Override
        public MaterialIngredient read(JsonObject json) {
            Identifier ingredientId = Identifier.tryParse(json.get(KEY_INGREDIENT).getAsString());
            return new MaterialIngredient(ingredientId);
        }

        @Override
        public void write(JsonObject json, MaterialIngredient ingredient) {
            json.addProperty(KEY_INGREDIENT, ingredient.ingredientType.toString());
        }

        @Override
        public MaterialIngredient read(PacketByteBuf buf) {
            Identifier materialId = Identifier.tryParse(buf.readString());
            return new MaterialIngredient(materialId);
        }

        @Override
        public void write(PacketByteBuf buf, MaterialIngredient ingredient) {
            buf.writeString(ingredient.ingredientType.toString());
        }
    }
}
