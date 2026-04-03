package sunsetsatellite.signalindustries.recipes.entry;

import net.danygames2014.nyalib.fluid.FluidStack;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachineFluid;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.catalyst.core.util.recipe.RecipeSymbol;

public class RecipeEntryExtractor extends RecipeEntryMachineFluid {
    public RecipeEntryExtractor(RecipeSymbol[] input, FluidStack output, RecipeProperties data) {
        super(input, output, data);
    }

    public RecipeEntryExtractor() {
    }
}
