package sunsetsatellite.signalindustries.api.impl.ami.handler;

import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeHandler;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.signalindustries.api.impl.ami.wrapper.MachineFluidRecipeWrapper;
import sunsetsatellite.signalindustries.api.impl.ami.wrapper.MachineRecipeWrapper;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachine;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachineFluid;

public abstract class MachineFluidRecipeHandler<T extends RecipeEntryMachineFluid> implements RecipeHandler<T> {

    public final String type;

    public MachineFluidRecipeHandler(String type) {
        this.type = type;
    }

    @Override
    public abstract @NotNull Class<T> getRecipeClass();

    @Override
    public @NotNull String getRecipeCategoryUid() {
        return "signalindustries:recipe/"+type;
    }

    @Override
    public @NotNull RecipeWrapper getRecipeWrapper(@NotNull T recipe) {
        return new MachineFluidRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(@NotNull T recipe) {
        return true;
    }
}
