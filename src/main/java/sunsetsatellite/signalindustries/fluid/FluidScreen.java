package sunsetsatellite.signalindustries.fluid;

import net.glasslauncher.mods.alwaysmoreitems.gui.Tooltip;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Formatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.catalyst.core.util.ExtendedScreenDraw;
import sunsetsatellite.signalindustries.util.SlotFluid;

import java.util.List;

public abstract class FluidScreen extends HandledScreen {

    public FluidScreenHandler fluidHandler;

    public FluidScreen(FluidScreenHandler handler) {
        super(handler);
        this.fluidHandler = handler;
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        super.render(mouseX, mouseY, delta);

        int centerX = (this.width - this.backgroundWidth) / 2;
        int centerY = (this.height - this.backgroundHeight) / 2;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)centerX, (float)centerY, 0.0F);
        SlotFluid slot = null;

        for (int i = 0; i < fluidHandler.fluidSlots.size(); i++) {
            SlotFluid currentSlot = fluidHandler.fluidSlots.get(i);
            boolean mouseOver = this.isPointOverFluidSlot(currentSlot, mouseX, mouseY);

            if(currentSlot.getFluidStack() != null){
                itemRenderer.renderGuiItem(this.textRenderer, this.minecraft.textureManager, new ItemStack(currentSlot.getFluidStack().fluid.getFlowingBlock()), currentSlot.x, currentSlot.y);
                itemRenderer.renderGuiItemDecoration(this.textRenderer, this.minecraft.textureManager,  new ItemStack(currentSlot.getFluidStack().fluid.getFlowingBlock()), currentSlot.x, currentSlot.y);
            }

            if (mouseOver) {
                slot = currentSlot;
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                this.fillGradient(slot.x, slot.y, slot.x + 16, slot.y + 16, 0x80ffffff, 0x80ffffff);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
            }
        }

        if(slot != null && !slot.hasStack()) {
            Tooltip.INSTANCE.setTooltip(List.of("Empty", Formatting.GRAY+"0/"+slot.fluidInventory.getFluidCapacity(slot.slotIndex, null)), mouseX, mouseY);
        } else if (slot != null && slot.hasStack()) {
            Tooltip.INSTANCE.setTooltip(List.of(slot.getFluidStack().fluid.getTranslatedName(), Formatting.GRAY+String.valueOf(slot.getFluidStack().amount)+"/"+slot.fluidInventory.getFluidCapacity(slot.slotIndex, null)), mouseX, mouseY);
        }

        GL11.glPopMatrix();
    }

    public boolean isPointOverFluidSlot(SlotFluid slot, int i2, int i3) {
        int i4 = (this.width - this.backgroundWidth) / 2;
        int i5 = (this.height - this.backgroundHeight) / 2;
        i2 -= i4;
        i3 -= i5;
        return i2 >= slot.x - 1 && i2 < slot.x + 16 + 1 && i3 >= slot.y - 1 && i3 < slot.y + 16 + 1;
    }

    public SlotFluid getFluidSlotAtPosition(int i1, int i2) {
        FluidScreenHandler fluidContainer = ((FluidScreenHandler) container);
        for(int i3 = 0; i3 < fluidContainer.fluidSlots.size(); ++i3) {
            SlotFluid slot4 = fluidContainer.fluidSlots.get(i3);
            if(this.isPointOverFluidSlot(slot4, i1, i2)) {
                return slot4;
            }
        }

        return null;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        if (button == 0 || button == 1 || button == 2 || button == 10) {
            this.clickFluidInventory(mouseX, mouseY, button);
        }
    }

    public void clickFluidInventory(int mx, int my, int button) {
        SlotFluid slot = this.getFluidSlotAtPosition(mx, my);
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;

        int slotId = -1;
        if (slot != null) {
            slotId = slot.slotIndex;
        }

        boolean outsideScreen = mx < x || my < y || mx >= x + this.backgroundWidth || my >= y+ this.backgroundHeight;

        if(outsideScreen){
            slotId = -999;
        }

        if(slotId != -1){
            boolean shift = slotId != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54) || button == 10);
            boolean control = slotId != -999 && (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157));

            ((FluidPickupController) this.minecraft.interactionManager).onFluidPickUpFromInventory(this.container.syncId, slotId, button == 10 ? 0 : button, shift, control, this.minecraft.player);
        }
    }
}
