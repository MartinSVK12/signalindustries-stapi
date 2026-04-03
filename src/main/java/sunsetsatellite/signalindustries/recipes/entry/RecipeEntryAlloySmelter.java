package sunsetsatellite.signalindustries.recipes.entry;

import net.minecraft.item.ItemStack;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachine;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.catalyst.core.util.recipe.RecipeSymbol;

public class RecipeEntryAlloySmelter extends RecipeEntryMachine {
    public RecipeEntryAlloySmelter(RecipeSymbol[] input, ItemStack output, RecipeProperties data) {
        super(input, output, data);
    }

    public RecipeEntryAlloySmelter() {
    }
}
