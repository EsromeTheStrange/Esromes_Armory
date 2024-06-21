package net.esromethestrange.esromes_armory.client;

import com.google.common.base.Charsets;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.item.material.ComponentBasedItem;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.Resource;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ComponentBasedItemModel implements UnbakedModel, BakedModel, FabricBakedModel {
    ComponentBasedItem componentBasedItem;
    HashMap<MaterialItem, BakedModel> components = new HashMap<>();

    public ComponentBasedItemModel(ComponentBasedItem componentBasedItem){
        this.componentBasedItem = componentBasedItem;
    }

    public static ModelTransformation loadTransformFromJson(Identifier location) {
        try {
            return JsonUnbakedModel.deserialize(getReaderForResource(location)).getTransformations();
        } catch (IOException exception) {
            EsromesArmory.LOGGER.warn("Can't load resource " + location);
            exception.printStackTrace();
            return null;
        }
    }

    public static Reader getReaderForResource(Identifier location) throws IOException {
        Identifier file = new Identifier(location.getNamespace(), location.getPath() + ".json");
        Resource resource = MinecraftClient.getInstance().getResourceManager().getResource(file).get();
        return new BufferedReader(new InputStreamReader(resource.getInputStream(), Charsets.UTF_8));
    }

    //Unbaked Model
    @Nullable
    @Override
    public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        for(MaterialItem materialItem : componentBasedItem.getComponents()){
            BakedModel itemModel = baker.bake(new ModelIdentifier(materialItem.getRawIdentifier(), "inventory"), ModelRotation.X0_Y0);
            components.put(materialItem, itemModel);
        }
        return this;
    }
    @Override public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) { return List.of(); }
    @Override public Collection<Identifier> getModelDependencies() { return List.of(); }
    @Override public void setParents(Function<Identifier, UnbakedModel> modelLoader) { }

    //Unbaked Model
    @Override public boolean useAmbientOcclusion() { return false; }
    @Override public boolean isBuiltin() { return false; }
    @Override public boolean hasDepth() { return true; }
    @Override public boolean isSideLit() { return false; }
    @Override
    public Sprite getParticleSprite() {
        return MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(new Identifier("block/cobblestone"));
    }
    @Override
    public ModelTransformation getTransformation() {
        return loadTransformFromJson(new Identifier("minecraft:models/item/handheld"));
    }
    @Override public ModelOverrideList getOverrides() { return ModelOverrideList.EMPTY; }

    //FabricBakedModel
    @Override public boolean isVanillaAdapter() { return false; }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        for (MaterialItem materialItem : components.keySet()){
            ArmoryMaterial material = materialItem.getMaterial(stack);
            ItemStack materialStack = materialItem.getStack(material);
            components.get(materialItem).emitItemQuads(materialStack, randomSupplier, context);
        }
    }
}
