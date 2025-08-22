package sunsetsatellite.signalindustries.util;

import net.danygames2014.nyalib.fluid.FluidHandler;
import net.danygames2014.nyalib.fluid.FluidStack;
import sunsetsatellite.catalyst.core.util.io.FluidIO;

public class SlotFluid {

    public final FluidHandler fluidInventory;
    public int slotIndex;
    public int slotNumber;
    public int x;
    public int y;

    public SlotFluid(FluidHandler iFluidInventory, int idx, int x, int y) {
        fluidInventory = iFluidInventory;
        slotIndex = idx;
        this.x = x;
        this.y = y;
    }

    public FluidStack getFluidStack() {
        return fluidInventory.getFluid(slotIndex, null);
    }

    public boolean hasStack(){
        return this.getFluidStack() != null;
    }

    public void putStack(FluidStack stack){
        if(stack == null) {
            fluidInventory.setFluid(slotIndex, null, null);
        } else {
            fluidInventory.setFluid(this.slotIndex, stack, null);
        }
    }

}
