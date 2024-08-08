package net.esromethestrange.esromes_armory.data.material_ingredient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistryKeys;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;

import java.util.ArrayList;
import java.util.List;

public class MaterialIngredientData {
    public static Codec<MaterialIngredientData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MaterialIngredientEntry.CODEC.listOf().fieldOf("entries").forGetter(data -> data.entries)
    ).apply(instance, MaterialIngredientData::new));
    public static final Codec<RegistryEntry<MaterialIngredientData>> ENTRY_CODEC = RegistryFixedCodec.of(ArmoryRegistryKeys.MATERIAL_INGREDIENT_DATA);
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<MaterialIngredientData>> ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(ArmoryRegistryKeys.MATERIAL_INGREDIENT_DATA);

    public final List<MaterialIngredientEntry<?>> entries = new ArrayList<>();
    public final List<ItemStack> matchingStacks = new ArrayList<>();

    public MaterialIngredientData(List<? extends MaterialIngredientEntry<?>> entries){
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
