package net.emirikol.golemancy.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.emirikol.golemancy.event.ConfigurationHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SoulGrafterScreen extends HandledScreen<ScreenHandler> {
    //A path to the GUI texture to use.
    private static final Identifier TEXTURE = new Identifier("golemancy", "textures/gui/container/soul_grafter.png");

    private static final int BUBBLE_STARTX = 0;
    private static final int BUBBLE_STARTY = 169;
    private static final int BUBBLE_WIDTH = 43;
    private static final int BUBBLE_HEIGHT = 15;

    private static final int FLAME_STARTX = 47;
    private static final int FLAME_STARTY = 169;
    private static final int FLAME_WIDTH = 13;
    private static final int FLAME_HEIGHT = 13;

    public SoulGrafterScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
        //Graft Progress Bubbles
        int graft_time = ((SoulGrafterScreenHandler) this.handler).getGraftTime();
        if (graft_time > 0) {
            float graft_factor = 1.00F - ((float) graft_time / (float) ConfigurationHandler.getGraftDuration());
            int progress_width = Math.round((float) BUBBLE_WIDTH * graft_factor);
            drawTexture(matrices, x + 59, y + 21, BUBBLE_STARTX, BUBBLE_STARTY, progress_width, BUBBLE_HEIGHT);
        }
        //Bonemeal Fuel Indicator
        int fuel_time = ((SoulGrafterScreenHandler) this.handler).getFuelTime();
        if (fuel_time > 0) {
            float fuel_factor = (float) fuel_time / (float) ConfigurationHandler.getFuelValue();
            int fuel_height = Math.round((float) FLAME_HEIGHT * fuel_factor);
            int fuel_offset = FLAME_HEIGHT - fuel_height;
            drawTexture(matrices, x + 63, y + 55 + fuel_offset, FLAME_STARTX, FLAME_STARTY + fuel_offset, FLAME_WIDTH, fuel_height);
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        //center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

}