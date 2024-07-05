package net.esromethestrange.esromes_armory;

import net.esromethestrange.esromes_armory.client.ArmoryModelLoadingPlugin;
import net.esromethestrange.esromes_armory.client.screen.ArmoryScreenHandlers;
import net.esromethestrange.esromes_armory.client.screen.WorkbenchScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class EsromesArmoryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ArmoryScreenHandlers.WORKBENCH_SCREEN_HANDLER, WorkbenchScreen::new);

        ModelLoadingPlugin.register(new ArmoryModelLoadingPlugin());
    }
}
