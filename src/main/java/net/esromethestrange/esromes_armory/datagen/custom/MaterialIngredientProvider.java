package net.esromethestrange.esromes_armory.datagen.custom;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.esromethestrange.esromes_armory.data.ArmoryData;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material_ingredient.MaterialIngredientData;
import net.esromethestrange.esromes_armory.data.material_ingredient.MaterialIngredientEntry;
import net.esromethestrange.esromes_armory.data.material_ingredient.MaterialIngredientFluidEntry;
import net.esromethestrange.esromes_armory.data.material_ingredient.MaterialIngredientIngredientEntry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class MaterialIngredientProvider implements DataProvider {
    private final FabricDataOutput dataOutput;
    private final List<MaterialIngredientData> materialIngredientDataList = new ArrayList<>();

    protected MaterialIngredientProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        this.dataOutput = dataOutput;
    }

    public abstract void generate();

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        generate();

        return CompletableFuture.allOf(materialIngredientDataList.stream().map(materialIngredientData -> {
            Path path = getFilePath(materialIngredientData.id);
            JsonObject json = MaterialIngredientData.CODEC.encodeStart(
                    JsonOps.INSTANCE, materialIngredientData
            ).getOrThrow().getAsJsonObject();
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
    protected final void register(Identifier id, Pair<Material, Object>... pairs){
        List<MaterialIngredientEntry<?>> ingredientEntries = new ArrayList<>();
        for(Pair<Material, Object> pair : pairs){
            if(pair.getRight() instanceof ItemConvertible item)
                ingredientEntries.add(new MaterialIngredientIngredientEntry(pair.getLeft(), item));
            if(pair.getRight() instanceof Fluid fluid)
                ingredientEntries.add(new MaterialIngredientFluidEntry(pair.getLeft(), fluid));
        }
        materialIngredientDataList.add(new MaterialIngredientData(id, ingredientEntries));
    }

    @Override
    public String getName() {
        return "Material Ingredients";
    }
}
