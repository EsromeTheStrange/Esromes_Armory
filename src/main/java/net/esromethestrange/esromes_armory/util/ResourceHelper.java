package net.esromethestrange.esromes_armory.util;

import com.google.common.base.Charsets;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class ResourceHelper {
    public static final ModelTransformation HANDHELD_TRANSFORMATION = loadTransformFromJson(Identifier.of("minecraft:models/item/handheld"));

    //Model Stuff
    /**
     * This code was taken from Smithee.
     * @author LordDeatHunter
     */
    public static ModelTransformation loadTransformFromJson(Identifier location) {
        try {
            Reader reader = getReaderForResource(location);
            ModelTransformation modelTransformation = JsonUnbakedModel.deserialize(reader).getTransformations();
            reader.close();
            return modelTransformation;
        } catch (IOException exception) {
            EsromesArmory.LOGGER.warn("Can't load resource " + location);
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * This code was taken from Smithee.
     * @author LordDeatHunter
     */
    public static Reader getReaderForResource(Identifier location) throws IOException {
        Identifier file = Identifier.of(location.getNamespace(), location.getPath() + ".json");
        Resource resource = MinecraftClient.getInstance().getResourceManager().getResource(file).get();
        return new BufferedReader(new InputStreamReader(resource.getInputStream(), Charsets.UTF_8));
    }

    public static boolean isMaterialModelPresent(Identifier id){
        return MinecraftClient.getInstance().getResourceManager().getResource(id.withPrefixedPath("models/").withSuffixedPath(".json")).isPresent();
    }

    public static boolean isMaterialModelPresent(MaterialItem materialItem, RegistryEntry<Material> material){
        Identifier materialId = material.getKey().get().getValue();
        if(materialId == null){
            EsromesArmory.LOGGER.error("Material \"{}\" not registered!", material);
            return false;
        }
        return isMaterialModelPresent(materialItem.getRawIdentifier()
                .withPrefixedPath("item/")
                .withSuffixedPath("_" + materialId.getNamespace() + "_" + materialId.getPath()));
    }
}
