package net.esromethestrange.esromes_armory.datagen.custom;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryData;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material_ingredient.MaterialIngredientData;
import net.esromethestrange.esromes_armory.data.material_ingredient.MaterialIngredientEntry;
import net.esromethestrange.esromes_armory.data.material_ingredient.MaterialIngredientFluidEntry;
import net.esromethestrange.esromes_armory.data.material_ingredient.MaterialIngredientIngredientEntry;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistryKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public abstract class MaterialIngredientProvider implements DataProvider {
    private final RegistryWrapper.WrapperLookup registryLookup;
    private final FabricDataOutput dataOutput;
    private final List<MaterialIngredientData> materialIngredientDataList = new ArrayList<>();

    protected MaterialIngredientProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        this.dataOutput = dataOutput;
        try{
            this.registryLookup = registryLookup.get();
        } catch (InterruptedException | ExecutionException e) {
            EsromesArmory.LOGGER.error("Unable to get wrapper lookup.");
            throw new RuntimeException(e);
        }
    }

    public abstract void generate();

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        generate();

        return CompletableFuture.allOf(materialIngredientDataList.stream().map(materialIngredientData -> {
            Path path = getFilePath(materialIngredientData.id);
            JsonObject json = MaterialIngredientData.CODEC.encodeStart(
                    registryLookup.getOps(JsonOps.INSTANCE), materialIngredientData
            ).getOrThrow(IllegalStateException::new).getAsJsonObject();
            return DataProvider.writeToPath(writer, json, path);
        }).toArray(CompletableFuture[]::new));
    }

    private Path getFilePath(Identifier path){
        return dataOutput
                .getResolver(DataOutput.OutputType.DATA_PACK, ArmoryData.MATERIAL_INGREDIENT_PATH)
                .resolveJson(Identifier.of(path.getNamespace(), path.getPath()));
    }

    protected void register(MaterialIngredientData materialIngredientData){
        materialIngredientDataList.add(materialIngredientData);
    }

    @SafeVarargs
    protected final void register(Identifier id, Pair<RegistryKey<Material>, Object>... pairs){
        List<MaterialIngredientEntry<?>> ingredientEntries = new ArrayList<>();
        for(Pair<RegistryKey<Material>, Object> pair : pairs){
            RegistryEntry<Material> materialEntry = registryLookup.getWrapperOrThrow(ArmoryRegistryKeys.MATERIAL).getOrThrow(pair.getLeft());
            if(pair.getRight() instanceof ItemConvertible item)
                ingredientEntries.add(new MaterialIngredientIngredientEntry(materialEntry, item));
            if(pair.getRight() instanceof Fluid fluid)
                ingredientEntries.add(new MaterialIngredientFluidEntry(materialEntry, fluid));
        }
        materialIngredientDataList.add(new MaterialIngredientData(id, ingredientEntries));
    }

    @Override
    public String getName() {
        return "Material Ingredients";
    }
}
