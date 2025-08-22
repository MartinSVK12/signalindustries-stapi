package sunsetsatellite.signalindustries.screen;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.signalindustries.block.entity.CrusherBlockEntity;
import sunsetsatellite.signalindustries.block.entity.base.FluidItemContainerBlockEntity;
import sunsetsatellite.signalindustries.fluid.FluidScreen;
import sunsetsatellite.signalindustries.fluid.FluidScreenHandler;
import sunsetsatellite.signalindustries.screen.handler.CrusherScreenHandler;

public class CrusherScreen extends FluidScreen {

    public CrusherBlockEntity tile;

    public CrusherScreen(PlayerInventory playerInventory, FluidItemContainerBlockEntity blockEntity) {
        super(new CrusherScreenHandler(playerInventory, blockEntity));
        this.tile = (CrusherBlockEntity) blockEntity;
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int bg = switch (tile.getTier()){
            case BASIC -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/generic_basic_machine.png");
            case REINFORCED -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/generic_reinforced_machine.png");
            default -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/generic_prototype_machine.png");
        };

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(bg);
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        drawTexture(x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        int counter;
        if(tile.isActive()){
            counter = tile.getBurnTimeScaled(12);
            drawTexture(x + 56, y + 36 + 12 - counter, 176, 12 - counter, 14, counter + 2);
        }
        counter = this.tile.getProgressScaled(24);
        this.drawTexture(x + 79, y + 34, 176, 14, counter + 1, 16);
        if(this.tile.speedMultiplier > 1){
            this.drawCenteredTextWithShadow(textRenderer, this.tile.speedMultiplier+"x",x + backgroundWidth - 16,y + backgroundHeight/2 - 16,tile.speedMultiplier >= 3 ? 0xFFFFA500 : (tile.speedMultiplier >= 2 ? 0xFFFF00FF : 0xFFFF8080));
        }
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
