package sunsetsatellite.signalindustries.block.entity.base;

import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.recipes.base.RecipeGroupSI;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryMachine;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryMachineFluid;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntrySI;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.signalindustries.util.RecipeSymbol;

import java.util.*;

public abstract class TieredMachineSimpleBlockEntity extends TieredMachineBaseBlockEntity {

    public RecipeGroupSI<?> recipeGroup;
    public RecipeEntrySI<?,?, RecipeProperties> currentRecipe;
    public int recipeId = 0;
    public int[] itemInputs = new int[0];
    public int[] itemOutputs = new int[0];
    public int[] fluidInputs = new int[0];
    public int[] fluidOutputs = new int[0];
    public int energySlot;
    public Random random = new Random();

    @Override
    public void tick() {
        super.tick();
        Block block = getBlock();
        if(block != null){
            setCurrentRecipe();
            if(!disabled) work();
        }
    }

    public void work(){
        if(world.isRemote) return;
        world.setBlockDirty(x,y,z);
        boolean update = false;
        if(fuelBurnTicks > 0){
            fuelBurnTicks--;
        }
        if(areAllInputsNull()){
            progressTicks = 0;
        } else if(canProcess()) {
            progressMaxTicks = getTieredProgressDuration(currentRecipe.getData().ticks); //(int) (currentRecipe.getData().ticks / speedMultiplier);
        }
        if(!world.isRemote){
            if (progressTicks == 0 && canProcess()){
                update = fuel();
            }
            if(isActive() && canProcess()){
                progressTicks++;
                if(progressTicks >= progressMaxTicks){
                    progressTicks = 0;
                    processItem();
                    update = true;
                }
            } else if(canProcess()){
                fuel();
                if(fuelBurnTicks > 0){
                    fuelBurnTicks++;
                }
            }
        }

        if(update) {
            this.markDirty();
        }
    }

    public void processItem(){
        if(canProcess()){
            if(currentRecipe instanceof RecipeEntryMachine){
                RecipeEntryMachine recipe = ((RecipeEntryMachine) currentRecipe);
                ItemStack stack = recipe.getOutput() == null ? null : recipe.getOutput().copy();
                if (stack != null) {
                    consumeInputs();
                    if(random.nextFloat() <= recipe.getData().chance){
                        int multiplier = 1;
                        float fraction = Float.parseFloat("0."+(String.valueOf(yield).split("\\.")[1]));
                        if(fraction <= 0) fraction = 1;
                        if(yield > 1 && random.nextFloat() <= fraction){
                            multiplier = (int) Math.ceil(yield);
                        }
                        if (itemContents[itemOutputs[0]] == null) {
                            stack.count *= multiplier;
                            setStack(itemOutputs[0], stack);
                        } else if (itemContents[itemOutputs[0]].isItemEqual(stack)) {
                            itemContents[itemOutputs[0]].count += (stack.count * multiplier);
                        }
                    }
                }
            } else if (currentRecipe instanceof RecipeEntryMachineFluid) {
                RecipeEntryMachineFluid recipe = ((RecipeEntryMachineFluid) currentRecipe);
                FluidStack fluidStack = recipe.getOutput() == null ? null : recipe.getOutput().copy();
                if (fluidStack != null) {
                    consumeInputs();
                    if(random.nextFloat() <= recipe.getData().chance) {
                        int multiplier = 1;
                        float fraction = Float.parseFloat("0."+(String.valueOf(yield).split("\\.")[1]));
                        if(fraction <= 0) fraction = 1;
                        if(yield > 1 && random.nextFloat() <= fraction){
                            multiplier = (int) Math.ceil(yield);
                        }
                        if (fluidContents[fluidOutputs[0]] == null) {
                            fluidStack.amount *= multiplier;
                            setFluid(fluidOutputs[0], fluidStack, null);
                        } else if (fluidContents[fluidOutputs[0]].isFluidEqual(fluidStack)) {
                            fluidContents[fluidOutputs[0]].amount += (fluidStack.amount * multiplier);
                        }
                    }
                }
            }
        }
    }

