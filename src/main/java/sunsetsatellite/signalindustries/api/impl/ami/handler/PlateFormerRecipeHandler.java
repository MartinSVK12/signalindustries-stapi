package sunsetsatellite.signalindustries.api.impl.ami.handler;

import org.jetbrains.annotations.NotNull;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryCrusher;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryPlateFormer;

public class PlateFormerRecipeHandler extends MachineRecipeHandler<RecipeEntryPlateFormer> {
    public PlateFormerRecipeHandler() {
        super("plate_former");
    }

    @Override
    public @NotNull Class<RecipeEntryPlateFormer> getRecipeClass() {
        return RecipeEntryPlateFormer.class;
    }
}
