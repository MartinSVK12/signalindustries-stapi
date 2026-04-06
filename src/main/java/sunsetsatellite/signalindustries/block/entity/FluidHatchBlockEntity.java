package sunsetsatellite.signalindustries.block.entity;

import net.danygames2014.nyalib.fluid.Fluid;
import net.danygames2014.nyalib.fluid.FluidRegistry;
import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import sunsetsatellite.signalindustries.block.entity.base.TieredBlockEntity;
import sunsetsatellite.signalindustries.interfaces.MultiblockPartTile;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.ArrayList;

public class FluidHatchBlockEntity extends TieredBlockEntity implements MultiblockPartTile {

    public BlockEntity connectedTo;

    public FluidHatchBlockEntity() {
        itemContents = new ItemStack[0];
        fluidContents = new FluidStack[1];
        fluidCapacity = new int[1];
        fluidCapacity[0] = 8000;
        acceptedFluids.clear();
        acceptedFluids.add(new ArrayList<>());
        acceptedFluids.get(0).addAll(FluidRegistry.getRegistry().values());
    }

    @Override
    public void init(Block block) {
        super.init(block);
        if (tier == Tier.PROTOTYPE) {
            fluidCapacity[0] = 8000;
        } else {
            fluidCapacity[0] = (int) Math.pow(2, tier.ordinal()) * 16000;
        }
    }

    @Override
    public String getName() {
        return "container.signalindustries.fluidHatch.name";
    }

    @Override
    public boolean isConnected() {
        return connectedTo != null;
    }

    @Override
    public BlockEntity getConnectedTileEntity() {
        return connectedTo;
    }

    @Override
    public boolean connect(BlockEntity tileEntity) {
        connectedTo = tileEntity;
        return true;
    }
}
