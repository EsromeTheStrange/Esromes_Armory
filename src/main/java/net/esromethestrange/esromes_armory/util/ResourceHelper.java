package net.esromethestrange.esromes_armory.util;

import com.google.common.base.Charsets;
import com.google.gson.*;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.*;

public class ResourceHelper {

    //Model Stuff
    /**
     * This code was taken from Smithee.
     * @author LordDeatHunter
     */
    public static ModelTransformation loadTransformFromJson(Identifier location) {
        try {
            return JsonUnbakedModel.deserialize(getReaderForResource(location)).getTransformations();
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
}
