package net.esromethestrange.esromes_armory.mixin;

import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.material.ArmoryMaterials;
import net.esromethestrange.esromes_armory.util.MaterialHelper;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class EsromesArmoryModelLoaderMixin {
    @Shadow protected abstract void addModel(ModelIdentifier modelId);

    /**
     * Injects right after <code>profiler.swap("items")</code> is called.
     */
    @Inject(method = "<init>", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", ordinal = 2))
    public void esromes_armory$loadModelFromJson(BlockColors blockColors, Profiler profiler, Map jsonUnbakedModels, Map blockStates, CallbackInfo ci) {
        for(MaterialItem materialItem : MaterialItem.MATERIAL_ITEMS){
            for(Identifier materialId : ArmoryMaterials.getMaterialIds()){
                Identifier id = MaterialHelper.getItemIdWithMaterial(materialId, materialItem.getRawIdentifier());
                addModel(new ModelIdentifier(id, "inventory"));
            }
        }
    }
}
