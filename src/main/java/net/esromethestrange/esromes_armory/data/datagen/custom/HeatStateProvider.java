package net.esromethestrange.esromes_armory.data.datagen.custom;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.esromethestrange.esromes_armory.data.ArmoryData;
import net.esromethestrange.esromes_armory.data.heat.HeatData;
import net.esromethestrange.esromes_armory.data.heat.HeatLevel;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class HeatStateProvider implements DataProvider {
    private final FabricDataOutput dataOutput;
    private final List<HeatData> heatDataList = new ArrayList<>();

    protected HeatStateProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        this.dataOutput = dataOutput;
    }

    public abstract void generate();

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        generate();

        return CompletableFuture.allOf(heatDataList.stream().map(heatData -> {
            Path path = getFilePath(heatData.id);
            JsonObject json = HeatData.CODEC.encodeStart(
                    JsonOps.INSTANCE, heatData
            ).getOrThrow().getAsJsonObject();
            return DataProvider.writeToPath(writer, json, path);
        }).toArray(CompletableFuture[]::new));
    }

    private Path getFilePath(Identifier path){
        return dataOutput
                .getResolver(DataOutput.OutputType.DATA_PACK, ArmoryData.HEAT_STATE_PATH)
                .resolveJson(Identifier.of(path.getNamespace(), path.getPath()));
    }

    protected void register(HeatData heatData){
        heatDataList.add(heatData);
    }

    protected void registerMetal(Identifier id, HeatLevel meltingPoint, Item ingot, Block block, Fluid fluid){
        register(new HeatData(id.withSuffixedPath("_ingot"))
                .addEntry(HeatLevel.ROOM_TEMPERATURE, ingot)
                .addEntry(meltingPoint, fluid, 9000));
        register(new HeatData(id.withSuffixedPath("_block"))
                .addEntry(HeatLevel.ROOM_TEMPERATURE, block.asItem())
                .addEntry(meltingPoint, fluid, 81000));
    }

    @Override
    public String getName() {
        return "Heat States";
    }
}
