package sunsetsatellite.signalindustries.block.entity;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import sunsetsatellite.signalindustries.block.entity.base.TieredMachineSimpleBlockEntity;
import sunsetsatellite.signalindustries.data.SIFluids;
import sunsetsatellite.signalindustries.data.SIRecipes;
import sunsetsatellite.signalindustries.util.Boostable;

import java.util.ArrayList;

public class PlateFormerBlockEntity extends TieredMachineSimpleBlockEntity implements Boostable {

    public PlateFormerBlockEntity() {
        itemContents = new ItemStack[2];
        fluidCapacity[0] = 2000;
        acceptedFluids.add(new ArrayList<>());
        acceptedFluids.get(0).add(SIFluids.ENERGY);
        energySlot = 0;
        recipeGroup = SIRecipes.PLATE_FORMER;
        itemInputs = new int[]{0};
        itemOutputs = new int[]{1};
    }

    @Override
    public void init(Block block) {
        super.init(block);
        fluidCapacity[0] = 2000 * (tier.ordinal()+1);
    }

    @Override
    public String getName() {
        return "container.signalindustries.plateFormer.name";
    }

}
