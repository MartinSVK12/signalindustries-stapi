package sunsetsatellite.signalindustries.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.signalindustries.block.entity.FluidHatchBlockEntity;
import sunsetsatellite.signalindustries.block.entity.ItemBusBlockEntity;
import sunsetsatellite.signalindustries.block.entity.base.FluidItemContainerBlockEntity;
import sunsetsatellite.signalindustries.screen.base.VisualFluidIOScreen;
import sunsetsatellite.signalindustries.screen.base.VisualItemIOScreen;
import sunsetsatellite.signalindustries.screen.handler.FluidHatchScreenHandler;
import sunsetsatellite.signalindustries.screen.handler.ItemBusScreenHandler;

public class FluidHatchScreen extends HandledScreen {

    public FluidHatchBlockEntity tile;

    public FluidHatchScreen(PlayerInventory playerInventory, FluidItemContainerBlockEntity blockEntity) {
        super(new FluidHatchScreenHandler(playerInventory, blockEntity));
        this.tile = (FluidHatchBlockEntity) blockEntity;
    }

    public ButtonWidget itemIoButton;
    public ButtonWidget fluidIoButton;

    @Override
    public void init() {
        ButtonWidget fluidIo = new ButtonWidget(0, Math.round((float) width / 2) + 60, Math.round((float) height / 2) - 80, 20, 20, "F");
        buttons.add(fluidIo);
        ButtonWidget itemIo = new ButtonWidget(1, Math.round((float) width / 2) + 60, Math.round((float) height / 2) - 60, 20, 20, "I");
        buttons.add(itemIo);
        fluidIoButton = fluidIo;
        itemIoButton = itemIo;
        itemIo.active = false;
        super.init();
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        if(!button.active) return;

        if(button == itemIoButton){
            minecraft.setScreen(new VisualItemIOScreen(minecraft.player, handler, this, tile));
        } else if(button == fluidIoButton){
            minecraft.setScreen(new VisualFluidIOScreen(minecraft.player, handler, this, tile));
        }
        super.buttonClicked(button);
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int bg = switch (tile.getTier()){
            case BASIC -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/basic_fluid_hatch.png");
            case REINFORCED -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/reinforced_fluid_hatch.png");
            default -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/basic_fluid_hatch.png");
        };

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(bg);
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        drawTexture(x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    protected void drawForeground() {
        super.drawForeground();
        int color = switch (tile.getTier()) {
            case BASIC -> 0xFFFF8080;
            case REINFORCED -> 0xFFFF0000;
            case AWAKENED -> 0xFFFFA500;
            default -> 0xFFFFFFFF;
        };
        drawCenteredTextWithShadow(textRenderer, I18n.getTranslation(tile.getName()),90,6,color);
    }
}
