package sunsetsatellite.signalindustries.screen.base;

import net.glasslauncher.mods.alwaysmoreitems.gui.Tooltip;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.Tessellator;

import net.minecraft.client.render.platform.Lighting;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.modificationstation.stationapi.api.block.BlockState;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.BlockInstance;
import sunsetsatellite.catalyst.core.util.Connection;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.vector.Vec3i;
import sunsetsatellite.signalindustries.block.base.TieredMachineBlock;
import sunsetsatellite.signalindustries.block.entity.base.FluidItemContainerBlockEntity;
import sunsetsatellite.signalindustries.mp.packet.IOChangePacket;
import sunsetsatellite.signalindustries.render.RenderBlocksOnScreen;
import sunsetsatellite.signalindustries.util.IOPreview;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class VisualItemIOScreen extends Screen {

    public int xSize = 176;
    public int ySize = 166;
    public Screen parentScreen;
    public PlayerEntity player;
    public FluidItemContainerBlockEntity tile;
    public ScreenHandler container;
    private float yRot = 0;
    private float xRot = 0;
    private Direction lastHoveredSide = null;

    public VisualItemIOScreen(PlayerEntity player, ScreenHandler handler, Screen parent, FluidItemContainerBlockEntity tile) {
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
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)centerX, (float)centerY, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        renderBlock(mouseX, mouseY);
        Lighting.turnOff();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        drawForeground();
        GL11.glPopMatrix();
        super.render(mouseX, mouseY, delta);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if(lastHoveredSide != null){
            List<Object> list = new ArrayList<>();
            list.add(lastHoveredSide.name());
            String slot = "Slot: " + (tile.activeItemSlots.get(lastHoveredSide) == -1 ? "Any" : String.valueOf(tile.activeItemSlots.get(lastHoveredSide)));
            String io = "I/O: " + tile.itemConnections.get(lastHoveredSide).name();
            list.add(slot);
            list.add(io);
            Tooltip.INSTANCE.setTooltip(list,mouseX + 8,mouseY + 8);
        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public void renderBlock(int mx, int my) {
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPushMatrix();
        GL11.glTranslatef(85,45, 900F);

        double size = 25;

        float centerX = (this.width - this.xSize) / 2f + 85;
        float centerY = (this.height - this.ySize) / 2f + 50;

        if(Mouse.isButtonDown(1)){
            xRot = mx - centerX;
            yRot = my - centerY;
        }

        GL11.glScaled(size, -size, size);
        GL11.glRotatef(-(float) Math.atan(yRot / 40F) * 60F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef((float) Math.atan(xRot / 40F) * 80F, 0, 1F, 0);
        lastHoveredSide = getHoveredSide(mx, my);
        RenderBlocksOnScreen r = new RenderBlocksOnScreen();
        ArrayList<BlockInstance> list = new ArrayList<>();
        list.add(new BlockInstance(tile.getBlock(),new Vec3i(), tile.world.getBlockState(tile.x, tile.y, tile.z), tile));
        TieredMachineBlock.ioConfig = true;
        TieredMachineBlock.ioType = IOPreview.ITEM;
        TieredMachineBlock.ioPos = tile.getPosition();
        TieredMachineBlock.ioWorld = tile.world;
        r.drawBlock(list, Tessellator.INSTANCE, tile.world, 0,0,0, 1);
        TieredMachineBlock.ioConfig = false;
        TieredMachineBlock.ioType = IOPreview.NONE;
        TieredMachineBlock.ioPos = null;
        TieredMachineBlock.ioWorld = null;
        for (Direction dir : Direction.values()) {
            Block block = dir.getBlock(tile.world, tile);
            BlockEntity te = dir.getTileEntity(tile.world, tile);
            int meta = dir.getBlockMeta(tile.world, tile);
            BlockState state = dir.getBlockState(tile.world, tile);
            Vec3i vec = dir.getVec();
            if(block != null){
                list.add(new BlockInstance(block, vec, meta, state, te));
            }
        }
        r.drawBlock(list, Tessellator.INSTANCE, tile.world, 0, 0, 0, 0.4f);
        GL11.glPopMatrix();
    }

    public Direction getHoveredSide(int mx, int my) {
        FloatBuffer modelMatrix = BufferUtils.createFloatBuffer(16);
        FloatBuffer projMatrix = BufferUtils.createFloatBuffer(16);
        IntBuffer viewport = BufferUtils.createIntBuffer(16);
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelMatrix);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projMatrix);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);

        Direction hovered = null;
        double minDepth = Double.MAX_VALUE;
        ScreenScaler scaler = new ScreenScaler(minecraft.options, minecraft.displayWidth, minecraft.displayHeight);

        for (Direction dir : Direction.values()) {
            Vector3f[] faceVertices = Direction.getVerticesForSide(dir);

            // project vertices to screen
            Vector2f[] screenCoords = new Vector2f[4];
            float avgDepth = 0;

            for(int i = 0; i < 4; i++) {
                FloatBuffer screenPos = BufferUtils.createFloatBuffer(3);
                GLU.gluProject(faceVertices[i].x, faceVertices[i].y, faceVertices[i].z,
                        modelMatrix, projMatrix, viewport, screenPos);

                float screenX = screenPos.get(0) / scaler.scaleFactor;
                float screenY = (viewport.get(3) - screenPos.get(1)) / scaler.scaleFactor;
                screenCoords[i] = new Vector2f(screenX, screenY);
                avgDepth += screenPos.get(2);
            }

            // check if mouse is in projected polygon
            if (isPointInPolygon(mx, my, screenCoords)) {
                // check depth to pick the closest face
                if (avgDepth < minDepth) {
                    minDepth = avgDepth;
                    hovered = dir;
                }
            }
        }
        return hovered;
    }

    private boolean isPointInPolygon(int x, int y, Vector2f[] vertices) {
        boolean inside = false;
        int n = vertices.length;
        for (int i = 0, j = n - 1; i < n; j = i++) {
            if (((vertices[i].y > y) != (vertices[j].y > y)) &&
                    (x < (vertices[j].x - vertices[i].x) * (y - vertices[i].y) / (vertices[j].y - vertices[i].y) + vertices[i].x)) {
                inside = !inside;
            }
        }
        return inside;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int buttonNum) {
        super.mouseClicked(mouseX, mouseY, buttonNum);
        if(lastHoveredSide != null && buttonNum == 0) {
            for (Direction dir : Direction.values()) {
                if(dir == lastHoveredSide){
                    if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
                        tile.cycleActiveItemSlotForSide(dir, false);
                    } else {
                        tile.cycleItemIOForSide(dir);
                    }
                    if(minecraft.isWorldRemote()){
                        Vec3i position = tile.getPosition();
                        Connection connection = tile.itemConnections.get(dir);
                        int slot = tile.activeItemSlots.get(dir);
                        Catalyst.getClientHandler().sendPacket(new IOChangePacket(position, connection, dir, IOPreview.ITEM, slot, tile.getClass()));
                    }
                    break;
                }
            }
        }
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
            if(button.id == 0){
                minecraft.setScreen(new ItemIOScreen(minecraft.player, container, this, tile));
            }

            if (button.id == 1) {
                for (Direction direction : Direction.values()) {
                    tile.itemConnections.replace(direction, Connection.INPUT);
                }
                currentButtonId = button.id;
            }

            if (button.id == 2) {
                for (Direction direction : Direction.values()) {
                    tile.itemConnections.replace(direction, Connection.OUTPUT);
                }
                currentButtonId = button.id;
            }

            if (button.id == 3) {
                for (Direction direction : Direction.values()) {
                    tile.itemConnections.replace(direction, Connection.NONE);
                }
                currentButtonId = button.id;
            }

            if(minecraft.isWorldRemote() && currentButtonId != -1){
                for (Direction dir : Direction.values()) {
                    Vec3i position = tile.getPosition();
                    Connection connection = tile.itemConnections.get(dir);
                    int slot = tile.activeItemSlots.get(dir);
                    Catalyst.getClientHandler().sendPacket(new IOChangePacket(position, connection, dir, IOPreview.ITEM, slot, tile.getClass()));
                }
            }
        }
        super.buttonClicked(button);
    }

    protected void buttonClickedAlt(ButtonWidget button) {

    }

    protected void drawBackground(float tickDelta) {
        int id = minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/config.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(id);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexture(j, k, 0, 0, xSize, ySize);
    }

    protected void drawForeground() {
        textRenderer.draw("Configure: Items", 45, 6, 0xFF404040);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void init() {
        buttons.add(new ButtonWidget(0, (width / 2) + 60, (height / 2) - 75, 20, 20, "M"));

        buttons.add(new ButtonWidget(1, (width / 2) - 85, (height / 2) - 12, 30, 15, "All I"));
        buttons.add(new ButtonWidget(2, (width / 2) - 55, (height / 2) - 12, 30, 15, "All O"));
        buttons.add(new ButtonWidget(3, (width / 2) - 25, (height / 2) - 12, 30, 15, "Clear"));

        super.init();
    }
}
