package sunsetsatellite.signalindustries.block.entity;

import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.recipe.FuelRegistry;
import sunsetsatellite.signalindustries.block.entity.base.TieredMachineBaseBlockEntity;
import sunsetsatellite.signalindustries.block.entity.base.TieredMachineSimpleFuelBlockEntity;
import sunsetsatellite.signalindustries.data.SIFluids;
import sunsetsatellite.signalindustries.data.SIRecipes;
import sunsetsatellite.signalindustries.util.Boostable;

import java.util.ArrayList;

public class ExtractorBlockEntity extends TieredMachineSimpleFuelBlockEntity implements Boostable {

    public ExtractorBlockEntity() {
        itemContents = new ItemStack[2];
        fluidCapacity[0] = 2000;
        acceptedFluids.add(new ArrayList<>());
        acceptedFluids.get(0).add(SIFluids.ENERGY);
        energySlot = 1;
        recipeGroup = SIRecipes.EXTRACTOR;
        itemInputs = new int[]{0,1};
        fluidOutputs = new int[]{0};
    }

    @Override
    public String getName() {
        return "container.signalindustries.extractor.name";
    }
}
