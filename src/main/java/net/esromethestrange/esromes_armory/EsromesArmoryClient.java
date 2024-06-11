package net.esromethestrange.esromes_armory;

import net.esromethestrange.esromes_armory.block.entity.forge.ForgeBlockEntityRenderer;
import net.esromethestrange.esromes_armory.block.entity.forge.ModBlockEntities;
import net.esromethestrange.esromes_armory.screen.ForgeScreen;
import net.esromethestrange.esromes_armory.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class EsromesArmoryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.FORGE_SCREEN_HANDLER, ForgeScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.FORGE_BLOCK_ENTITY, ForgeBlockEntityRenderer::new);
    }
}
