package net.esromethestrange.esromes_armory.fluid;

import net.esromethestrange.esromes_armory.data.material.Materials;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;

public class ArmoryFluidRendering {
    private static final Identifier STILL_TEXTURE = Identifier.of("minecraft:block/water_still");
    private static final Identifier FLOWING_TEXTURE = Identifier.of("minecraft:block/water_flow");

    public static void clientInitialize(){
        //TODO Custom Textures
        //ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
        //    registry.register(new Identifier("tutorial:block/custom_fluid_still"));
        //    registry.register(new Identifier("tutorial:block/custom_fluid_flowing"));
        //});

        registerMoltenMetal(ArmoryFluids.MOLTEN_STEEL, ArmoryFluids.MOLTEN_STEEL_FLOWING, Materials.STEEL.color);
        registerMoltenMetal(ArmoryFluids.MOLTEN_GOLD, ArmoryFluids.MOLTEN_GOLD_FLOWING, Materials.GOLD.color);
    }

    private static void registerMoltenMetal(Fluid stillState, Fluid flowingState, int color){
        FluidRenderHandlerRegistry.INSTANCE.register(stillState, flowingState, new SimpleFluidRenderHandler(
                STILL_TEXTURE, FLOWING_TEXTURE, color
        ));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), stillState, flowingState);
    }
}
