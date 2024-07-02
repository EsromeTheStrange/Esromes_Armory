package net.esromethestrange.esromes_armory.mixin;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.material.ArmoryMaterials;
import net.esromethestrange.esromes_armory.util.MaterialHelper;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.rmi.registry.Registry;
import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class EsromesArmoryModelLoaderMixin {
    @Shadow protected abstract void loadInventoryVariantItemModel(Identifier id);

    /**
     * Injects right after <code>profiler.swap("items")</code> is called.
     */
    @Inject(method = "loadInventoryVariantItemModel", at=@At("HEAD"))
    public void esromes_armory$loadModelFromJson(Identifier identifier, CallbackInfo cir) {
        if(Registries.ITEM.get(identifier) instanceof MaterialItem materialItem) {
            for(Identifier materialId : ArmoryMaterials.getMaterialIds()){
                Identifier id = MaterialHelper.getItemIdWithMaterial(materialId, materialItem.getRawIdentifier());
                loadInventoryVariantItemModel(id);
            }
        }
    }
}
