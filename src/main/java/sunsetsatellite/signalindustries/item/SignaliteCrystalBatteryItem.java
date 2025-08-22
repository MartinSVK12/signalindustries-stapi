package sunsetsatellite.signalindustries.item;

import net.danygames2014.nyalib.fluid.FluidHandlerItem;
import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.signalindustries.data.SIFluids;
import sunsetsatellite.signalindustries.data.SIItems;

public class SignaliteCrystalBatteryItem extends TemplateItem implements FluidHandlerItem, CustomTooltipProvider {

    public final boolean infinite;

    public SignaliteCrystalBatteryItem(Identifier identifier, boolean infinite) {
        super(identifier);
        this.infinite = infinite;
    }

    @Override
    public boolean canExtractFluid(ItemStack thisStack) {
        if(infinite) return true;
        return getFluidCapacity(thisStack,0) > getRemainingCapacity(thisStack,0) && thisStack.getItem().id == SIItems.signalumCrystalBattery.id;
    }

    @Override
    public FluidStack extractFluid(ItemStack thisStack, int slot, int amount) {
        if(!canExtractFluid(thisStack)) return null;
        if(infinite) return new FluidStack(SIFluids.ENERGY, amount);

        FluidStack fluidStack = getFluid(thisStack, 0);

        int take = Math.min(amount, fluidStack.amount);

        writeNbt(thisStack, new FluidStack(SIFluids.ENERGY, fluidStack.amount-take));

        return new FluidStack(SIFluids.ENERGY, take);
    }

    @Override
    public boolean canInsertFluid(ItemStack thisStack) {
        return getRemainingCapacity(thisStack, 0) > 0;
    }

    @Override
    public FluidStack insertFluid(ItemStack thisStack, FluidStack fluidStack, int slot) {
        if(!canInsertFluid(thisStack)) return fluidStack;
        if(!fluidStack.isFluidEqual(new FluidStack(SIFluids.ENERGY))) return fluidStack;
        return insertFluid(thisStack, fluidStack);
    }

    @Override
    public FluidStack insertFluid(ItemStack thisStack, FluidStack fluidStack) {
        if(!canInsertFluid(thisStack)) return fluidStack;
        if(!fluidStack.isFluidEqual(new FluidStack(SIFluids.ENERGY))) return fluidStack;

        if(infinite) return null;

        int remaining = getRemainingCapacity(thisStack, 0);
        int saturation = readNbt(thisStack).amount;
        int amount = fluidStack.amount;
        if(remaining == 0) return fluidStack;

        if(amount > remaining) {
            fluidStack.amount -= remaining;
            writeNbt(thisStack, new FluidStack(SIFluids.ENERGY, saturation+remaining));
            return fluidStack.copy();
        } else {
            writeNbt(thisStack, new FluidStack(SIFluids.ENERGY, saturation+amount));
            return null;
        }
    }

    @Override
    public FluidStack getFluid(ItemStack thisStack, int slot) {
        if(infinite) return new FluidStack(SIFluids.ENERGY, Integer.MAX_VALUE);
        return readNbt(thisStack);
    }

    @Override
    public boolean setFluid(ItemStack thisStack, int slot, FluidStack fluidStack) {
        if(!fluidStack.isFluidEqual(new FluidStack(SIFluids.ENERGY))) return false;
        if(!canInsertFluid(thisStack)) return false;

        writeNbt(thisStack, fluidStack);
        return true;
    }

    @Override
    public int getFluidSlots(ItemStack thisStack) {
        return 1;
    }

    @Override
    public int getFluidCapacity(ItemStack thisStack, int slot) {
        if(infinite) return Integer.MAX_VALUE;
        return thisStack.getStationNbt().getInt("size")*1000;
    }

    public int getRemainingCapacity(ItemStack thisStack, int slot) {
        if(infinite) return Integer.MAX_VALUE;
        return (thisStack.getStationNbt().getInt("size")*1000)-thisStack.getStationNbt().getInt("saturation");
    }

    @Override
    public FluidStack[] getFluids(ItemStack thisStack) {
        if(infinite) return new FluidStack[]{new FluidStack(SIFluids.ENERGY, Integer.MAX_VALUE)};
        return new FluidStack[]{readNbt(thisStack)};
    }

    @Override
    public @NotNull String[] getTooltip(ItemStack itemStack, String s) {
        StringBuilder text = new StringBuilder();
        if(infinite){
            text.append("Size: 1 | Saturation: Infinite");
        } else {
            text.append("Size: ").append(itemStack.getStationNbt().getInt("size"));
            text.append(" | ").append("Saturation: ").append(itemStack.getStationNbt().getInt("saturation"));
        }
        return new String[]{s,text.toString()};
    }

    public void writeNbt(ItemStack thisStack, FluidStack stack){
        thisStack.getStationNbt().putInt("saturation", stack.amount);
    }

    public FluidStack readNbt(ItemStack thisStack){
        return new FluidStack(SIFluids.ENERGY, thisStack.getStationNbt().getInt("saturation"));
    }
}
