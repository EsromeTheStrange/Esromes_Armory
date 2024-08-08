package net.esromethestrange.esromes_armory.data.material_ingredient;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistryKeys;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;

public class MaterialIngredientIngredientEntry extends MaterialIngredientEntry<ItemStack> {
    public final Ingredient ingredient;

    public MaterialIngredientIngredientEntry(RegistryEntry<Material> material, Ingredient ingredient) {
        this.material = material;
        this.ingredient = ingredient;
    }

    public MaterialIngredientIngredientEntry(RegistryEntry<Material> material, ItemConvertible... items){
        this(material, Ingredient.ofItems(items));
    }

    @Override
    public boolean test(Object o, long requiredAmount) {
        if(!(o instanceof ItemStack itemStack))
            return false;
        return ingredient.test(itemStack);
    }

    @Override
    public boolean hasObject(Object o) {
        return test(o, 1);
    }

    public MaterialIngredientEntrySerializer<MaterialIngredientIngredientEntry> getSerializer(){
        return Serializer.INSTANCE;
    }

    public static class Serializer extends MaterialIngredientEntrySerializer<MaterialIngredientIngredientEntry>{
        public static Serializer INSTANCE = new Serializer();

        public static final MapCodec<MaterialIngredientIngredientEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Material.ENTRY_CODEC.fieldOf("material").forGetter(entry -> entry.material),
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(entry -> entry.ingredient)
        ).apply(instance, MaterialIngredientIngredientEntry::new));

        @Override
        public MapCodec<MaterialIngredientIngredientEntry> createCodec() {
            return CODEC;
        }
    }
}
