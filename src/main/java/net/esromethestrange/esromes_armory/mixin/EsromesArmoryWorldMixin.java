package net.esromethestrange.esromes_armory.mixin;

import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistryKeys;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(World.class)
public class EsromesArmoryWorldMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    public void esromes_armory$init(MutableWorldProperties properties, RegistryKey registryRef, DynamicRegistryManager registryManager,
                     RegistryEntry dimensionEntry, Supplier profiler, boolean isClient, boolean debugWorld,
                     long biomeAccess, int maxChainedNeighborUpdates, CallbackInfo ci){
        Materials.INSTANCE = new Materials(registryManager.get(ArmoryRegistryKeys.MATERIAL));
    }
}
