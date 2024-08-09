package net.esromethestrange.esromes_armory.mixin;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DataOutput.class)
public abstract class EsromesArmoryTagProviderMixin {
    @Shadow public abstract DataOutput.PathResolver getResolver(DataOutput.OutputType outputType, String directoryName);

    @Inject(method = "getTagResolver", at = @At("RETURN"), cancellable = true)
    private void esromes_armory$getTagResolver(RegistryKey<? extends Registry<?>> registryRef, CallbackInfoReturnable<DataOutput.PathResolver> cir){
        Identifier id = registryRef.getValue();
        if(!id.getNamespace().equals(EsromesArmory.MOD_ID))
            return;
        cir.setReturnValue(
            getResolver(DataOutput.OutputType.DATA_PACK, "tags/" + EsromesArmory.MOD_ID + "/" + id.getPath())
        );
    }
}
