package net.esromethestrange.esromes_armory.data.material_ingredient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

public abstract class MaterialIngredientEntrySerializer<T extends MaterialIngredientEntry<?>>{
    public abstract MapCodec<T> createCodec();
}