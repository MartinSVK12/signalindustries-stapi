package sunsetsatellite.signalindustries.recipes.base;

import sunsetsatellite.catalyst.core.util.recipe.RecipeEntryBase;
import sunsetsatellite.catalyst.core.util.recipe.RecipeGroup;

public interface MachineRecipesBase<T extends RecipeGroup<? extends RecipeEntryBase<?,?,?>>> {
    void addRecipes(T group);
}
