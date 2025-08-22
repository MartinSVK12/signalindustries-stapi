package sunsetsatellite.signalindustries.block.entity;

import net.minecraft.item.ItemStack;
import sunsetsatellite.signalindustries.block.entity.base.TieredMachineSimpleBlockEntity;
import sunsetsatellite.signalindustries.data.SIFluids;
import sunsetsatellite.signalindustries.data.SIRecipes;

import java.util.ArrayList;

public class CrusherBlockEntity extends TieredMachineSimpleBlockEntity {

    public CrusherBlockEntity() {
        itemContents = new ItemStack[2];
        fluidCapacity[0] = 2000;
        acceptedFluids.add(new ArrayList<>());
        acceptedFluids.get(0).add(SIFluids.ENERGY);
        energySlot = 0;
        recipeGroup = SIRecipes.CRUSHER;
        itemInputs = new int[]{0};
        itemOutputs = new int[]{1};
    }

    @Override
    public String getName() {
        return "container.signalindustries.crusher.name";
    }

}
