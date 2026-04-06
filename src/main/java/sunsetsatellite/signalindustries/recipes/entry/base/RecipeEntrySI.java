package sunsetsatellite.signalindustries.recipes.entry.base;

import sunsetsatellite.catalyst.core.util.recipe.RecipeEntryBase;
import sunsetsatellite.catalyst.core.util.recipe.RecipeSymbol;
import sunsetsatellite.signalindustries.block.entity.base.TieredMultiblockBlockEntity;

public abstract class RecipeEntrySI<I,O,D> extends RecipeEntryBase<I,O,D> {

    public RecipeEntrySI(I input, O output, D data) {
        super(input, output, data);
    }

    public RecipeEntrySI() {
    }

    public abstract boolean matches(RecipeSymbol[] symbols);

    public abstract void consumeMultiblockInputs(TieredMultiblockBlockEntity multiblock);

    public abstract boolean canMultiblockProcess(TieredMultiblockBlockEntity multiblock);

    public abstract void processMultiblockRecipe(TieredMultiblockBlockEntity multiblock);

    public abstract void consumeMachineInputs(TieredMultiblockBlockEntity machine);

    public abstract boolean canMachineProcess(TieredMultiblockBlockEntity machine);

    public abstract void processMachineRecipe(TieredMultiblockBlockEntity machine);


}
