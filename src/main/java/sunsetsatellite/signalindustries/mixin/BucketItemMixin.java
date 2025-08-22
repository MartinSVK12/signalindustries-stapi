package sunsetsatellite.signalindustries.mixin;

import net.danygames2014.nyalib.fluid.*;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin extends Item implements FluidHandlerItem {
    @Shadow
    private int fluidBlockId;

    private BucketItemMixin(int id) {
        super(id);
    }

    @Override
    public boolean canExtractFluid(ItemStack thiz) {
        return fluidBlockId != 0;
    }

    @Override
    public FluidStack extractFluid(ItemStack thiz, int slot, int amount) {
        FluidStack fluidStack = getFluid(thiz, 0);
        thiz.itemId = Item.BUCKET.id;
        return fluidStack;
    }

    @Override
    public boolean canInsertFluid(ItemStack thiz) {
        return fluidBlockId == 0;
    }

    @Override
    public FluidStack insertFluid(ItemStack thiz, FluidStack stack, int slot) {
        return insertFluid(thiz, stack);
    }

    @Override
    public FluidStack insertFluid(ItemStack thiz, FluidStack stack) {
        if(stack == null) return null;
        if(stack.amount < getBucketSize()) return stack;
        FluidStack fluidStack = getFluid(thiz, 0);
        if(fluidStack != null && !fluidStack.isFluidEqual(stack)) return stack;

        if(stack.fluid == Fluids.LAVA) {
            stack.amount -= getBucketSize();
            thiz.itemId = Item.LAVA_BUCKET.id;
            return stack.copy();
        } else if(stack.fluid == Fluids.WATER) {
            stack.amount -= getBucketSize();
            thiz.itemId = Item.WATER_BUCKET.id;
            return stack.copy();
        }

        return stack;
    }

    @Override
    public FluidStack getFluid(ItemStack thiz, int slot) {
        return getFluidFromId(fluidBlockId) == null ? null : new FluidStack(getFluidFromId(fluidBlockId), getBucketSize());
    }

    @Override
    public boolean setFluid(ItemStack thiz, int slot, FluidStack stack) {
        if(stack.amount != getBucketSize()) return false;

        if(stack.fluid == Fluids.LAVA) {
            thiz.itemId = Item.LAVA_BUCKET.id;
            return true;
        } else if(stack.fluid == Fluids.WATER) {
            thiz.itemId = Item.WATER_BUCKET.id;
            return true;
        }

        return false;
    }


    @Override
    public int getFluidSlots(ItemStack thiz) {
        return 1;
    }

    @Override
    public int getFluidCapacity(ItemStack thiz, int slot) {
        return getFluidFromId(fluidBlockId) == null ? 1000 : getBucketSize();
    }

    @Override
    public FluidStack[] getFluids(ItemStack thiz) {
        if(fluidBlockId != 0){
            return new FluidStack[]{new FluidStack(getFluidFromId(fluidBlockId), getBucketSize())};
        }
        return new FluidStack[]{null};
    }

    @Unique
    private Fluid getFluidFromId(int id){
        for (Map.Entry<Identifier, Fluid> entry : FluidRegistry.getRegistry().entrySet()) {
            Fluid fluid = entry.getValue();
            if (fluid.getBucketFluid().id == id) {
                return fluid;
            }
        }
        return null;
    }

    @Unique
    private int getBucketSize() {
        return getFluidFromId(fluidBlockId) == null ? 1000 : getFluidFromId(fluidBlockId).getBucketSize();
    }
}
