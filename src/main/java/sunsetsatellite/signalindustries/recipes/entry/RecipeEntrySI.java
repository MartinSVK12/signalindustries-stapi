package sunsetsatellite.signalindustries.recipes.entry;

import sunsetsatellite.catalyst.core.util.recipe.RecipeEntryBase;
import sunsetsatellite.signalindustries.util.RecipeSymbol;

public abstract class RecipeEntrySI<I,O,D> extends RecipeEntryBase<I,O,D> {

    public RecipeEntrySI(I input, O output, D data) {
        super(input, output, data);
    }

    public RecipeEntrySI() {
    }

    public abstract boolean matches(RecipeSymbol[] symbols);

}
