package net.esromethestrange.esromes_armory.client;

import net.esromethestrange.esromes_armory.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.item.material.PartBasedItem;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.util.ResourceHelper;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class PartBasedItemModel implements UnbakedModel, BakedModel, FabricBakedModel {
    PartBasedItem partBasedItem;
    HashMap<MaterialItem, BakedModel> components = new HashMap<>();

    public PartBasedItemModel(PartBasedItem partBasedItem){
        this.partBasedItem = partBasedItem;
    }


    //Unbaked Model
    @Nullable
    @Override
    public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer) {
        for(MaterialItem materialItem : partBasedItem.getParts()){
            BakedModel itemModel = baker.bake(materialItem.getRawIdentifier().withPrefixedPath("item/"), ModelRotation.X0_Y0);
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
        return MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(Identifier.of("block/cobblestone"));
    }
    @Override
    public ModelTransformation getTransformation() {
        return ResourceHelper.loadTransformFromJson(Identifier.of("minecraft:models/item/handheld"));
    }
    @Override public ModelOverrideList getOverrides() { return ModelOverrideList.EMPTY; }

    //FabricBakedModel
    @Override public boolean isVanillaAdapter() { return false; }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        for (MaterialItem materialItem : ((PartBasedItem)stack.getItem()).getParts()){
            ArmoryMaterial material = materialItem.getMaterial(stack);
            ItemStack materialStack = materialItem.getStack(material);
            components.get(materialItem).emitItemQuads(materialStack, randomSupplier, context);
        }
    }
}
