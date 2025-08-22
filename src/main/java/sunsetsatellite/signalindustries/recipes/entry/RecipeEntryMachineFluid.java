package sunsetsatellite.signalindustries.recipes.entry;

import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.item.ItemStack;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.signalindustries.util.RecipeSymbol;

import java.util.HashMap;
import java.util.List;

public class RecipeEntryMachineFluid extends RecipeEntrySI<RecipeSymbol[], FluidStack, RecipeProperties> {

    public RecipeEntryMachineFluid(RecipeSymbol[] input, FluidStack output, RecipeProperties data) {
        super(input, output, data);
    }

    public RecipeEntryMachineFluid() {}

    public boolean matches(RecipeSymbol[] symbols) {
        if(symbols.length == 0){
            return false;
        }
        //key is recipe input, value is inventory input
        HashMap<RecipeSymbol, RecipeSymbol> alreadyMatched = new HashMap<>();
        for (RecipeSymbol invInputSymbol : symbols) {
            for (RecipeSymbol recipeInputSymbol : getInput()) {
                if (recipeInputSymbol.matches(invInputSymbol) && !alreadyMatched.containsKey(recipeInputSymbol)) {
                    alreadyMatched.put(recipeInputSymbol, invInputSymbol);
                    break;
                }
            }
        }
        if(alreadyMatched.size() != getInput().length) return false;
        HashMap<List<ItemStack>,List<ItemStack>> alreadyMatchedResolved = new HashMap<>();
        alreadyMatched.forEach((recipeInputSymbol,invInputSymbol)->{
            alreadyMatchedResolved.put(recipeInputSymbol.toItemSymbol().resolve(),invInputSymbol.toItemSymbol().resolve());
        });

        return alreadyMatchedResolved.entrySet().stream()
                .allMatch((e)->e.getKey().stream()
                        .anyMatch((s)->e.getValue().stream()
                                .anyMatch((s2)->s.count <= s2.count)));
    }

}