    public void consumeInputs(){
        if(currentRecipe instanceof RecipeEntryMachine) {
            RecipeEntryMachine recipe = ((RecipeEntryMachine) currentRecipe);
            for (int itemInput : itemInputs) {
                ItemStack inputStack = getStack(itemInput);
                if(inputStack != null && inputStack.getItem().hasCraftingReturnItem() && !recipe.getData().consumeContainers){
                    setStack(itemInput, new ItemStack(inputStack.getItem().getCraftingReturnItem()));
                } else if (inputStack != null) {
                    Optional<ItemStack> recipeStack = Arrays.stream(recipe.getInput())
                            .flatMap(symbol -> symbol.resolve().stream())
                            .filter(Objects::nonNull)
                            .filter(stack -> stack.isItemEqual(inputStack))
                            .findFirst();
                    if(inputStack.getItem().hasCraftingReturnItem() && !recipe.getData().consumeContainers){
                        setStack(itemInput, new ItemStack(inputStack.getItem().getCraftingReturnItem()));
                    } else {
                        recipeStack.ifPresent(stack -> inputStack.count -= stack.count);
                        if (inputStack.count <= 0) {
                            setStack(itemInput, null);
                        }
                    }

                }
            }
            for (int fluidInput : fluidInputs) {
                FluidStack inputStack = getFluid(fluidInput, null);
                if(inputStack != null){
                    Optional<FluidStack> recipeStack = Arrays.stream(recipe.getInput())
                            .flatMap(symbol -> symbol.resolveFluids().stream())
                            .filter(Objects::nonNull)
                            .filter(stack -> stack.isFluidEqual(inputStack))
                            .findFirst();
                    recipeStack.ifPresent(stack -> inputStack.amount -= stack.amount);
                    if (inputStack.amount <= 0) {
                        setFluid(fluidInput, null, null);
                    }
                }
            }
        } else if (currentRecipe instanceof RecipeEntryMachineFluid) {
            RecipeEntryMachineFluid recipe = ((RecipeEntryMachineFluid) currentRecipe);
            for (int itemInput : itemInputs) {
                ItemStack inputStack = getStack(itemInput);
                if(inputStack != null && inputStack.getItem().hasCraftingReturnItem() && !recipe.getData().consumeContainers){
                    setStack(itemInput, new ItemStack(inputStack.getItem().getCraftingReturnItem()));
                } else if (inputStack != null) {
                    Optional<ItemStack> recipeStack = Arrays.stream(recipe.getInput())
                            .flatMap(symbol -> symbol.resolve().stream())
                            .filter(Objects::nonNull)
                            .filter(stack -> stack.isItemEqual(inputStack))
                            .findFirst();
                    if(inputStack.getItem().hasCraftingReturnItem() && !recipe.getData().consumeContainers){
                        setStack(itemInput, new ItemStack(inputStack.getItem().getCraftingReturnItem()));
                    } else {
                        recipeStack.ifPresent(stack -> inputStack.count -= stack.count);
                        if (inputStack.count <= 0) {
                            setStack(itemInput, null);
                        }
                    }
                }
            }
            for (int fluidInput : fluidInputs) {
                FluidStack inputStack = getFluid(fluidInput, null);
                if(inputStack != null){
                    Optional<FluidStack> recipeStack = Arrays.stream(recipe.getInput())
                            .flatMap(symbol -> symbol.resolveFluids().stream())
                            .filter(Objects::nonNull)
                            .filter(stack -> stack.isFluidEqual(inputStack))
                            .findFirst();
                    recipeStack.ifPresent(stack -> inputStack.amount -= stack.amount);
                    if (inputStack.amount <= 0) {
                        setFluid(fluidInput, null, null);
                    }
                }
            }
        }
    }

