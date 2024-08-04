package net.esromethestrange.esromes_armory.client.model;

import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistries;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.util.MaterialHelper;
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

public class MaterialItemModel implements UnbakedModel, BakedModel, FabricBakedModel {
    MaterialItem materialItem;
    HashMap<Material, BakedModel> variants = new HashMap<>();

    public MaterialItemModel(MaterialItem materialItem){
        this.materialItem = materialItem;
    }

    @Nullable
    @Override
    public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer) {
        ArmoryRegistries.MATERIAL.stream().forEach(material -> {
            Identifier materialId = ArmoryRegistries.MATERIAL.getId(material);
            if(materialId == null)
                return;
            Identifier itemModelId = MaterialHelper.getItemModelIdentifier(materialId, materialItem.getRawIdentifier());
            if(!ResourceHelper.isMaterialModelPresent(itemModelId))
                return;
            BakedModel materialModel = baker.bake(itemModelId, ModelRotation.X0_Y0);
            variants.put(material, materialModel);
        });
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
        return ResourceHelper.HANDHELD_TRANSFORMATION;
    }
    @Override public ModelOverrideList getOverrides() { return ModelOverrideList.EMPTY; }

    //FabricBakedModel
    @Override public boolean isVanillaAdapter() { return false; }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        Material material = ((MaterialItem)stack.getItem()).getMaterial(stack);
        BakedModel variant = variants.containsKey(material) ? variants.get(material) : variants.get(Materials.NONE);
        variant.emitItemQuads(stack, randomSupplier, context);
    }
}
