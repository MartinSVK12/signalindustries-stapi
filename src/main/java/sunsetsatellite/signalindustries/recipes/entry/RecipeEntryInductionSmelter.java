package sunsetsatellite.signalindustries.recipes.entry;

import net.minecraft.item.ItemStack;
import sunsetsatellite.catalyst.core.util.recipe.RecipeSymbol;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachine;
import sunsetsatellite.signalindustries.util.RecipeProperties;

public class RecipeEntryInductionSmelter extends RecipeEntryMachine {

    public RecipeEntryInductionSmelter(RecipeSymbol[] input, ItemStack output, RecipeProperties data) {
        super(input, output, data);
    }

    public RecipeEntryInductionSmelter() {
    }
}
