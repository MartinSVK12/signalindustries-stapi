package sunsetsatellite.signalindustries.recipes.base;

import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.item.ItemStack;
import sunsetsatellite.catalyst.core.util.recipe.RecipeGroup;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntrySI;
import sunsetsatellite.signalindustries.util.RecipeSymbol;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.List;

public class RecipeGroupSI<T extends RecipeEntrySI<?,?, RecipeProperties>> extends RecipeGroup<T> {
    public RecipeGroupSI(List<ItemStack> machines) {
        super(machines);
    }

    public FluidStack findFluidOutput(ItemStack stack){
        for (T recipe : this.getAllRecipes()) {
            RecipeSymbol symbol = new RecipeSymbol(new ItemStack(stack.itemId, 1, stack.getDamage()));
            if (recipe.matches(new RecipeSymbol[]{symbol})) {
                return ((FluidStack)recipe.getOutput()).copy();
            }
        }
        return null;
    }
    public FluidStack findFluidOutput(ItemStack stack, Tier tier){
        for (T recipe : this.getAllRecipes()) {
            RecipeSymbol symbol = new RecipeSymbol(new ItemStack(stack.itemId, 1, stack.getDamage()));
            if (recipe.matches(new RecipeSymbol[]{symbol}) && recipe.getData().isCorrectTier(tier)) {
                return ((FluidStack)recipe.getOutput()).copy();
            }
        }
        return null;
    }

    public FluidStack findFluidOutput(FluidStack stack, Tier tier){
        for (T recipe : this.getAllRecipes()) {
            RecipeSymbol symbol = new RecipeSymbol(stack.copy());
            if (recipe.matches(new RecipeSymbol[]{symbol}) && recipe.getData().isCorrectTier(tier)) {
                return ((FluidStack)recipe.getOutput()).copy();
            }
        }
        return null;
    }

    public FluidStack findFluidOutput(RecipeSymbol[] symbols, Tier tier){
        for (T recipe : this.getAllRecipes()) {
            if(recipe.matches(symbols) && recipe.getData().isCorrectTier(tier)){
                return ((FluidStack)recipe.getOutput()).copy();
            }
        }
        return null;
    }

    public ItemStack findOutput(ItemStack stack){
        for (T recipe : this.getAllRecipes()) {
            RecipeSymbol symbol = new RecipeSymbol(new ItemStack(stack.itemId, 1, stack.getDamage()));
            if (recipe.matches(new RecipeSymbol[]{symbol})) {
                return ((ItemStack)recipe.getOutput()).copy();
            }
        }
        return null;
    }
    public ItemStack findOutput(ItemStack stack, Tier tier){
        for (T recipe : this.getAllRecipes()) {
            RecipeSymbol symbol = new RecipeSymbol(new ItemStack(stack.itemId, 1, stack.getDamage()));
            if (recipe.matches(new RecipeSymbol[]{symbol}) && recipe.getData().isCorrectTier(tier)) {
                return ((ItemStack)recipe.getOutput()).copy();
            }
        }
        return null;
    }

    public ItemStack findOutput(RecipeSymbol[] symbols){
        for (T recipe : this.getAllRecipes()) {
            if(recipe.matches(symbols)){
                return ((ItemStack)recipe.getOutput()).copy();
            }
        }
        return null;
    }

    public ItemStack findOutput(RecipeSymbol[] symbols, Tier tier){
        for (T recipe : this.getAllRecipes()) {
            if(recipe.matches(symbols) && recipe.getData().isCorrectTier(tier)){
                return ((ItemStack)recipe.getOutput()).copy();
            }
        }
        return null;
    }

    public ItemStack findOutput(RecipeSymbol[] symbols, Tier tier, int id){
        for (T recipe : this.getAllRecipes()) {
            if(recipe.matches(symbols) && recipe.getData().isCorrectTier(tier) && recipe.getData().id == id){
                return ((ItemStack)recipe.getOutput()).copy();
            }
        }
        return null;
    }

    public T findRecipe(RecipeSymbol[] symbols, Tier tier, int id){
        for (T recipe : this.getAllRecipes()) {
            if (recipe.matches(symbols) && recipe.getData().isCorrectTier(tier) && recipe.getData().id == id) {
                return recipe;
            }
        }
        return null;
    }

    public T findRecipe(RecipeSymbol[] symbols, Tier tier){
        for (T recipe : this.getAllRecipes()) {
            if (recipe.matches(symbols) && recipe.getData().isCorrectTier(tier)) {
                return recipe;
            }
        }
        return null;
    }

    public ItemStack findOutput(RecipeSymbol[] symbols, int id){
        for (T recipe : this.getAllRecipes()) {
            if(recipe.matches(symbols) && recipe.getData().id == id){
                return ((ItemStack)recipe.getOutput()).copy();
            }
        }
        return null;
    }
}
