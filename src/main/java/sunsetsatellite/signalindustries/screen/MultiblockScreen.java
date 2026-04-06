package sunsetsatellite.signalindustries.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.modificationstation.stationapi.api.util.Formatting;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.signalindustries.block.entity.ItemBusBlockEntity;
import sunsetsatellite.signalindustries.block.entity.base.FluidItemContainerBlockEntity;
import sunsetsatellite.signalindustries.block.entity.base.TieredMultiblockBlockEntity;
import sunsetsatellite.signalindustries.screen.base.VisualFluidIOScreen;
import sunsetsatellite.signalindustries.screen.base.VisualItemIOScreen;
import sunsetsatellite.signalindustries.screen.handler.ItemBusScreenHandler;
import sunsetsatellite.signalindustries.screen.handler.MultiblockScreenHandler;

public class MultiblockScreen extends HandledScreen {

    public TieredMultiblockBlockEntity tile;

    public MultiblockScreen(PlayerInventory playerInventory, FluidItemContainerBlockEntity blockEntity) {
        super(new MultiblockScreenHandler(playerInventory, blockEntity));
        this.tile = (TieredMultiblockBlockEntity) blockEntity;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        if(!button.active) return;
        super.buttonClicked(button);
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int bg = switch (tile.getTier()){
            case PROTOTYPE -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/prototype_multiblock_gui.png");
            case BASIC -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/basic_multiblock_gui.png");
            case REINFORCED -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/reinforced_multiblock_gui.png");
            case AWAKENED -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/awakened_multiblock_gui.png");
            default -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/prototype_multiblock_gui.png");
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
        drawTextWithShadow(textRenderer, I18n.getTranslation(tile.getName()),10,10,color);
        if (tile.isActive()) {
            drawTextWithShadow(textRenderer,"Current Parallel: " + Formatting.GOLD + tile.parallel, 10, 20, 0xFFFFFFFF);
        } else {
            drawTextWithShadow(textRenderer,"Max Parallel: " + Formatting.GOLD + tile.parallel, 10, 20, 0xFFFFFFFF);
        }
        drawTextWithShadow(textRenderer,"Speed Multiplier: " + Formatting.LIGHT_PURPLE + tile.speedMultiplier + "x", 10, 30, 0xFFFFFFFF);
        if (tile.isDisabled()) {
            drawTextWithShadow(textRenderer,"Disabled", 10, 50, 0xFFFF0000);
        } else if (tile.isActive()) {
            drawTextWithShadow(textRenderer, String.format("Processing: %d%%", tile.getProgressScaled(100)), 10, 50, 0xFF00FF00);
        } else {
            drawTextWithShadow(textRenderer,Formatting.GRAY + "Idling..", 10, 50, 0xFFFFFFFF);
        }
    }
}
