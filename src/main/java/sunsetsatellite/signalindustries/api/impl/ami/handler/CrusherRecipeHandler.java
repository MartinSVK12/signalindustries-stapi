package sunsetsatellite.signalindustries.api.impl.ami.handler;

import org.jetbrains.annotations.NotNull;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryCrusher;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachine;

public class CrusherRecipeHandler extends MachineRecipeHandler<RecipeEntryCrusher> {
    public CrusherRecipeHandler() {
        super("crusher");
    }

    @Override
    public @NotNull Class<RecipeEntryCrusher> getRecipeClass() {
        return RecipeEntryCrusher.class;
    }
}
