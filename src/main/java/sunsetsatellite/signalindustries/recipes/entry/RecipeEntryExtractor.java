package sunsetsatellite.signalindustries.recipes.entry;

import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.item.ItemStack;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachine;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachineFluid;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.signalindustries.util.RecipeSymbol;

public class RecipeEntryExtractor extends RecipeEntryMachineFluid {
    public RecipeEntryExtractor(RecipeSymbol[] input, FluidStack output, RecipeProperties data) {
        super(input, output, data);
    }

    public RecipeEntryExtractor() {
    }
}
