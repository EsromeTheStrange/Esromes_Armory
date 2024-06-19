package net.esromethestrange.esromes_armory.client;

import net.esromethestrange.esromes_armory.item.material.ComponentBasedItem;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class ComponentBasedItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    public static final ComponentBasedItemRenderer INSTANCE = new ComponentBasedItemRenderer();

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.scale(1.0f, 1.0f, 1.0f);
        matrices.translate(0.5f, 0.5f, 0.5f);

        if(!(stack.getItem() instanceof ComponentBasedItem componentBasedItem)){
            matrices.pop();
            return;
        }

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        if(mode == ModelTransformationMode.GUI)
            DiffuseLighting.enableGuiDepthLighting();

        for (MaterialItem item : componentBasedItem.getComponents()){
            ItemStack componentStack = item.getStack(componentBasedItem.getMaterial(stack, item));

            itemRenderer.renderItem(componentStack, mode, light, OverlayTexture.DEFAULT_UV,
                    matrices, vertexConsumers, null, 0);
        }

        if(mode == ModelTransformationMode.GUI)
            DiffuseLighting.disableGuiDepthLighting();

        matrices.pop();
    }
}
