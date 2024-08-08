package net.esromethestrange.esromes_armory.data.material_ingredient;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MaterialIngredientEntrySerializers {
    public static final MaterialIngredientEntrySerializer<?> ITEM_ENTRY_SERIALIZER = register("item", MaterialIngredientIngredientEntry.Serializer.INSTANCE);
    public static final MaterialIngredientEntrySerializer<?> FLUID_ENTRY_SERIALIZER = register("fluid", MaterialIngredientFluidEntry.Serializer.INSTANCE);

    public static void registerMaterialIngredientEntrySerializers(){}

    public static MaterialIngredientEntrySerializer<?> register(String name, MaterialIngredientEntrySerializer<?> serializer){
        return Registry.register(ArmoryRegistries.MATERIAL_INGREDIENT_ENTRY_SERIALIZERS, Identifier.of(EsromesArmory.MOD_ID, name), serializer);
    }
}
