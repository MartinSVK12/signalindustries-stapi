package sunsetsatellite.signalindustries.block.entity.base;

import net.danygames2014.nyalib.fluid.Fluid;
import net.danygames2014.nyalib.fluid.FluidHandler;
import net.danygames2014.nyalib.fluid.FluidStack;
import net.danygames2014.nyalib.item.ItemHandler;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;
import org.jetbrains.annotations.Nullable;
import sunsetsatellite.catalyst.core.util.Connection;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.io.FluidIO;
import sunsetsatellite.catalyst.core.util.io.ItemIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FluidItemContainerBlockEntity extends BlockEntity implements FluidHandler, ItemHandler, Inventory, ItemIO, FluidIO {

    public FluidStack[] fluidContents = new FluidStack[1];
    public ItemStack[] itemContents = new ItemStack[1];
    public int[] fluidCapacity = new int[1];
    public ArrayList<ArrayList<Fluid>> acceptedFluids = new ArrayList<>(fluidContents.length);
    public HashMap<Direction, Connection> fluidConnections = new HashMap<>();
    public HashMap<Direction, Integer> activeFluidSlots = new HashMap<>();
    public HashMap<Direction, Connection> itemConnections = new HashMap<>();
    public HashMap<Direction, Integer> activeItemSlots = new HashMap<>();

    // Fluid Handler

    @Override
    public boolean canConnectFluid(net.modificationstation.stationapi.api.util.math.Direction direction) {
        return fluidConnections.get(Direction.get(direction)) != Connection.NONE;
    }

    @Override
    public boolean canExtractFluid(@Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        if(direction == null) return true;
        return fluidConnections.get(Direction.get(direction)) == Connection.OUTPUT;
    }

    @Override
    public FluidStack extractFluid(int slot, int amount, @Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        if(!canExtractFluid(direction)) return null;
        if (slot >= getFluidSlots(direction)) {
            return null;
        }
        FluidStack stack = getFluid(slot,direction);
        if (stack == null) return null;
        amount = Math.min(amount, stack.amount);
        FluidStack splitStack = new FluidStack(stack.fluid, amount);
        stack.amount -= amount;
        if (stack.amount <= 0) {
            setFluid(0,null, direction);
        }
        return splitStack;
    }

    @Override
    public FluidStack extractFluid(Fluid fluid, int amount, @Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        if(!canExtractFluid(direction)) return null;
        return FluidHandler.super.extractFluid(fluid, amount, direction);
    }

    @Override
    public boolean canInsertFluid(@Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        if(direction == null) return true;
        return fluidConnections.get(Direction.get(direction)) == Connection.INPUT;
    }

    @Override
    public FluidStack insertFluid(FluidStack stack, int slot, @Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        if(!canInsertFluid(direction) || stack == null) return stack;
        FluidStack invStack = fluidContents[slot];
        if(invStack == null) {
            if(!(acceptedFluids.get(slot).contains(stack.fluid))) return stack;
            int splitAmount = Math.min(stack.amount, getFluidCapacity(slot, direction));
            fluidContents[slot] = new FluidStack(stack.fluid,splitAmount);
            stack.amount -= splitAmount;
            if(stack.amount == 0) return null;
            return stack.copy();
        } else {
            if(!invStack.isFluidEqual(stack) || !(acceptedFluids.get(slot).contains(stack.fluid))) return stack;
            int splitAmount = Math.min(stack.amount, getFluidCapacity(slot, direction));
            invStack.amount += splitAmount;
            stack.amount -= splitAmount;
            if(stack.amount == 0) return null;
            return stack.copy();
        }
    }

    @Override
    public FluidStack insertFluid(FluidStack stack, @Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        if(!canInsertFluid(direction) || stack == null) return stack;

        int n = stack.amount;

        for (int i = 0; i < getFluidSlots(direction); i++) {
            FluidStack invStack = getFluid(i, direction);
            if(invStack == null) {
                int amount = Math.min(stack.amount, getFluidCapacity(i, direction));
                n -= amount;
                stack.amount -= amount;
                fluidContents[i] = new FluidStack(stack.fluid,amount);
                if(n <= 0) break;
            } else if(invStack.isFluidEqual(stack) && acceptedFluids.get(i).contains(stack.fluid)) {
                int remaining = Math.min(n,getFluidCapacity(i, direction) - invStack.amount);
                n -= remaining;
                invStack.amount += remaining;
                if(n <= 0) break;
            }
        }

        if(n <= 0){
            return null;
        }

        return new FluidStack(stack.fluid,n);
    }

    @Override
    public FluidStack getFluid(int slot, @Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        return fluidContents[slot];
    }

    @Override
    public int getFluidSlots(@Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        return fluidContents.length;
    }

    @Override
    public int getFluidCapacity(int slot, @Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        return fluidCapacity[slot];
    }

    @Override
    public FluidStack[] getFluids(@Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        return fluidContents;
    }

    public int getRemainingCapacity(int slot) {
        if(fluidContents[slot] == null){
            return fluidCapacity[slot];
        }
        return fluidCapacity[slot]-fluidContents[slot].amount;
    }

    @Override
    public boolean setFluid(int slot, FluidStack fluid, net.modificationstation.stationapi.api.util.math.Direction direction) {
        if(fluid == null || fluid.amount == 0 || fluid.fluid == null){
            this.fluidContents[slot] = null;
            return false;
        }
        if(acceptedFluids.get(slot).contains(fluid.fluid) || acceptedFluids.get(slot).isEmpty()){
            this.fluidContents[slot] = fluid;
            return true;
        }
        return false;
    }

    // Item Handler

    @Override
    public boolean canConnectItem(net.modificationstation.stationapi.api.util.math.Direction direction) {
        return itemConnections.get(Direction.get(direction)) != Connection.NONE;
    }

    @Override
    public boolean canExtractItem(@Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        if(direction == null) return true;
        return fluidConnections.get(Direction.get(direction)) == Connection.OUTPUT;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, @Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        if(!canExtractItem(direction)) return null;
        return null;
    }

    @Override
    public boolean canInsertItem(@Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        if(direction == null) return true;
        return fluidConnections.get(Direction.get(direction)) == Connection.INPUT;
    }

    @Override
    public ItemStack insertItem(ItemStack stack, int slot, @Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        if(!canInsertItem(direction) || stack == null) return stack;
        ItemStack invStack = getStack(slot);
        if(invStack == null) {
            ItemStack split = stack.split(Math.min(stack.count,stack.getMaxCount()));
            setStack(slot, split);
            return stack.count <= 0 ? null : stack;
        } else if(invStack.isItemEqual(stack)) {
            int remaining = Math.min(stack.count,invStack.getMaxCount() - invStack.count);
            ItemStack split = stack.split(remaining);
            invStack.count += split.count;
            return  stack.count <= 0 ? null : stack;
        }
        return stack;
    }

    @Override
    public ItemStack insertItem(ItemStack stack, @Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        if(!canInsertItem(direction) || stack == null) return stack;
        int n = stack.count;

        for (int i = 0; i < size(); i++) {
            ItemStack invStack = getStack(i);
            if(invStack == null) {
                int amount = Math.min(stack.count, stack.getMaxCount());
                n -= amount;
                setStack(i, stack.split(amount));
                if(n <= 0) break;
            } else if(invStack.isItemEqual(stack)) {
                int remaining = Math.min(n,invStack.getMaxCount() - invStack.count);
                n -= remaining;
                invStack.count += remaining;
                if(n <= 0) break;
            }
        }

        if(n <= 0){
            return null;
        }

        ItemStack copy = stack.copy();
        copy.count = n;

        return copy;
    }

    @Override
    public ItemStack getItem(int slot, @Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        return getStack(slot);
    }

    @Override
    public boolean setItem(ItemStack stack, int slot, net.modificationstation.stationapi.api.util.math.@Nullable Direction direction) {
        itemContents[slot] = stack;
        if(stack != null && stack.count > getMaxCountPerStack())
        {
            stack.count = getMaxCountPerStack();
        }
        markDirty();
        return true;
    }

    @Override
    public int getItemSlots(@Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        return itemContents.length;
    }

    @Override
    public ItemStack[] getInventory(@Nullable net.modificationstation.stationapi.api.util.math.Direction direction) {
        return itemContents;
    }

    // Inventory

    @Override
    public int size() {
        return itemContents.length;
    }

    @Override
    public ItemStack getStack(int slot) {
        return itemContents[slot];
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (this.itemContents[slot] != null) {
            if (this.itemContents[slot].count <= amount) {
                ItemStack stack = this.itemContents[slot];
                this.itemContents[slot] = null;
                return stack;
            } else {
                ItemStack stack = this.itemContents[slot].split(amount);
                if (this.itemContents[slot].count == 0) {
                    this.itemContents[slot] = null;
                }

                return stack;
            }
        } else {
            return null;
        }
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        setItem(stack,slot,null);
    }

    @Override
    public String getName() {
        return "Fluid & Item Container";
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.x, this.y, this.z) != this) {
            return false;
        } else {
            return !(player.getSquaredDistance((double)this.x + (double)0.5F, (double)this.y + (double)0.5F, (double)this.z + (double)0.5F) > (double)64.0F);
        }
    }

    // Fluid IO

    @Override
    public int getActiveFluidSlotForSide(Direction dir) {
        return activeFluidSlots.get(dir);
    }

    @Override
    public void setActiveFluidSlotForSide(Direction dir, int slot) {
        activeFluidSlots.replace(dir,slot);
    }

    @Override
    public Connection getFluidIOForSide(Direction dir) {
        return fluidConnections.get(dir);
    }

    @Override
    public void setFluidIOForSide(Direction dir, Connection con) {
        fluidConnections.put(dir,con);
    }

    @Override
    public void cycleFluidIOForSide(Direction dir) {
        switch (fluidConnections.get(dir)) {
            case NONE:
                fluidConnections.replace(dir, Connection.INPUT);
                break;
            case INPUT:
                fluidConnections.replace(dir, Connection.OUTPUT);
                break;
            case OUTPUT:
                fluidConnections.replace(dir, Connection.BOTH);
                break;
            case BOTH:
                fluidConnections.replace(dir, Connection.NONE);
                break;
        }
    }

    @Override
    public void cycleActiveFluidSlotForSide(Direction dir, boolean backwards) {
        int i = activeFluidSlots.get(dir);
        if(!backwards){
            if(i < getFluidSlots(dir.to())-1){
                activeFluidSlots.replace(dir,i+1);
            } else {
                activeFluidSlots.replace(dir,0);
            }
        } else {
            if(i > -1){
                activeFluidSlots.replace(dir,i-1);
            } else {
                activeFluidSlots.replace(dir,getFluidSlots(dir.to())-1);
            }
        }
    }

    // Item IO

    @Override
    public int getActiveItemSlotForSide(Direction dir) {
        if(activeItemSlots.get(dir) == -1){
            if(itemConnections.get(dir) == Connection.INPUT){
                for (int i = 0; i < itemContents.length; i++) {
                    ItemStack content = itemContents[i];
                    if (content == null) {
                        return i;
                    }
                }
            } else if(itemConnections.get(dir) == Connection.OUTPUT) {
                for (int i = 0; i < itemContents.length; i++) {
                    ItemStack content = itemContents[i];
                    if (content != null) {
                        return i;
                    }
                }
            }
            return 0;
        } else {
            return activeItemSlots.get(dir);
        }
    }

    @Override
    public int getActiveItemSlotForSide(Direction dir, ItemStack stack) {
        if(activeItemSlots.get(dir) == -1){
            if(itemConnections.get(dir) == Connection.INPUT){
                for (int i = 0; i < itemContents.length; i++) {
                    ItemStack content = itemContents[i];
                    if (content == null || (content.isItemEqual(stack) && content.count+stack.count <= content.getMaxCount())) {
                        return i;
                    }
                }
            } else if(itemConnections.get(dir) == Connection.OUTPUT) {
                for (int i = 0; i < itemContents.length; i++) {
                    ItemStack content = itemContents[i];
                    if (content != null) {
                        return i;
                    }
                }
            }
            return 0;
        } else {
            return activeItemSlots.get(dir);
        }
    }

    @Override
    public void setActiveItemSlotForSide(Direction dir, int slot) {
        activeItemSlots.replace(dir,slot);
    }

    @Override
    public Connection getItemIOForSide(Direction dir) {
        return itemConnections.get(dir);
    }

    @Override
    public void setItemIOForSide(Direction dir, Connection con) {
        itemConnections.put(dir,con);
    }

    @Override
    public void cycleItemIOForSide(Direction dir) {
        switch (itemConnections.get(dir)) {
            case NONE:
                itemConnections.replace(dir, Connection.INPUT);
                break;
            case INPUT:
                itemConnections.replace(dir, Connection.OUTPUT);
                break;
            case OUTPUT:
                itemConnections.replace(dir, Connection.BOTH);
                break;
            case BOTH:
                itemConnections.replace(dir, Connection.NONE);
                break;
        }
    }

    @Override
    public void cycleActiveItemSlotForSide(Direction dir, boolean backwards) {
        int i = activeItemSlots.get(dir);
        if(!backwards){
            if(i < size()-1){
                activeItemSlots.replace(dir,i+1);
            } else {
                activeItemSlots.replace(dir,0);
            }
        } else {
            if(i > -1){
                activeItemSlots.replace(dir,i-1);
            } else {
                activeItemSlots.replace(dir,size()-1);
            }
        }
    }

    // NBT
    
    @Override
    public void readNbt(NbtCompound tag)
    {
        super.readNbt(tag);

        NbtList nbtTagList = tag.getList("Fluids");
        this.fluidContents = new FluidStack[this.getFluidSlots(null)];

        for(int i3 = 0; i3 < nbtTagList.size(); ++i3) {
            NbtCompound CompoundTag4 = (NbtCompound) nbtTagList.get(i3);
            int i5 = CompoundTag4.getByte("Slot") & 255;
            if(i5 < this.fluidContents.length) {
                this.fluidContents[i5] = new FluidStack(CompoundTag4);
            }
        }

        NbtCompound fluidConnectionsTag = tag.getCompound("fluidConnections");
        for (Object con : fluidConnectionsTag.values()) {
            fluidConnections.replace(Direction.values()[Integer.parseInt(((NbtInt)con).getKey())],Connection.values()[((NbtInt)con).value]);
        }

        NbtCompound activeFluidSlotsTag = tag.getCompound("fluidActiveSlots");
        for (Object con : activeFluidSlotsTag.values()) {
            activeFluidSlots.replace(Direction.values()[Integer.parseInt(((NbtInt)con).getKey())],((NbtInt) con).value);
        }

        NbtList nbttaglist = tag.getList("Items");
        itemContents = new ItemStack[size()];
        for(int i = 0; i < nbttaglist.size(); i++)
        {
            NbtCompound nbttagcompound1 = (NbtCompound)nbttaglist.get(i);
            int j = nbttagcompound1.getByte("Slot") & 0xff;
            if(j < itemContents.length)
            {
                itemContents[j] = new ItemStack(nbttagcompound1);
            }
        }

        NbtCompound connectionsTag = tag.getCompound("itemConnections");
        for (Object con : connectionsTag.values()) {
            itemConnections.replace(Direction.values()[Integer.parseInt(((NbtInt)con).getKey())],Connection.values()[((NbtInt)con).value]);
        }

        NbtCompound activeItemSlotsTag = tag.getCompound("itemActiveSlots");
        for (Object con : activeItemSlotsTag.values()) {
            activeItemSlots.replace(Direction.values()[Integer.parseInt(((NbtInt)con).getKey())],((NbtInt) con).value);
        }
    }

    @Override
    public void writeNbt(NbtCompound tag)
    {
        super.writeNbt(tag);

        NbtList fluidListTag = new NbtList();
        NbtCompound connectionsTag = new NbtCompound();
        NbtCompound activeFluidSlotsTag = new NbtCompound();
        for(int i3 = 0; i3 < this.fluidContents.length; ++i3) {
            if(this.fluidContents[i3] != null && this.fluidContents[i3].fluid != null) {
                NbtCompound CompoundTag4 = new NbtCompound();
                CompoundTag4.putByte("Slot", (byte)i3);
                this.fluidContents[i3].writeNbt(CompoundTag4);
                fluidListTag.add(CompoundTag4);
            }
        }
        for (Map.Entry<Direction, Integer> entry : activeFluidSlots.entrySet()) {
            Direction dir = entry.getKey();
            activeFluidSlotsTag.putInt(String.valueOf(dir.ordinal()),entry.getValue());
        }
        for (Map.Entry<Direction, Connection> entry : fluidConnections.entrySet()) {
            Direction dir = entry.getKey();
            Connection con = entry.getValue();
            connectionsTag.putInt(String.valueOf(dir.ordinal()),con.ordinal());
        }
        tag.put("fluidConnections",connectionsTag);
        tag.put("fluidActiveSlots",activeFluidSlotsTag);
        tag.put("Fluids", fluidListTag);
        
        NbtList itemListTag = new NbtList();
        NbtCompound itemConnectionsTag = new NbtCompound();
        NbtCompound activeItemSlotsTag = new NbtCompound();
        for(int i = 0; i < itemContents.length; i++)
        {
            if(itemContents[i] != null)
            {
                NbtCompound nbttagcompound1 = new NbtCompound();
                nbttagcompound1.putByte("Slot", (byte)i);
                itemContents[i].writeNbt(nbttagcompound1);
                itemListTag.add(nbttagcompound1);
            }
        }

        for (Map.Entry<Direction, Integer> entry : activeItemSlots.entrySet()) {
            Direction dir = entry.getKey();
            activeItemSlotsTag.putInt(String.valueOf(dir.ordinal()),entry.getValue());
        }
        for (Map.Entry<Direction, Connection> entry : itemConnections.entrySet()) {
            Direction dir = entry.getKey();
            Connection con = entry.getValue();
            itemConnectionsTag.putInt(String.valueOf(dir.ordinal()),con.ordinal());
        }
        tag.put("itemConnections",itemConnectionsTag);
        tag.put("itemActiveSlots",activeItemSlotsTag);

        tag.put("Items", itemListTag);
    }
}
