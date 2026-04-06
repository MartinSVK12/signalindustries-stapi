package sunsetsatellite.signalindustries.recipes.entry.base;

import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.item.ItemStack;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.io.InventoryWrapper;
import sunsetsatellite.signalindustries.block.entity.base.TieredMultiblockBlockEntity;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.catalyst.core.util.recipe.RecipeSymbol;

import java.util.*;
import java.util.stream.Collectors;

public abstract class RecipeEntryMachineFluid extends RecipeEntrySI<RecipeSymbol[], FluidStack, RecipeProperties> {

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

    @Override
    public void consumeMultiblockInputs(TieredMultiblockBlockEntity multiblock) {
        if (multiblock.usesItemInput) {
            List<ItemStack> recipeStacks = Catalyst.condenseItemList(
                    Arrays.stream(getInput())
                            .flatMap(symbol -> symbol.resolve().stream())
                            .filter(Objects::nonNull)
                            .map(ItemStack::copy).collect(Collectors.toList())
            );
            List<ItemStack> remainingRecipeStacks = recipeStacks.stream().map(ItemStack::copy).peek(I -> I.count *= multiblock.parallel).toList();
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
            for (int i = 0; i < multiblock.fluidInput.fluidContents.length; i++) {
                FluidStack inputStack = multiblock.fluidInput.getFluid(i, null);
                List<FluidStack> recipeStacks = Arrays.stream(getInput())
                        .flatMap(symbol -> symbol.resolveFluids().stream())
                        .filter(Objects::nonNull)
                        .map(FluidStack::copy)
                        .collect(Collectors.toList());
                List<FluidStack> remainingRecipeStacks = recipeStacks.stream().map(FluidStack::copy).toList();
                if (inputStack != null) {
                    Optional<FluidStack> recipeStack = recipeStacks.stream().filter(stack -> stack.isFluidEqual(inputStack)).findFirst();
                    Optional<FluidStack> remainingRecipeStack = remainingRecipeStacks.stream().filter(stack -> stack.isFluidEqual(inputStack)).findFirst();
                    recipeStack.ifPresent(stack -> {
                        remainingRecipeStack.ifPresent(remainingStack -> {
                            if (remainingStack.amount > 0) {
                                int willTake = Math.min(stack.amount * multiblock.parallel, inputStack.amount);
                                inputStack.amount -= willTake;
                                remainingStack.amount -= stack.amount;
                            }
                        });
                    });
                    //recipeStack.ifPresent(stack -> inputStack.amount -= stack.amount * parallel);
                    if (inputStack.amount <= 0) {
                        multiblock.fluidInput.setFluid(i, null, null);
                    }
                }
            }
        }
    }

    @Override
    public boolean canMultiblockProcess(TieredMultiblockBlockEntity multiblock) {
        return multiblock.areFluidOutputsValid(getOutput());
    }

    @Override
    public void processMultiblockRecipe(TieredMultiblockBlockEntity multiblock) {
        FluidStack fluidStack = getOutput() == null ? null : getOutput().copy();
        if (fluidStack != null) {
            multiblock.consumeInputs();
            if (multiblock.random.nextFloat() <= getData().chance) {
                int multiplier = 1;
                multiplier *= multiblock.parallel;
                int outputAmountRemaining = fluidStack.amount * multiplier;
                for (int i = 0; i < multiblock.fluidOutput.itemContents.length; i++) {
                    FluidStack outputStack = multiblock.fluidOutput.fluidContents[i];
                    if (outputStack == null) {
                        int maxAmountInSlot = multiblock.fluidOutput.getFluidCapacity(i, null);
                        if (maxAmountInSlot <= 0) continue;
                        int willTake = Math.min(outputAmountRemaining, maxAmountInSlot);
                        if (willTake <= 0) continue;
                        FluidStack copy = fluidStack.copy();
                        copy.amount = willTake;
                        multiblock.fluidOutput.setFluid(i, copy, null);
                        outputAmountRemaining -= willTake;
                        if (outputAmountRemaining <= 0) {
                            break;
                        }
                    } else if (outputStack.isFluidEqual(fluidStack)) {
                        int maxAmountInSlot = multiblock.fluidOutput.getFluidCapacity(i, null) - outputStack.amount;
                        if (maxAmountInSlot <= 0) continue;
                        int willTake = Math.min(outputAmountRemaining, maxAmountInSlot);
                        if (willTake <= 0) continue;
                        outputStack.amount += willTake;
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
        return false;
        //todo
    }

    @Override
    public void processMachineRecipe(TieredMultiblockBlockEntity machine) {
        //todo
    }

}
