package sunsetsatellite.signalindustries.block.entity;

import net.danygames2014.nyalib.fluid.FluidStack;
import net.danygames2014.nyalib.fluid.Fluids;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import sunsetsatellite.signalindustries.block.entity.base.TieredMachineSimpleBlockEntity;
import sunsetsatellite.signalindustries.data.SIFluids;
import sunsetsatellite.signalindustries.data.SIItems;
import sunsetsatellite.signalindustries.data.SIRecipes;
import sunsetsatellite.signalindustries.util.Boostable;

import java.util.ArrayList;

public class CrystalCutterBlockEntity extends TieredMachineSimpleBlockEntity implements Boostable {

    public CrystalCutterBlockEntity() {
        itemContents = new ItemStack[2];
        fluidContents = new FluidStack[2];
        fluidCapacity = new int[2];
        fluidCapacity[0] = 2000;
        fluidCapacity[1] = 1000;
        for (FluidStack ignored : fluidContents) {
            acceptedFluids.add(new ArrayList<>());
        }
        acceptedFluids.get(0).add(SIFluids.ENERGY);
        acceptedFluids.get(1).add(Fluids.WATER);
        energySlot = 0;
        recipeGroup = SIRecipes.CRYSTAL_CUTTER;
        itemInputs = new int[]{0};
        itemOutputs = new int[]{1};
        fluidInputs = new int[]{1};
    }

    @Override
    public void init(Block block) {
        super.init(block);
        fluidCapacity[1] = (int) (1000 * (Math.pow(2,tier.ordinal())));
    }

    @Override
    public void processItem() {
        super.processItem();
        if(itemContents[itemOutputs[0]].getItem().equals(SIItems.signalumCrystalEmpty)){
            if(fluidContents[energySlot] != null && fluidContents[energySlot].amount+1000 <= fluidCapacity[energySlot]){
                fluidContents[energySlot].amount += 1000;
            } else if(fluidContents[energySlot] == null){
                fluidContents[energySlot] = new FluidStack(SIFluids.ENERGY,1000);
            }
        }
    }

    @Override
    public String getName() {
        return "container.signalindustries.crystalCutter.name";
    }

}
