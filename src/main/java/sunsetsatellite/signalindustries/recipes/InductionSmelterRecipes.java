package sunsetsatellite.signalindustries.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.SmeltingRecipeManager;
import net.modificationstation.stationapi.api.recipe.SmeltingRegistry;
import net.modificationstation.stationapi.api.registry.Registries;
import sunsetsatellite.catalyst.core.util.recipe.RecipeGroup;
import sunsetsatellite.catalyst.core.util.recipe.RecipeSymbol;
import sunsetsatellite.signalindustries.recipes.base.MachineRecipesBase;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryInductionSmelter;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachine;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.signalindustries.util.Tier;

// I really did not want to remake how multiblocks work just for this
public class InductionSmelterRecipes implements MachineRecipesBase<RecipeGroup<RecipeEntryMachine>> {

    @Override
    public void addRecipes(RecipeGroup<RecipeEntryMachine> group) {
        final int[] i = {0};
        //noinspection unchecked
        SmeltingRecipeManager.getInstance().getRecipes().forEach((k,v)->{
            ItemStack input;
            if(k instanceof Integer){
                input = new ItemStack(((int) k), 1, 0);
            } else {
                input = (ItemStack) k;
            }
            ItemStack output = (ItemStack) v;
            group.register(
                "smelting_"+i[0],
                    new RecipeEntryInductionSmelter(
                            new RecipeSymbol[]{
                                    new RecipeSymbol(input)
                            },
                            output,
                            new RecipeProperties(100, 40, Tier.BASIC, false)
                    )
            );
            i[0]++;
        });
    }
}
