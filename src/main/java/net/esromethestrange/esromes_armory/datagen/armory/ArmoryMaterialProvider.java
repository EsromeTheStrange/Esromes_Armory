package net.esromethestrange.esromes_armory.datagen.armory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialInfo;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public abstract class ArmoryMaterialProvider implements DataProvider {
    protected final FabricDataOutput dataOutput;
    private HashMap<Identifier, JsonObject> jsonsToWrite = new HashMap<>();

    private final DataOutput.PathResolver pathResolver;

    protected ArmoryMaterialProvider(FabricDataOutput dataOutput) {
        this.dataOutput = dataOutput;
        this.pathResolver = dataOutput.getResolver(DataOutput.OutputType.DATA_PACK, "esrome/materials");
    }

    /**
     * Implement this method to register materials.
     *
     * <p>Call {@link ArmoryMaterialProvider#createMaterial(ArmoryMaterialInfo)} to add a material.
     */
    public abstract void generateMaterials();

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        generateMaterials();

        return CompletableFuture.allOf(jsonsToWrite.keySet().stream().map(key -> {
            Path path = pathResolver.resolve(key, "json");
            JsonElement jsonElement = jsonsToWrite.get(key);
            return DataProvider.writeToPath(writer, jsonElement, path);
        }).toArray(CompletableFuture[]::new));
    }

    protected void createMaterial(ArmoryMaterialInfo materialInfo){
        JsonObject materialJson = new JsonObject();
        materialInfo.writeToJson(materialJson);
        jsonsToWrite.put(materialInfo.id, materialJson);
    }

    @Override
    public String getName() {
        return "Esrome's Armory Material Definitions";
    }
}
