package sunsetsatellite.signalindustries.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.signalindustries.item.attachment.BackpackAttachmentItem;
import sunsetsatellite.signalindustries.screen.handler.BackpackScreenHandler;
import sunsetsatellite.signalindustries.util.Tier;

public class BackpackScreen extends HandledScreen {

    public ItemStack stack;
    public Tier tier;

    public BackpackScreen(PlayerInventory playerInv, int slot) {
        super(new BackpackScreenHandler(playerInv, slot));
        this.stack = playerInv.getStack(slot);

        backgroundWidth = 198;
        backgroundHeight = 168;
        if(stack != null && stack.getItem() instanceof BackpackAttachmentItem backpack){
            this.tier = backpack.tier;
            if(backpack.tier == Tier.REINFORCED){
                backgroundHeight = 223;
            }
        }
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int bg = switch (tier){
            case BASIC -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/basic_backpack.png");
            case REINFORCED -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/reinforced_backpack.png");
            default -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/reinforced_backpack.png");
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
        int color = 0xFFFFFFFF;
        String name = I18n.getTranslation(((BackpackScreenHandler)handler).inventory.getName());
        switch (tier){
            case BASIC -> color = 0xFFFF8080;
            case REINFORCED -> color = 0xFFFF0000;
        }
        drawCenteredTextWithShadow(textRenderer, name, 90, 6, color);
    }
}
