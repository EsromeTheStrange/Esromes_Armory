package net.esromethestrange.esromes_armory.data.material_ingredient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class MaterialIngredientData {
    public static MaterialIngredientData EMPTY = new MaterialIngredientData(Identifier.of(EsromesArmory.MOD_ID, "empty"), List.of());

    public static Codec<MaterialIngredientData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MaterialIngredientEntry.CODEC.listOf().fieldOf("entries").forGetter(data -> data.entries)
    ).apply(instance, MaterialIngredientData::new));

    public final Identifier id;
    public final List<MaterialIngredientEntry<?>> entries = new ArrayList<>();
    public final List<ItemStack> matchingStacks = new ArrayList<>();

    public MaterialIngredientData(Identifier id){
        this(id, List.of());
    }
    public MaterialIngredientData(List<? extends MaterialIngredientEntry<?>> entries){
        this(Identifier.of(EsromesArmory.MOD_ID, "temp_id"), entries);
    }
    public MaterialIngredientData(Identifier id, List<? extends MaterialIngredientEntry<?>> entries){
        this.id = id;
        this.entries.addAll(entries);
    }

    public void addEntry(MaterialIngredientEntry<?> entry){
        this.entries.add(entry);
    }

    public boolean isValid(Object o, long requiredAmount){
        for(MaterialIngredientEntry<?> materialIngredientEntry : entries){
            if(materialIngredientEntry.test(o, requiredAmount))
                return true;
        }
        return false;
    }

    public RegistryEntry<Material> getMaterial(Object o){
        for(MaterialIngredientEntry<?> materialIngredientEntry : entries)
            if(materialIngredientEntry.hasObject(o))
                return materialIngredientEntry.getMaterial();

        return Materials.get(Materials.NONE);
    }
}
