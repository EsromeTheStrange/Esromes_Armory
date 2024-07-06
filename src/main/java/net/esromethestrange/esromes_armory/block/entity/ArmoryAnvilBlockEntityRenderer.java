package net.esromethestrange.esromes_armory.block.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class ArmoryAnvilBlockEntityRenderer implements BlockEntityRenderer<ArmoryAnvilBlockEntity> {

    public ArmoryAnvilBlockEntityRenderer(BlockEntityRendererFactory.Context context){}

    @Override
    public void render(ArmoryAnvilBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = entity.getRenderStack(0);
        ItemStack stack2 = entity.getRenderStack(1);


        if(!stack.isEmpty()){
            matrices.push();
            matrices.translate(0.75f, 0.5f, 0.5f);

            matrices.push();
            matrices.scale(0.4f, 0.4f, 0.4f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
            matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(entity.getRotation()));

            itemRenderer.renderItem(stack, ModelTransformationMode.GUI, getLightLevel(entity.getWorld(), entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);

            matrices.pop();
            matrices.translate(-0.4f, 0, 0);

            matrices.scale(0.4f, 0.4f, 0.4f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
            matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(entity.getRotation()));


            itemRenderer.renderItem(stack2, ModelTransformationMode.GUI, getLightLevel(entity.getWorld(), entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);

            matrices.pop();
        }
    }

    private int getLightLevel(World world, BlockPos pos){
        int block = world.getLightLevel(LightType.BLOCK, pos);
        int sky = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(block, sky);
    }
}
