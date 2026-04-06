package sunsetsatellite.signalindustries.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.platform.Lighting;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.ExtendedScreenDraw;
import sunsetsatellite.catalyst.core.util.mp.ScreenActionPacket;
import sunsetsatellite.catalyst.core.util.vector.Vec3i;
import sunsetsatellite.signalindustries.block.entity.CrusherBlockEntity;
import sunsetsatellite.signalindustries.block.entity.EncapsulatorBlockEntity;
import sunsetsatellite.signalindustries.block.entity.base.FluidItemContainerBlockEntity;
import sunsetsatellite.signalindustries.mp.packet.IOChangePacket;
import sunsetsatellite.signalindustries.render.RenderBlocksOnScreen;
import sunsetsatellite.signalindustries.screen.base.VisualFluidIOScreen;
import sunsetsatellite.signalindustries.screen.base.VisualItemIOScreen;
import sunsetsatellite.signalindustries.screen.handler.CrusherScreenHandler;
import sunsetsatellite.signalindustries.screen.handler.EncapsulatorScreenHandler;
import sunsetsatellite.signalindustries.util.IOPreview;

public class EncapsulatorScreen extends HandledScreen implements ExtendedScreenDraw {

    public EncapsulatorBlockEntity tile;

    public EncapsulatorScreen(PlayerInventory playerInventory, FluidItemContainerBlockEntity blockEntity) {
        super(new EncapsulatorScreenHandler(playerInventory, blockEntity));
        this.tile = (EncapsulatorBlockEntity) blockEntity;
    }

    public ButtonWidget itemIoButton;
    public ButtonWidget fluidIoButton;
    private ButtonWidget storeButton;
    private ButtonWidget cutButton;

    @Override
    public void init() {
        ButtonWidget fluidIo = new ButtonWidget(0, Math.round((float) width / 2) - 80, Math.round((float) height / 2) - 25, 20, 20, "F");
        buttons.add(fluidIo);
        ButtonWidget itemIo = new ButtonWidget(1, Math.round((float) width / 2) - 60, Math.round((float) height / 2) - 25, 20, 20, "I");
        buttons.add(itemIo);
        fluidIoButton = fluidIo;
        itemIoButton = itemIo;

        storeButton = new ButtonWidget(2, Math.round((float) width / 2), Math.round((float) height / 2) - 50, 60, 20, "Store");
        cutButton = new ButtonWidget(3, Math.round((float) width / 2) - 60, Math.round((float) height / 2) - 50, 60, 20, "Cut");
        storeButton.active = tile.state == EncapsulatorBlockEntity.State.NONE;
        cutButton.active = tile.state == EncapsulatorBlockEntity.State.NONE;
        buttons.add(storeButton);
        buttons.add(cutButton);
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

        if (button.id == 2) {
            tile.state = EncapsulatorBlockEntity.State.STORING;
        } else if (button.id == 3) {
            tile.state = EncapsulatorBlockEntity.State.CUTTING;
        }
        tile.reset();

        if(tile.world.isRemote){
            PacketHelper.send(new ScreenActionPacket(button.id, 0, 0, new Vec3i(tile.x, tile.y, tile.z)));
        }

        super.buttonClicked(button);
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int bg = this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/encapsulator_awakened.png");

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(bg);
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        drawTexture(x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        int counter;
        if(tile.isActive()){
            counter = tile.getBurnTimeScaled(12);
            drawTexture(x + 8, y + 17 + 12 - counter, 176, 16 - counter, 14, counter + 2);
        }
        counter = this.tile.getProgressScaled(25);
        this.drawTexture(x + 149, y + 29, 176, 29, 12, counter + 1);
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
        storeButton.active = tile.state == EncapsulatorBlockEntity.State.NONE;
        cutButton.active = tile.state == EncapsulatorBlockEntity.State.NONE;

        cutButton.active = false; //todo: unfinished, so it is disabled
    }

    @Override
    public void drawAfterSlotAndButtonRendering(int mouseX, int mouseY, float partialTick) {
        Minecraft mc = Minecraft.INSTANCE;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPushMatrix();
        GL11.glTranslatef(-80, 80, 900F);

        double size = 7;

        GL11.glScaled(size, -size, size);
        GL11.glRotatef(30.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, 0, 0.0F);
        RenderBlocksOnScreen r = new RenderBlocksOnScreen();
        r.drawBlock(tile.structure, Tessellator.INSTANCE, mc.world, 0, 0, 0, 1);
        GL11.glPopMatrix();
        Lighting.turnOff();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        int color = 0xFFFFA500;
        drawCenteredTextWithShadow(textRenderer, tile.structure.size() + " blocks.", 90, 70, color);
        drawCenteredTextWithShadow(textRenderer, String.valueOf(tile.size), 90, 60, color);
    }
}
