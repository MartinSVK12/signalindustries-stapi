package sunsetsatellite.signalindustries.api.impl.ami.handler;

import org.jetbrains.annotations.NotNull;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryAlloySmelter;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryCrystalCutter;

public class CrystalCutterRecipeHandler extends MachineRecipeHandler<RecipeEntryCrystalCutter> {
    public CrystalCutterRecipeHandler() {
        super("crystal_cutter");
    }

    @Override
    public @NotNull Class<RecipeEntryCrystalCutter> getRecipeClass() {
        return RecipeEntryCrystalCutter.class;
    }
}
