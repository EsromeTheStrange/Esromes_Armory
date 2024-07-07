package net.esromethestrange.esromes_armory.data.recipe.ingredient;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.Identifier;

import java.util.List;

public class MaterialIngredient implements CustomIngredient {
    public static final Identifier ID = Identifier.of(EsromesArmory.MOD_ID, "material");
    private final Identifier ingredientType;

    public MaterialIngredient(Identifier ingredientType){
        this.ingredientType = ingredientType;
    }

    public boolean test(ItemStack stack){
        return ArmoryIngredients.getMaterialIngredient(ingredientType).isValid(stack);
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return ArmoryIngredients.getMaterialIngredient(ingredientType).matchingStacks;
    }

    @Override
    public boolean requiresTesting() { return true; }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public Material getMaterial(ItemStack stack){
        return ArmoryIngredients.getMaterialIngredient(ingredientType).getMaterial(stack);
    }

    public static class Serializer implements CustomIngredientSerializer<MaterialIngredient> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String KEY_INGREDIENT = "ingredient";

        @Override
        public Identifier getIdentifier() { return MaterialIngredient.ID; }

        public static final MapCodec<MaterialIngredient> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Identifier.CODEC.fieldOf(KEY_INGREDIENT).forGetter(ingredient -> ingredient.ingredientType)
        ).apply(instance, MaterialIngredient::new));

        public static final PacketCodec<RegistryByteBuf, MaterialIngredient> PACKET_CODEC = PacketCodec.of(
                (value, buf) -> buf.writeIdentifier(value.ingredientType),
                buf -> new MaterialIngredient(buf.readIdentifier())
        );

        @Override public MapCodec<MaterialIngredient> getCodec(boolean allowEmpty) { return CODEC; }
        @Override public PacketCodec<RegistryByteBuf, MaterialIngredient> getPacketCodec() { return PACKET_CODEC; }
    }
}
