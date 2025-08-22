package sunsetsatellite.signalindustries.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import sunsetsatellite.catalyst.core.util.recipe.RecipeGroup;
import sunsetsatellite.signalindustries.recipes.base.MachineRecipesBase;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryMachine;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.signalindustries.util.RecipeSymbol;
import sunsetsatellite.signalindustries.util.Tier;

public class CrusherRecipes implements MachineRecipesBase<RecipeGroup<RecipeEntryMachine>> {
    @Override
    public void addRecipes(RecipeGroup<RecipeEntryMachine> group) {
        group.register(
                "cobble_to_gravel",
                new RecipeEntryMachine(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(Block.COBBLESTONE,1))
                        },
                        new ItemStack(Block.GRAVEL,1),
                        new RecipeProperties(200,40, Tier.PROTOTYPE,false)
                )
        );
    }
}
