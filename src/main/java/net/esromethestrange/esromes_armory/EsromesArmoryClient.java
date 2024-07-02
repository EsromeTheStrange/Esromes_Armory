package net.esromethestrange.esromes_armory;

import net.esromethestrange.esromes_armory.client.ModModelLoadingPlugin;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;

@Environment(EnvType.CLIENT)
public class EsromesArmoryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLoadingPlugin.register(new ModModelLoadingPlugin());
    }
}
