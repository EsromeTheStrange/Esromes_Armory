package net.esromethestrange.esromes_armory;

import net.esromethestrange.esromes_armory.block.entity.ForgeBlockEntityRenderer;
import net.esromethestrange.esromes_armory.block.entity.ModBlockEntities;
import net.esromethestrange.esromes_armory.client.ComponentBasedItemRenderer;
import net.esromethestrange.esromes_armory.item.ModItems;
import net.esromethestrange.esromes_armory.screen.ForgeScreen;
import net.esromethestrange.esromes_armory.screen.ModScreenHandlers;
import net.esromethestrange.esromes_armory.screen.WorkbenchScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

@Environment(EnvType.CLIENT)
public class EsromesArmoryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.FORGE_SCREEN_HANDLER, ForgeScreen::new);
        HandledScreens.register(ModScreenHandlers.WORKBENCH_SCREEN_HANDLER, WorkbenchScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.FORGE_BLOCK_ENTITY, ForgeBlockEntityRenderer::new);

        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.PICKAXE, ComponentBasedItemRenderer.INSTANCE);
    }
}
