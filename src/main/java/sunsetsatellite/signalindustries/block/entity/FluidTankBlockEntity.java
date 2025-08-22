package sunsetsatellite.signalindustries.block.entity;

import net.danygames2014.nyalib.fluid.FluidRegistry;
import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import sunsetsatellite.catalyst.core.util.Connection;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.signalindustries.block.entity.base.TieredBlockEntity;
import sunsetsatellite.signalindustries.data.SIFluids;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.ArrayList;

public class FluidTankBlockEntity extends TieredBlockEntity {

    public FluidTankBlockEntity() {
        fluidContents = new FluidStack[1];
        fluidCapacity = new int[1];
        itemContents = new ItemStack[0];
        fluidCapacity[0] = 8000;
        fluidConnections.replace(Direction.Y_POS, Connection.INPUT);
        fluidConnections.replace(Direction.Y_NEG, Connection.OUTPUT);
        acceptedFluids.clear();
        acceptedFluids.add(new ArrayList<>());
        FluidRegistry.getRegistry().forEach((K,V)->{
            if(V != SIFluids.ENERGY){
                acceptedFluids.get(0).add(V);
            }
        });
    }

    @Override
    public void init(Block block) {
        super.init(block);
        if(tier == Tier.PROTOTYPE){
            fluidCapacity[0] = 8000;
        } else {
            fluidCapacity[0] = (int) Math.pow(2, tier.ordinal()) * 16000;
        }
    }
}
