package net.esromethestrange.esromes_armory.recipe.ingredient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material_ingredient.MaterialIngredientData;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.List;

public class MaterialIngredient implements CustomIngredient {
    public static final Identifier ID = Identifier.of(EsromesArmory.MOD_ID, "material");
    private final RegistryEntry<MaterialIngredientData> ingredientData;
    private final long requiredAmount;

    public MaterialIngredient(RegistryEntry<MaterialIngredientData> ingredientType, long requiredAmount){
        this.ingredientData = ingredientType;
        this.requiredAmount = requiredAmount;
    }

    public boolean testObject(Object o){
        return ingredientData.value().isValid(o, requiredAmount);
    }

    @Override
    public boolean test(ItemStack stack) {
        return testObject(stack);
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return ingredientData.value().matchingStacks;
    }

    public long getAmount() { return requiredAmount; }

    @Override
    public boolean requiresTesting() { return true; }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public RegistryEntry<Material> getMaterial(Object o){
        return ingredientData.value().getMaterial(o);
    }

    public static class Serializer implements CustomIngredientSerializer<MaterialIngredient> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public Identifier getIdentifier() { return MaterialIngredient.ID; }

        public static final MapCodec<MaterialIngredient> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                MaterialIngredientData.ENTRY_CODEC.fieldOf("material_ingredient_data").forGetter(ingredient -> ingredient.ingredientData),
                Codec.LONG.optionalFieldOf("amount", 1L).forGetter(ingredient -> ingredient.requiredAmount)
        ).apply(instance, MaterialIngredient::new));

        public static final PacketCodec<RegistryByteBuf, MaterialIngredient> PACKET_CODEC = PacketCodec.of(
                (value, buf) -> {
                    MaterialIngredientData.ENTRY_PACKET_CODEC.encode(buf, value.ingredientData);
                    buf.writeLong(value.requiredAmount);
                },
                buf -> new MaterialIngredient(MaterialIngredientData.ENTRY_PACKET_CODEC.decode(buf), buf.readLong())
        );

        @Override public MapCodec<MaterialIngredient> getCodec(boolean allowEmpty) { return CODEC; }
        @Override public PacketCodec<RegistryByteBuf, MaterialIngredient> getPacketCodec() { return PACKET_CODEC; }
    }
}
