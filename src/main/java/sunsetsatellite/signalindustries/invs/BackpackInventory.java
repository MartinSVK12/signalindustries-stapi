package sunsetsatellite.signalindustries.invs;

import net.danygames2014.nyalib.capability.CapabilityHelper;
import net.danygames2014.nyalib.capability.item.fluidhandler.FluidHandlerItemCapability;
import net.danygames2014.nyalib.capability.item.itemhandler.ItemHandlerItemCapability;
import net.danygames2014.nyalib.fluid.FluidStack;
import net.danygames2014.nyalib.fluid.block.FluidHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class BackpackInventory implements Inventory, FluidHandler {

    public ItemHandlerItemCapability itemCap;
    public FluidHandlerItemCapability fluidCap;
    public ItemStack stack;

    public BackpackInventory(PlayerInventory playerInventory, ItemStack stack) {
        this.stack = stack;
        itemCap = CapabilityHelper.getCapability(stack, ItemHandlerItemCapability.class);
        fluidCap = CapabilityHelper.getCapability(stack, FluidHandlerItemCapability.class);
    }

    public BackpackInventory() {

    }

    @Override
    public int size() {
        return itemCap.getItemSlots();
    }

    @Override
    public ItemStack getStack(int slot) {
        return itemCap.getItem(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return itemCap.extractItem(slot, amount);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        itemCap.setItem(stack, slot);
    }

    @Override
    public String getName() {
        return "container.signalindustries.backpack.name";
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean canExtractFluid(@Nullable Direction direction) {
        return fluidCap.canExtractFluid();
    }

    @Override
    public FluidStack extractFluid(int slot, int amount, @Nullable Direction direction) {
        return fluidCap.extractFluid(slot, amount);
    }

    @Override
    public boolean canInsertFluid(@Nullable Direction direction) {
        return fluidCap.canInsertFluid();
    }

    @Override
    public FluidStack insertFluid(FluidStack stack, int slot, @Nullable Direction direction) {
        return fluidCap.insertFluid(stack, slot);
    }

    @Override
    public FluidStack insertFluid(FluidStack stack, @Nullable Direction direction) {
        return fluidCap.insertFluid(stack);
    }

    @Override
    public FluidStack getFluid(int slot, @Nullable Direction direction) {
        return fluidCap.getFluid(slot);
    }

    @Override
    public boolean setFluid(int slot, FluidStack stack, @Nullable Direction direction) {
        return fluidCap.setFluid(slot, stack);
    }

    @Override
    public int getFluidSlots(@Nullable Direction direction) {
        return fluidCap.getFluidSlots();
    }

    @Override
    public int getFluidCapacity(int slot, @Nullable Direction direction) {
        return fluidCap.getFluidCapacity(slot);
    }

    @Override
    public FluidStack[] getFluids(@Nullable Direction direction) {
        return fluidCap.getFluids();
    }

    @Override
    public boolean canConnectFluid(Direction direction) {
        return true;
    }
}
