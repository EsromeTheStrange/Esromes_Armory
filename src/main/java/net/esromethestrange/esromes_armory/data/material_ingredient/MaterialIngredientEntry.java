package net.esromethestrange.esromes_armory.data.material_ingredient;

import com.mojang.serialization.Codec;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistries;
import net.esromethestrange.esromes_armory.data.material.Material;

public abstract class MaterialIngredientEntry<T> {
    public static Codec<MaterialIngredientEntry<?>> CODEC;

    protected Material material;

    public abstract boolean test(Object o, long requiredAmount);
    public abstract boolean hasObject(Object o);
    public abstract MaterialIngredientEntrySerializer<? extends MaterialIngredientEntry<T>> getSerializer();
    public Material getMaterial() { return material; }

    public static void initializeCodec(){
        CODEC = ArmoryRegistries.MATERIAL_INGREDIENT_ENTRY_SERIALIZERS.getCodec().dispatch(
                "type",
                MaterialIngredientEntry::getSerializer,
                MaterialIngredientEntrySerializer::createCodec
        );
    }
}
