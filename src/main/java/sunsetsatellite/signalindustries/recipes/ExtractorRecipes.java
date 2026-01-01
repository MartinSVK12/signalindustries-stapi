package sunsetsatellite.signalindustries.recipes;


import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.item.ItemStack;
import sunsetsatellite.catalyst.core.util.recipe.RecipeGroup;
import sunsetsatellite.signalindustries.data.SIFluids;
import sunsetsatellite.signalindustries.data.SIItems;
import sunsetsatellite.signalindustries.recipes.base.MachineRecipesBase;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryExtractor;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachineFluid;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.signalindustries.util.RecipeSymbol;
import sunsetsatellite.signalindustries.util.Tier;

public class ExtractorRecipes implements MachineRecipesBase<RecipeGroup<RecipeEntryMachineFluid>> {
    @Override
    public void addRecipes(RecipeGroup<RecipeEntryMachineFluid> group) {
        group.register(
                "prototype",
                new RecipeEntryExtractor(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.rawSignalumCrystal))
                        },
                        new FluidStack(SIFluids.ENERGY,160),
                        new RecipeProperties(200, Tier.PROTOTYPE,true))
        );
        group.register(
                "basic",
                new RecipeEntryExtractor(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.rawSignalumCrystal))
                        },
                        new FluidStack(SIFluids.ENERGY,320),
                        new RecipeProperties(100, Tier.BASIC,true))
        );
        group.register(
                "reinforced",
                new RecipeEntryExtractor(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.rawSignalumCrystal,4))
                        },
                        new FluidStack(SIFluids.ENERGY,640*4),
                        new RecipeProperties(200, Tier.REINFORCED,true))
        );
    }
}