    public boolean areAllInputsNull(){
        boolean itemsNull = Arrays.stream(itemInputs).allMatch(slot -> itemContents[slot] == null);
        boolean fluidsNull = Arrays.stream(fluidInputs).allMatch(slot -> fluidContents[slot] == null);
        return itemsNull && fluidsNull;
    }

    public boolean canProcess(){
        if(currentRecipe instanceof RecipeEntryMachine){
            RecipeEntryMachine recipe = ((RecipeEntryMachine) currentRecipe);
            ItemStack stack = recipe.getOutput();
            if (stack == null) {
                return false;
            }
            return areItemOutputsValid(stack);
        } else if (currentRecipe instanceof RecipeEntryMachineFluid) {
            RecipeEntryMachineFluid recipe = ((RecipeEntryMachineFluid) currentRecipe);
            FluidStack fluidStack = recipe.getOutput();
            if(fluidStack == null){
                return false;
            }
            return areFluidOutputsValid(fluidStack);
        }
        return false;
    }

    public boolean areItemOutputsValid(ItemStack stack){
        for (int itemOutput : itemOutputs) {
            ItemStack outputStack = getStack(itemOutput);
            if (outputStack != null) {
                if (outputStack.isItemEqual(stack)) {
                    if(yield > 1){
                        int n = outputStack.count+(stack.count*((int) Math.ceil(yield)));
                        if ((n > getMaxCountPerStack() || n > outputStack.getMaxCount()) && n > stack.getMaxCount()) {
                            return false;
                        }
                    } else {
                        int n = outputStack.count+stack.count;
                        if ((n > getMaxCountPerStack() || n > outputStack.getMaxCount()) && n > stack.getMaxCount()) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean areFluidOutputsValid(FluidStack stack){
        for (int fluidOutput : fluidOutputs) {
            FluidStack outputStack = getFluid(fluidOutput, null);
            if (outputStack != null) {
                if (outputStack.isFluidEqual(stack)) {
                    if(yield > 1){
                        if (stack.amount*Math.ceil(yield) > getRemainingCapacity(fluidOutput)) {
                            return false;
                        }
                    } else {
                        if (stack.amount > getRemainingCapacity(fluidOutput)) {
                            return false;
                        }
                    }
                    return false;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean fuel(){
        int burn = SignalIndustries.getEnergyBurnTime(fluidContents[energySlot]);
        if(burn > 0 && canProcess() && currentRecipe != null && fuelBurnTicks <= 0){
            if(fluidContents[energySlot].amount >= currentRecipe.getData().cost){
                progressMaxTicks = getTieredProgressDuration(currentRecipe.getData().ticks);
                fuelMaxBurnTicks = fuelBurnTicks = burn;
                fluidContents[energySlot].amount -= currentRecipe.getData().cost;
                if (fluidContents[energySlot].amount == 0) {
                    fluidContents[energySlot] = null;
                }
                return true;
            }
        }
        return false;
    }

    public void setCurrentRecipe(){
        ArrayList<RecipeSymbol> symbols = new ArrayList<>();
        Arrays.stream(itemInputs).forEach((id)->{
            if (getStack(id) != null) {
                symbols.add(new RecipeSymbol(getStack(id)));
            }
        });
        Arrays.stream(fluidInputs).forEach((id)->{
            if (getFluid(id, null) != null) {
                symbols.add(new RecipeSymbol(getFluid(id, null)));
            }
        });
        currentRecipe = recipeGroup.findRecipe(symbols.toArray(new RecipeSymbol[0]),tier,recipeId);
    }


    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.putInt("RecipeId",recipeId);
        /*if(currentRecipe != null) {
            tag.putString("CurrentRecipe",currentRecipe.toString());
        }*/
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        recipeId = tag.getInt("RecipeId");
        /*if(currentRecipe == null && tag.containsKey("CurrentRecipe")) {
            try {
                currentRecipe = (RecipeEntrySI<?, ?, RecipeProperties>) Registries.RECIPES.getRecipeFromKey(tag.getString("CurrentRecipe")).recipe;
            } catch (IllegalArgumentException ignored) {}
        }*/
    }

}
