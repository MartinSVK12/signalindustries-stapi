package sunsetsatellite.signalindustries.recipes.entry.base;

import net.minecraft.item.ItemStack;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.catalyst.core.util.recipe.RecipeSymbol;

import java.util.HashMap;
import java.util.List;

//fixme: SEVERE AMI L MOMENT, have to make different classes for every machine and that's why this is abstract
public abstract class RecipeEntryMachine extends RecipeEntrySI<RecipeSymbol[], ItemStack, RecipeProperties> {

    public RecipeEntryMachine(RecipeSymbol[] input, ItemStack output, RecipeProperties data) {
        super(input, output, data);
    }

    public RecipeEntryMachine() {}

    public boolean matches(RecipeSymbol[] symbols) {
        if(symbols.length == 0){
            return false;
        }
        //key is recipe input, value is inventory input
        HashMap<RecipeSymbol,RecipeSymbol> alreadyMatched = new HashMap<>();
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

        return alreadyMatchedResolved.entrySet().stream().allMatch((e)->e.getKey().stream()
                        .anyMatch((s)->e.getValue().stream()
                                .anyMatch((s2)->s.count <= s2.count)));
    }

}
