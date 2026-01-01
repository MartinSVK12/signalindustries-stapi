package sunsetsatellite.signalindustries.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.NetworkHandler;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.vector.Vec3i;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.block.entity.AlloySmelterBlockEntity;
import sunsetsatellite.signalindustries.block.entity.CrystalCutterBlockEntity;
import sunsetsatellite.signalindustries.block.entity.base.FluidItemContainerBlockEntity;
import sunsetsatellite.signalindustries.mp.packet.RecipeIdChangePacket;
import sunsetsatellite.signalindustries.screen.base.FluidIOScreen;
import sunsetsatellite.signalindustries.screen.base.ItemIOScreen;
import sunsetsatellite.signalindustries.screen.handler.AlloySmelterScreenHandler;
import sunsetsatellite.signalindustries.screen.handler.CrystalCutterScreenHandler;

public class CrystalCutterScreen extends HandledScreen {

    public CrystalCutterBlockEntity tile;

    public CrystalCutterScreen(PlayerInventory playerInventory, FluidItemContainerBlockEntity blockEntity) {
        super(new CrystalCutterScreenHandler(playerInventory, blockEntity));
        this.tile = (CrystalCutterBlockEntity) blockEntity;
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

        buttons.add(new ButtonWidget(2, Math.round((float) width / 2) + 60, Math.round((float) height / 2) - 30, 20, 20, String.valueOf(tile.recipeId)));

        super.init();
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        //TODO: recipe id change tooltip
        super.render(mouseX, mouseY, delta);
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        if(!button.active) return;

        if (button.id == 2) {
            if (tile.world.isRemote) {
                if (tile.recipeId > 0 && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
                    button.text = String.valueOf(tile.recipeId - 1);
                    Catalyst.getClientHandler().sendPacket(new RecipeIdChangePacket(tile.recipeId - 1, new Vec3i(tile.x, tile.y, tile.z), tile.getClass()));
                } else {
                    button.text = String.valueOf(tile.recipeId + 1);
                    Catalyst.getClientHandler().sendPacket(new RecipeIdChangePacket(tile.recipeId + 1, new Vec3i(tile.x, tile.y, tile.z), tile.getClass()));
                }

            } else {
                if (tile.recipeId > 0 && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
                    tile.recipeId--;
                } else {
                    tile.recipeId++;
                }
                button.text = String.valueOf(tile.recipeId);
            }
        }

        if(button == itemIoButton){
            minecraft.setScreen(new ItemIOScreen(minecraft.player, container, this, tile));
        } else if(button == fluidIoButton){
            minecraft.setScreen(new FluidIOScreen(minecraft.player, container, this, tile));
        }
        super.buttonClicked(button);
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int bg = switch (tile.getTier()){
            case BASIC -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/generic_basic_machine_double.png");
            case REINFORCED -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/generic_reinforced_machine_double.png");
            default -> this.minecraft.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/gui/generic_prototype_machine_double.png");
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
