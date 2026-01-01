package sunsetsatellite.signalindustries.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.signalindustries.block.entity.CrusherBlockEntity;
import sunsetsatellite.signalindustries.block.entity.EnergyCellBlockEntity;
import sunsetsatellite.signalindustries.block.entity.base.FluidItemContainerBlockEntity;
import sunsetsatellite.signalindustries.screen.base.FluidIOScreen;
import sunsetsatellite.signalindustries.screen.handler.EnergyCellScreenHandler;
import sunsetsatellite.signalindustries.screen.handler.FluidTankScreenHandler;
import sunsetsatellite.signalindustries.util.Tier;

public class EnergyCellScreen extends HandledScreen {

    public EnergyCellBlockEntity tile;

    public EnergyCellScreen(PlayerInventory playerInventory, FluidItemContainerBlockEntity blockEntity) {
        super(new EnergyCellScreenHandler(playerInventory, blockEntity));
        this.tile = (EnergyCellBlockEntity) blockEntity;
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

    @Override
    protected void drawForeground()
    {
        super.drawForeground();
        int color = switch (tile.getTier()) {
            case BASIC -> 0xFFFF8080;
            case REINFORCED -> 0xFFFF0000;
            case AWAKENED -> 0xFFFFA500;
            default -> 0xFFFFFFFF;
        };
        drawCenteredTextWithShadow(textRenderer, I18n.getTranslation(tile.getName()),90,6, color);
    }

    public ButtonWidget fluidIoButton;

    @Override
    protected void buttonClicked(ButtonWidget guibutton) {
        if (!guibutton.active) {
            return;
        }
        if (guibutton == fluidIoButton) {
            minecraft.setScreen(new FluidIOScreen(minecraft.player, container, this, tile));
        }
        if(tile.getTier() == Tier.INFINITE && guibutton.id == 1){
            tile.isInfiniteSource = !tile.isInfiniteSource;
            guibutton.text = tile.isInfiniteSource ? "INF" : "VOID";
        }
    }

    @Override
    public void init() {
        ButtonWidget fluidIo = new ButtonWidget(0, Math.round((float) width / 2) + 60, Math.round((float) height / 2) - 80, 20, 20, "F");
        buttons.add(fluidIo);
        fluidIoButton = fluidIo;

        if(tile.getTier() == Tier.INFINITE){
            buttons.add(new ButtonWidget(1, Math.round((float) width / 2) - 80, Math.round((float) height / 2) - 30, 20, 20, tile.isInfiniteSource ? "INF" : "VOID"));
        }

        super.init();
    }
}
