package net.esromethestrange.esromes_armory.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WorkbenchScreen extends HandledScreen<WorkbenchScreenHandler> {
    public static final Identifier TEXTURE = Identifier.of(EsromesArmory.MOD_ID, "textures/gui/workbench_gui.png");

    public WorkbenchScreen(WorkbenchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = 1000;
        titleY = 500;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }
}