package sunsetsatellite.signalindustries.screen.base;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.platform.Lighting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.screen.ScreenHandler;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.Connection;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.vector.Vec3i;
import sunsetsatellite.signalindustries.block.entity.base.FluidItemContainerBlockEntity;
import sunsetsatellite.signalindustries.interfaces.HasIOPreview;
import sunsetsatellite.signalindustries.mp.packet.IOChangePacket;
import sunsetsatellite.signalindustries.util.IOPreview;

public class FluidIOScreen extends Screen {

    public int xSize = 176;
    public int ySize = 166;
    public Screen parentScreen;
    public PlayerEntity player;
    public FluidItemContainerBlockEntity tile;
    public ScreenHandler container;

    public FluidIOScreen(PlayerEntity player, ScreenHandler handler, Screen parent, FluidItemContainerBlockEntity tile) {
        super();
        this.player = player;
        this.tile = tile;
        this.parentScreen = parent;
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        int centerX = (this.width - this.xSize) / 2;
        int centerY = (this.height - this.ySize) / 2;
        drawBackground(delta);
        GL11.glPushMatrix();
        GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
        Lighting.turnOn();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)centerX, (float)centerY, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        Lighting.turnOff();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_LIGHTING);
        drawForeground();
        GL11.glPopMatrix();
        super.render(mouseX, mouseY, delta);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int buttonNum) {
        super.mouseClicked(mouseX, mouseY, buttonNum);
        if(buttonNum == 1){
            for (Object object : buttons) {
                ButtonWidget button = (ButtonWidget) object;
                if(button.isMouseOver(minecraft,mouseX,mouseY)){
                    this.minecraft.soundManager.playSound("random.click", 1.0F, 1.0F);
                    this.buttonClickedAlt(button);
                }
            }
        }
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        if(tile != null){
            int currentButtonId = -1;
            if(button.id >= 0 && button.id <= 5){
                Direction dir = Direction.values()[button.id];
                tile.cycleFluidIOForSide(dir);
                button.text = tile.fluidConnections.get(dir).getLetter();
                currentButtonId = button.id;
            }

            if(button.id > 5 && button.id < 12){
                Direction dir = Direction.values()[button.id-6];
                tile.cycleActiveFluidSlotForSide(dir,false);
                button.text = String.valueOf(tile.activeFluidSlots.get(dir));
                currentButtonId = button.id;
            }

            if(button.id == 12) {
                for (Direction direction : Direction.values()) {
                    tile.fluidConnections.replace(direction, Connection.INPUT);
                }
                for (Object o : buttons) {
                    ButtonWidget b = (ButtonWidget) o;
                    if(b.id >= 0 && b.id < 6){
                        b.text = tile.fluidConnections.get(Direction.values()[b.id]).getLetter();
                    }
                }
                currentButtonId = button.id;
            }

            if(button.id == 13) {
                for (Direction direction : Direction.values()) {
                    tile.fluidConnections.replace(direction, Connection.OUTPUT);
                }
                for (Object o : buttons) {
                    ButtonWidget b = (ButtonWidget) o;
                    if(b.id >= 0 && b.id < 6){
                        b.text = tile.fluidConnections.get(Direction.values()[b.id]).getLetter();
                    }
                }
                currentButtonId = button.id;
            }

            if (button.id == 14) {
                if (tile instanceof HasIOPreview p) {
                    p.setPreview(p.getPreview() != IOPreview.FLUID ? IOPreview.FLUID : IOPreview.NONE);
                }
            }

            if(minecraft.isWorldRemote() && currentButtonId != -1){
                if(currentButtonId == 12 || currentButtonId == 13){
                    for (Direction dir : Direction.values()) {
                        Vec3i position = tile.getPosition();
                        Connection connection = tile.fluidConnections.get(dir);
                        int slot = tile.activeFluidSlots.get(dir);
                        Catalyst.getClientHandler().sendPacket(new IOChangePacket(position, connection, dir, IOPreview.FLUID, slot, tile.getClass()));
                    }
                } else {
                    Direction dir = Direction.Y_POS;
                    if(currentButtonId <= 5 && currentButtonId >= 0){
                        dir = Direction.values()[currentButtonId];
                    } else if(currentButtonId > 5 && currentButtonId < 12) {
                        dir = Direction.values()[currentButtonId - 6];
                    }
                    Vec3i position = tile.getPosition();
                    Connection connection = tile.fluidConnections.get(dir);
                    int slot = tile.activeFluidSlots.get(dir);
                    Catalyst.getClientHandler().sendPacket(new IOChangePacket(position, connection, dir, IOPreview.FLUID, slot, tile.getClass()));
                }

            }
        }
        super.buttonClicked(button);
    }

    protected void buttonClickedAlt(ButtonWidget button) {
        if(button.id > 5 && button.id < 12){
            Direction dir = Direction.values()[button.id-6];
            tile.cycleActiveFluidSlotForSide(dir,true);
            button.text = tile.activeFluidSlots.get(dir) == -1 ? "*" : String.valueOf(tile.activeFluidSlots.get(dir));

            if(minecraft.isWorldRemote()){
                Vec3i position = tile.getPosition();
                Connection connection = tile.fluidConnections.get(dir);
                int slot = tile.activeFluidSlots.get(dir);
                Catalyst.getClientHandler().sendPacket(new IOChangePacket(position, connection, dir, IOPreview.FLUID, slot, tile.getClass()));
            }
        }
    }

    protected void drawBackground(float tickDelta) {
        int id = minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/ioconfig.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(id);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexture(j, k, 0, 0, xSize, ySize);
    }

    protected void drawForeground() {
        textRenderer.draw("Configure: Fluids", 45, 6, 0xFF404040);
        textRenderer.draw("I/O", 78, 70, 0xFF404040);
        textRenderer.draw("Slot", 128, 70, 0xFF404040);
        textRenderer.draw("Y+", 26, 22, 0xFFFFFFFF);
        textRenderer.draw("Y-", 26, 58, 0xFFFFFFFF);
        textRenderer.draw("Z+", 26, 40, 0xFFFFFFFF);
        textRenderer.draw("X+", 44, 40, 0xFFFFFFFF);
        textRenderer.draw("Z-", 44, 58, 0xFFFFFFFF);
        textRenderer.draw("X-", 8, 40, 0xFFFFFFFF);
    }

    @Override
    public void init() {
        buttons.add(new ButtonWidget(2, Math.round(width / 2f) - 10, Math.round(height / 2f) - 63, 15, 15, tile.fluidConnections.get(Direction.Y_POS).getLetter())); //Y+
        buttons.add(new ButtonWidget(4, Math.round(width / 2f) - 10, Math.round(height / 2f) - 48, 15, 15, tile.fluidConnections.get(Direction.Z_POS).getLetter())); //Z+
        buttons.add(new ButtonWidget(3, Math.round(width / 2f) - 10, Math.round(height / 2f) - 33, 15, 15, tile.fluidConnections.get(Direction.Y_NEG).getLetter())); //Y-
        buttons.add(new ButtonWidget(0, Math.round(width / 2f) + 4, Math.round(height / 2f) - 48, 15, 15, tile.fluidConnections.get(Direction.X_POS).getLetter())); //X+
        buttons.add(new ButtonWidget(1, Math.round(width / 2f) - 24, Math.round(height / 2f) - 48, 15, 15, tile.fluidConnections.get(Direction.X_NEG).getLetter())); //X-
        buttons.add(new ButtonWidget(5, Math.round(width / 2f) + 4, Math.round(height / 2f) - 33, 15, 15, tile.fluidConnections.get(Direction.Z_NEG).getLetter())); //Z-

        buttons.add(new ButtonWidget(8, Math.round(width / 2f) - 10 + 50, Math.round(height / 2f) - 63, 15, 15, tile.activeFluidSlots.get(Direction.Y_POS) == -1 ? "*" : String.valueOf(tile.activeFluidSlots.get(Direction.Y_POS))));
        buttons.add(new ButtonWidget(10, Math.round(width / 2f) - 10 + 50, Math.round(height / 2f) - 48, 15, 15, tile.activeFluidSlots.get(Direction.Z_POS) == -1 ? "*" : String.valueOf(tile.activeFluidSlots.get(Direction.Z_POS))));
        buttons.add(new ButtonWidget(9, Math.round(width / 2f) - 10 + 50, Math.round(height / 2f) - 33, 15, 15, tile.activeFluidSlots.get(Direction.Y_NEG) == -1 ? "*" : String.valueOf(tile.activeFluidSlots.get(Direction.Y_NEG))));
        buttons.add(new ButtonWidget(6, Math.round(width / 2f) + 4 + 50, Math.round(height / 2f) - 48, 15, 15, tile.activeFluidSlots.get(Direction.X_POS) == -1 ? "*" : String.valueOf(tile.activeFluidSlots.get(Direction.X_POS))));
        buttons.add(new ButtonWidget(7, Math.round(width / 2f) - 24 + 50, Math.round(height / 2f) - 48, 15, 15, tile.activeFluidSlots.get(Direction.X_NEG) == -1 ? "*" : String.valueOf(tile.activeFluidSlots.get(Direction.X_NEG))));
        buttons.add(new ButtonWidget(11, Math.round(width / 2f) + 4 + 50, Math.round(height / 2f) - 33, 15, 15, tile.activeFluidSlots.get(Direction.Z_NEG) == -1 ? "*" : String.valueOf(tile.activeFluidSlots.get(Direction.Z_NEG))));

        buttons.add(new ButtonWidget(12,(width / 2) - 85, (height / 2)-12, 30, 15, "All I"));
        buttons.add(new ButtonWidget(13, (width / 2) - 55, (height / 2)-12, 30, 15, "All O"));

        if(tile instanceof HasIOPreview){
            buttons.add(new ButtonWidget(14, (width / 2) + 60, (height / 2) - 75, 20, 20, "P"));
        }

        if(tile.getFluidSlots(null) == 1){
            ((ButtonWidget) buttons.get(6)).active = false;
            ((ButtonWidget) buttons.get(7)).active = false;
            ((ButtonWidget) buttons.get(8)).active = false;
            ((ButtonWidget) buttons.get(9)).active = false;
            ((ButtonWidget) buttons.get(10)).active = false;
            ((ButtonWidget) buttons.get(11)).active = false;
        }

        super.init();
    }
}
