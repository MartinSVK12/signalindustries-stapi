package sunsetsatellite.signalindustries.fluid;

import net.danygames2014.nyalib.capability.CapabilityHelper;
import net.danygames2014.nyalib.capability.item.fluidhandler.FluidHandlerItemCapability;
import net.danygames2014.nyalib.fluid.FluidHandler;
import net.danygames2014.nyalib.fluid.FluidHandlerItem;
import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import sunsetsatellite.signalindustries.util.SlotFluid;

import java.util.ArrayList;
import java.util.List;

public abstract class FluidScreenHandler extends ScreenHandler {

    public ArrayList<SlotFluid> fluidSlots = new ArrayList<>();
    public List<FluidStack> fluidItemStacks = new ArrayList<>();
    public FluidHandler fluidInventory;
    public Inventory itemInventory;

    public FluidScreenHandler(FluidHandler fluidInventory){
        this.fluidInventory = fluidInventory;
        if(fluidInventory instanceof Inventory) {
            itemInventory = (Inventory) fluidInventory;
        }
    }

    public SlotFluid getFluidSlot(int idx) { return this.fluidSlots.get(idx); }
    public void putFluidInSlot(int idx, FluidStack fluid) { this.getFluidSlot(idx).putStack(fluid);}

    protected void addFluidSlot(SlotFluid slot){
        slot.slotNumber = this.fluidSlots.size();
        this.fluidSlots.add(slot);
        this.fluidItemStacks.add(null);
    }

    public FluidStack onFluidSlotClick(int index, int button, boolean shift, PlayerEntity player) {
        if(index == -999){
            return null;
        }
        SlotFluid slot = fluidSlots.get(index);
        PlayerInventory inventory = player.inventory;

        if(slot != null){
            ItemStack stack = inventory.getCursorStack();
            if(stack == null) return null;
            FluidHandlerItemCapability cap = CapabilityHelper.getCapability(stack, FluidHandlerItemCapability.class);
            if(cap != null){
                FluidStack fluid = cap.getFluid(0);
                FluidStack invFluid = fluidInventory.getFluid(index, null);
                if(fluid != null){
                    if(cap.canExtractFluid() && fluidInventory.canInsertFluid(null)){
                        if(invFluid == null) {
                            FluidStack fluidStack = cap.extractFluid(getRemainingFluidCapacity(fluidInventory,index));
                            FluidStack remainder = fluidInventory.insertFluid(fluidStack, index, null);
                            if(remainder != null && remainder.amount > 0) {
                                cap.insertFluid(remainder);
                            }
                        } else if (invFluid.amount <= fluidInventory.getFluidCapacity(0, null)) {
                            FluidStack fluidStack = cap.extractFluid(getRemainingFluidCapacity(fluidInventory,index));
                            FluidStack remainder = fluidInventory.insertFluid(fluidStack, index, null);
                            if(remainder != null && remainder.amount > 0) {
                                cap.insertFluid(remainder);
                            }
                        } else if (invFluid.amount >= fluidInventory.getFluidCapacity(0, null)) {
                            if (cap.canInsertFluid() && fluidInventory.canExtractFluid(null)) {
                                FluidStack fluidStack = fluidInventory.extractFluid(index, getRemainingFluidCapacity(cap,0), null);
                                FluidStack remainder = cap.insertFluid(fluidStack);
                                if(remainder != null && remainder.amount > 0) {
                                    fluidInventory.insertFluid(remainder, index, null);
                                }
                            }
                        }
                    } else if (cap.canInsertFluid() && fluidInventory.canExtractFluid(null)) {
                        FluidStack fluidStack = fluidInventory.extractFluid(index, getRemainingFluidCapacity(cap,0), null);
                        FluidStack remainder = cap.insertFluid(fluidStack);
                        if(remainder != null && remainder.amount > 0) {
                            fluidInventory.insertFluid(remainder, index, null);
                        }
                    }
                } else if (cap.canInsertFluid() && fluidInventory.canExtractFluid(null)) {
                    FluidStack fluidStack = fluidInventory.extractFluid(index, getRemainingFluidCapacity(cap, 0), null);
                    FluidStack remainder = cap.insertFluid(fluidStack);
                    if(remainder != null && remainder.amount > 0) {
                        fluidInventory.insertFluid(remainder, index, null);
                    }
                }
            }
        }
        sendContentUpdates();
        return null;
    }

    private int getRemainingFluidCapacity(FluidHandlerItemCapability handler, int slot){
        return slot >= handler.getFluidSlots() ? 0 : handler.getFluidCapacity(slot) - (handler.getFluid(slot) == null ? 0 : handler.getFluid(slot).amount);
    }

    private int getRemainingFluidCapacity(FluidHandler handler, int slot){
        return slot >= handler.getFluidSlots(null) ? 0 : handler.getFluidCapacity(slot, null) - (handler.getFluid(slot, null) == null ? 0 : handler.getFluid(slot, null).amount);
    }

    @Override
    public void sendContentUpdates() {
        super.sendContentUpdates();
        for (int i = 0; i < this.fluidSlots.size(); i++) {
            FluidStack fluidStack = this.fluidSlots.get(i).getFluidStack();
            FluidStack fluidStack1 = this.fluidItemStacks.get(i);
            this.fluidItemStacks.set(i, fluidStack1);
            for (Object listener : this.listeners) {
                ((FluidSlotUpdater) listener).updateFluidSlot(this, i, fluidStack);
            }
        }
    }
}
