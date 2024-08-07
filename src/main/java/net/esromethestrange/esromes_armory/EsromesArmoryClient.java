package net.esromethestrange.esromes_armory;

import net.esromethestrange.esromes_armory.block.entity.ArmoryAnvilBlockEntityRenderer;
import net.esromethestrange.esromes_armory.block.entity.ArmoryBlockEntities;
import net.esromethestrange.esromes_armory.block.entity.ForgeBlockEntityRenderer;
import net.esromethestrange.esromes_armory.client.ArmoryModelLoadingPlugin;
import net.esromethestrange.esromes_armory.client.screen.ArmoryScreenHandlers;
import net.esromethestrange.esromes_armory.client.screen.WorkbenchScreen;
import net.esromethestrange.esromes_armory.fluid.ArmoryFluidRendering;
import net.esromethestrange.esromes_armory.fluid.ArmoryFluids;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

@Environment(EnvType.CLIENT)
public class EsromesArmoryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ArmoryScreenHandlers.WORKBENCH_SCREEN_HANDLER, WorkbenchScreen::new);

        ModelLoadingPlugin.register(new ArmoryModelLoadingPlugin());

        BlockEntityRendererFactories.register(ArmoryBlockEntities.FORGE_BLOCK_ENTITY, ForgeBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ArmoryBlockEntities.ANVIL_BLOCK_ENTITY, ArmoryAnvilBlockEntityRenderer::new);

        ArmoryFluidRendering.clientInitialize();
    }
}
