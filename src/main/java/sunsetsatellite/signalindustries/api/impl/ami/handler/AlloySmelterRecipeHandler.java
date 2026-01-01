package sunsetsatellite.signalindustries.api.impl.ami.handler;

import org.jetbrains.annotations.NotNull;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryAlloySmelter;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryCrusher;

public class AlloySmelterRecipeHandler extends MachineRecipeHandler<RecipeEntryAlloySmelter> {
    public AlloySmelterRecipeHandler() {
        super("alloy_smelter");
    }

    @Override
    public @NotNull Class<RecipeEntryAlloySmelter> getRecipeClass() {
        return RecipeEntryAlloySmelter.class;
    }
}
