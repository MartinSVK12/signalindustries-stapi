package sunsetsatellite.signalindustries.screen;

import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.signalindustries.block.entity.base.FluidItemContainerBlockEntity;
import sunsetsatellite.signalindustries.fluid.FluidScreen;
import sunsetsatellite.signalindustries.screen.handler.FluidTankScreenHandler;

public class FluidTankScreen extends FluidScreen {
    public FluidTankScreen(PlayerInventory playerInventory, FluidItemContainerBlockEntity blockEntity) {
        super(new FluidTankScreenHandler(playerInventory, blockEntity));
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int textureId = minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/tank_gui.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(textureId);
        int j = (width - backgroundWidth) / 2;
        int k = (height - backgroundHeight) / 2;
        drawTexture(j, k, 0, 0, backgroundWidth, backgroundHeight);
    }
}
