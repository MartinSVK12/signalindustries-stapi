package sunsetsatellite.signalindustries.recipes.entry.base;

import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.item.ItemStack;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.io.FluidInventoryWrapper;
import sunsetsatellite.catalyst.core.util.io.InventoryWrapper;
import sunsetsatellite.signalindustries.block.entity.base.TieredMultiblockBlockEntity;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.catalyst.core.util.recipe.RecipeSymbol;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    public void consumeMultiblockInputs(TieredMultiblockBlockEntity multiblock) {
        if (multiblock.usesItemInput) {
            List<ItemStack> recipeStacks = Catalyst.condenseItemList(
                    Arrays.stream(getInput())
                            .flatMap(symbol -> symbol.resolve().stream())
                            .filter(Objects::nonNull)
                            .map(ItemStack::copy).collect(Collectors.toList())
            );
            List<ItemStack> remainingRecipeStacks = recipeStacks.stream().map(ItemStack::copy).peek(I -> I.count *= multiblock.parallel).collect(Collectors.toList());
            InventoryWrapper wrapper = new InventoryWrapper(multiblock.itemInput);
            for (ItemStack remainingRecipeStack : remainingRecipeStacks) {
                ItemStack stack = wrapper.removeUntil(remainingRecipeStack.itemId, remainingRecipeStack.getDamage(), remainingRecipeStack.count, remainingRecipeStack.getStationNbt(), false, false);
                if (stack.equals(remainingRecipeStack)) {
                    if (stack.getItem().hasCraftingReturnItem() && !getData().consumeContainers) {
                        wrapper.add(new ItemStack(stack.getItem().getCraftingReturnItem()));
                    }
                }
            }
        }
        if (multiblock.usesFluidInput) {
            List<FluidStack> recipeStacks = Arrays.stream(getInput())
                    .flatMap(symbol -> symbol.resolveFluids().stream())
                    .filter(Objects::nonNull)
                    .map(FluidStack::copy)
                    .collect(Collectors.toList());
            List<FluidStack> remainingRecipeStacks = recipeStacks.stream().map(FluidStack::copy).peek(F -> F.amount *= multiblock.parallel).collect(Collectors.toList());
            FluidInventoryWrapper wrapper = new FluidInventoryWrapper(multiblock.fluidInput);
            for (FluidStack remainingRecipeStack : remainingRecipeStacks) {
                wrapper.removeUntil(remainingRecipeStack.fluid.getFlowingBlock().id, remainingRecipeStack.amount, false);
            }
        }
    }

    @Override
    public boolean canMultiblockProcess(TieredMultiblockBlockEntity multiblock) {
        return multiblock.areItemOutputsValid(getOutput());
    }

    @Override
    public void processMultiblockRecipe(TieredMultiblockBlockEntity multiblock) {
        ItemStack stack = getOutput() == null ? null : getOutput().copy();
        if (stack != null) {
            multiblock.consumeInputs();
            if (multiblock.random.nextFloat() <= getData().chance) {
                int multiplier = 1;
                multiplier *= multiblock.parallel;
                int outputAmountRemaining = stack.count * multiplier;
                for (int i = 0; i < multiblock.itemOutput.itemContents.length; i++) {
                    ItemStack outputStack = multiblock.itemOutput.itemContents[i];
                    if (outputStack == null) {
                        int maxAmountInSlot = stack.getMaxCount();
                        if (maxAmountInSlot <= 0) continue;
                        int willTake = Math.min(outputAmountRemaining, maxAmountInSlot);
                        if (willTake <= 0) continue;
                        ItemStack copy = stack.copy();
                        copy.count = willTake;
                        multiblock.itemOutput.setItem(copy, i, null);
                        outputAmountRemaining -= willTake;
                        if (outputAmountRemaining <= 0) {
                            break;
                        }
                    } else if (outputStack.isItemEqual(stack)) {
                        int maxAmountInSlot = stack.getMaxCount() - outputStack.count;
                        if (maxAmountInSlot <= 0) continue;
                        int willTake = Math.min(outputAmountRemaining, maxAmountInSlot);
                        if (willTake <= 0) continue;
                        outputStack.count += willTake;
                        outputAmountRemaining -= willTake;
                        if (outputAmountRemaining <= 0) {
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void consumeMachineInputs(TieredMultiblockBlockEntity machine) {
        //todo
    }

    @Override
    public boolean canMachineProcess(TieredMultiblockBlockEntity machine) {
        //todo
        return false;
    }

    @Override
    public void processMachineRecipe(TieredMultiblockBlockEntity machine) {
        //todo
    }

}
