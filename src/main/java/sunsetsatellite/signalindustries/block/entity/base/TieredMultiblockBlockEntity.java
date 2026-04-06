package sunsetsatellite.signalindustries.block.entity.base;

import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.BlockInstance;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.recipe.RecipeSymbol;
import sunsetsatellite.catalyst.core.util.vector.Vec3i;
import sunsetsatellite.catalyst.multiblocks.IMultiblock;
import sunsetsatellite.catalyst.multiblocks.MultiblockInstance;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.block.entity.EnergyConnectorBlockEntity;
import sunsetsatellite.signalindustries.block.entity.FluidHatchBlockEntity;
import sunsetsatellite.signalindustries.block.entity.ItemBusBlockEntity;
import sunsetsatellite.signalindustries.data.SIBlocks;
import sunsetsatellite.signalindustries.interfaces.MultiblockPartBlock;
import sunsetsatellite.signalindustries.interfaces.MultiblockPartTile;
import sunsetsatellite.signalindustries.interfaces.Tiered;
import sunsetsatellite.signalindustries.recipes.base.RecipeGroupSI;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntrySI;
import sunsetsatellite.signalindustries.util.MultiblockPart;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.*;
import java.util.stream.Collectors;

import static net.modificationstation.stationapi.api.state.property.Properties.HORIZONTAL_FACING;

public abstract class TieredMultiblockBlockEntity extends TieredMachineBaseBlockEntity implements IMultiblock {
    public MultiblockInstance multiblock;
    public ItemBusBlockEntity itemInput;
    public ItemBusBlockEntity itemOutput;
    public FluidHatchBlockEntity fluidInput;
    public FluidHatchBlockEntity fluidOutput;
    public EnergyConnectorBlockEntity energy;

    public boolean usesItemInput = false;
    public boolean usesItemOutput = false;
    public boolean usesFluidInput = false;
    public boolean usesFluidOutput = false;
    public boolean usesEnergy = false;

    public Tier minimumItemInputTier = Tier.BASIC;
    public Tier minimumItemOutputTier = Tier.BASIC;
    public Tier minimumFluidInputTier = Tier.BASIC;
    public Tier minimumFluidOutputTier = Tier.BASIC;
    public Tier minimumEnergyTier = Tier.BASIC;

    public RecipeGroupSI<?> recipeGroup;
    public RecipeEntrySI<?, ?, RecipeProperties> currentRecipe;

    public Random random = new Random();

    public int parallel = 1;
    public int baseParallel = 1;

    public TieredMultiblockBlockEntity() {
        super();
        itemContents = new ItemStack[0];
        fluidContents = new FluidStack[0];
        fluidCapacity = new int[0];
        acceptedFluids.clear();
    }

    public void work() {
        if (multiblock.isValid() && allPartsPresent()) {
            boolean update = false;
            if (fuelBurnTicks > 0) {
                fuelBurnTicks--;
            }
            ArrayList<ItemStack> inputContents = getItemInputContents();
            ArrayList<FluidStack> fluidInputContents = getFluidInputContents();
            if (inputContents.isEmpty() && fluidInputContents.isEmpty()) {
                progressTicks = 0;
            } else if (canProcess()) {
                progressMaxTicks = (int) (currentRecipe.getData().ticks / speedMultiplier);
            }
            if (world != null && !world.isRemote) {
                if (progressTicks == 0 && canProcess() && fuelBurnTicks < 2) {
                    update = fuel();
                }
                if (isActive() && canProcess()) {
                    progressTicks++;
                    if (progressTicks >= progressMaxTicks) {
                        progressTicks = 0;
                        processItem();
                        update = true;
                    }
                } else if (canProcess()) {
                    fuel();
                    if (fuelBurnTicks > 0) {
                        fuelBurnTicks++;
                    }
                }
            }

            if (update) {
                this.markDirty();
            }
        }
    }

    @Override
    public void tick() {
        if (multiblock == null) {
            return;
        }
        super.tick();
        if (world != null) {
            world.setBlocksDirty(x, y, z, x, y, z);
        }
        Block block = getBlock();
        itemInput = null;
        itemOutput = null;
        fluidInput = null;
        fluidOutput = null;
        energy = null;
        if (multiblock.isValid()) {
            Direction dir = Direction.getDirectionFromSide(world.getBlockState(x,y,z).get(HORIZONTAL_FACING).getId());
            ArrayList<BlockInstance> tileEntities = multiblock.data.getTileEntities(world, new Vec3i(x, y, z), dir);
            for (BlockInstance tileEntity : tileEntities) {
                if (tileEntity.tile instanceof MultiblockPartTile partTile && tileEntity.block instanceof MultiblockPartBlock partBlock && partBlock instanceof Tiered tiered) {
                    partBlock = (MultiblockPartBlock) tileEntity.pos.getBlockEntity(world).getBlock();
                    partTile = (MultiblockPartTile) tileEntity.pos.getBlockEntity(world);
                    tiered = ((Tiered) partBlock);
                    if (partBlock.getType() == MultiblockPart.Type.ITEM && partBlock.getIO() == MultiblockPart.IO.INPUT) {
                        if (tiered.getTier().ordinal() >= minimumItemInputTier.ordinal()) {
                            itemInput = (ItemBusBlockEntity) tileEntity.tile;
                        }
                    } else if (partBlock.getType() == MultiblockPart.Type.ITEM && partBlock.getIO() == MultiblockPart.IO.OUTPUT) {
                        if (tiered.getTier().ordinal() >= minimumItemOutputTier.ordinal()) {
                            itemOutput = (ItemBusBlockEntity) tileEntity.tile;
                        }
                    } else if (tileEntity.tile instanceof EnergyConnectorBlockEntity) {
                        if (tiered.getTier().ordinal() >= minimumEnergyTier.ordinal()) {
                            energy = (EnergyConnectorBlockEntity) tileEntity.tile;
                        }
                    } else if (partBlock.getType() == MultiblockPart.Type.FLUID && partBlock.getIO() == MultiblockPart.IO.INPUT) {
                        if (tiered.getTier().ordinal() >= minimumFluidInputTier.ordinal()) {
                            fluidInput = (FluidHatchBlockEntity) tileEntity.tile;
                        }
                    } else if (partBlock.getType() == MultiblockPart.Type.FLUID && partBlock.getIO() == MultiblockPart.IO.INPUT) {
                        if (tiered.getTier().ordinal() >= minimumFluidOutputTier.ordinal()) {
                            fluidOutput = (FluidHatchBlockEntity) tileEntity.tile;
                        }
                    }
                    partTile.connect(this);
                }
            }
            parallel = baseParallel;
            ArrayList<BlockInstance> extraBlocks = multiblock.data.getSubstitutions(new Vec3i(x, y, z), dir);
            /*for (BlockInstance extraBlock : extraBlocks) {
                if (world != null && extraBlock.exists(world)) {
                    BlockLogicParallelProcessor multiblockPart = Catalyst.blockLogic(extraBlock.block, BlockLogicParallelProcessor.class);
                    if (multiblockPart != null && multiblockPart.getType() == MultiblockPart.Type.PARALLEL) {
                        parallel = baseParallel * multiblockPart.maxParallel;
                    }
                }
            }*/
            if (block != null && allPartsPresent()) {
                int oldParallel = parallel;
                parallel = 1;
                setCurrentRecipe();
                parallel = oldParallel;
                if (currentRecipe != null) {
                    int recipeInputSum = Arrays.stream(((RecipeSymbol[]) currentRecipe.getInput())).map(RecipeSymbol::toItemSymbol).map(RecipeSymbol::resolve).map(L -> L.get(0)).mapToInt(S -> S.count).sum();
                    int inputSum = 0;
                    if (itemInput != null) {
                        inputSum += Catalyst.condenseItemList(Arrays.asList(itemInput.itemContents)).stream().mapToInt(S -> S.count).sum();
                    }
                    if (fluidInput != null) {
                        inputSum += Catalyst.condenseFluidList(Arrays.asList(fluidInput.fluidContents)).stream().mapToInt(S -> S.amount).sum();
                    }
                    int effectiveParallel = inputSum / recipeInputSum;

                    if (parallel > effectiveParallel && effectiveParallel > 0) {
                        parallel = effectiveParallel;
                    }
                }
                setCurrentRecipe();
                work();
            }

            if (isActive()) {
                ArrayList<BlockInstance> blocks = multiblock.data.getBlocks(new Vec3i(x, y, z), dir);
                for (BlockInstance structBlock : blocks) {
                    if (structBlock.block == SIBlocks.reinforcedCasing2 || structBlock.block == SIBlocks.awakenedSocketCasing || structBlock.block == SIBlocks.awakenedCasing2) {
                        if (world != null && structBlock.pos.getBlockMetadata(world) != 1) {
                            world.setBlockMeta(structBlock.pos.x, structBlock.pos.y, structBlock.pos.z, 1);
                        }
                    }
                }
            } else {
                ArrayList<BlockInstance> blocks = multiblock.data.getBlocks(new Vec3i(x, y, z), dir);
                for (BlockInstance structBlock : blocks) {
                    if (structBlock.block == SIBlocks.reinforcedCasing2 || structBlock.block == SIBlocks.awakenedSocketCasing || structBlock.block == SIBlocks.awakenedCasing2) {
                        if (world != null && structBlock.pos.getBlockMetadata(world) == 1) {
                            world.setBlockMeta(structBlock.pos.x, structBlock.pos.y, structBlock.pos.z, 0);
                        }
                    }
                }
            }
        }
    }


    @Override
    public MultiblockInstance getMultiblock() {
        return multiblock;
    }

    public boolean allPartsPresent() {
        return (itemInput != null || !usesItemInput)
                && (itemOutput != null || !usesItemOutput)
                && (fluidInput != null || !usesFluidInput)
                && (fluidOutput != null || !usesFluidOutput)
                && (energy != null || !usesEnergy);
    }

    public boolean canProcess() {
        if (allPartsPresent() && currentRecipe != null) {
            return currentRecipe.canMultiblockProcess(this);
        }
        return false;
    }

    public void processItem() {
        if (canProcess()) {
            currentRecipe.processMultiblockRecipe(this);
        }
    }

    public void consumeInputs() {
        if (currentRecipe != null) {
            currentRecipe.consumeMultiblockInputs(this);
        }
    }


    @Override
    public boolean isActive() {
        if (multiblock == null) return false;
        return super.isActive() && multiblock.isValid();
    }

    @NotNull
    private ArrayList<ItemStack> getItemInputContents() {
        if (!allPartsPresent() || !usesItemInput) {
            return new ArrayList<>();
        }
        return Catalyst.condenseItemList(Arrays.asList(itemInput.itemContents));
    }

    @NotNull
    private ArrayList<ItemStack> getItemOutputContents() {
        if (!allPartsPresent() || !usesItemOutput) {
            return new ArrayList<>();
        }
        return Catalyst.condenseItemList(Arrays.asList(itemOutput.itemContents));
    }

    @NotNull
    private ArrayList<FluidStack> getFluidInputContents() {
        if (!allPartsPresent() || !usesFluidInput) {
            return new ArrayList<>();
        }
        return Catalyst.condenseFluidList(Arrays.asList(fluidInput.fluidContents));
    }

    @NotNull
    private ArrayList<FluidStack> getFluidOutputContents() {
        if (!allPartsPresent() || !usesFluidOutput) {
            return new ArrayList<>();
        }
        return Catalyst.condenseFluidList(Arrays.asList(fluidOutput.fluidContents));
    }

    public boolean areItemOutputsValid(ItemStack stack) {
        if (!usesItemOutput) return true;
        int outputAmountRemaining;
        outputAmountRemaining = stack.count;

        outputAmountRemaining *= parallel;
        stack = stack.copy();
        stack.count *= parallel;

        if (outputAmountRemaining <= 0) return true;
        for (ItemStack outputStack : itemOutput.itemContents) {
            if (outputStack != null) {
                if (outputStack.isItemEqual(stack)) {
                    int maxFreeAmountInSlot = Math.min(outputStack.getMaxCount(), itemOutput.getMaxCountPerStack()) - outputStack.count;
                    int willTake = Math.min(outputAmountRemaining, maxFreeAmountInSlot);
                    outputAmountRemaining -= willTake;
                }
            } else {
                int maxFreeAmountInSlot = Math.min(itemOutput.getMaxCountPerStack(), stack.getMaxCount());
                int willTake = Math.min(outputAmountRemaining, maxFreeAmountInSlot);
                outputAmountRemaining -= willTake;
            }
            if (outputAmountRemaining <= 0) {
                break;
            }
        }

        return outputAmountRemaining <= 0;
    }

    public boolean areFluidOutputsValid(FluidStack stack) {
        if (!usesFluidOutput) return true;
        int outputAmountRemaining;
        outputAmountRemaining = stack.amount;

        outputAmountRemaining *= parallel;

        if (outputAmountRemaining <= 0) return true;
        FluidStack[] contents = fluidOutput.fluidContents;
        for (int i = 0; i < contents.length; i++) {
            FluidStack outputStack = contents[i];
            if (outputStack != null) {
                if (outputStack.isFluidEqual(stack)) {
                    int maxFreeAmountInSlot = fluidOutput.getFluidCapacity(i, null) - outputStack.amount;
                    int willTake = Math.min(outputAmountRemaining, maxFreeAmountInSlot);
                    outputAmountRemaining -= willTake;
                }
            } else {
                int maxFreeAmountInSlot = fluidOutput.getFluidCapacity(i, null);
                int willTake = Math.min(outputAmountRemaining, maxFreeAmountInSlot);
                outputAmountRemaining -= willTake;
            }
            if (outputAmountRemaining <= 0) {
                break;
            }
        }

        return outputAmountRemaining <= 0;
    }

    public boolean fuel() {
        if (allPartsPresent() && energy.getFluid(0, null) != null) {
            int burn = SignalIndustries.getEnergyBurnTime(energy.getFluid(0, null));
            if (burn > 0 && canProcess() && energy.getFluid(0, null) != null && currentRecipe != null) {
                if (energy.getFluid(0, null) != null && energy.getFluid(0, null).amount >= currentRecipe.getData().cost) {
                    progressMaxTicks = (int) (currentRecipe.getData().ticks / speedMultiplier);
                    fuelMaxBurnTicks = fuelBurnTicks = burn;
                    energy.getFluid(0, null).amount -= currentRecipe.getData().cost;
                    if (energy.getFluid(0, null) != null && energy.getFluid(0, null).amount <= 0) {
                        energy.getFluid(0, null);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void setCurrentRecipe() {
        if (allPartsPresent()) {
            List<Object> objs = new ArrayList<>();
            if (usesItemInput) {
                List<ItemStack> items = getItemInputContents().stream().map(ItemStack::copy).toList();
                items.forEach(stack -> stack.count /= parallel);
                objs.addAll(items);
            }
            if (usesFluidInput) {
                List<FluidStack> fluids = getFluidInputContents().stream().map(FluidStack::copy).toList();
                fluids.forEach(stack -> stack.amount /= parallel);
                objs.addAll(fluids);
            }
            objs = objs.stream().map(o -> {
                if (o instanceof ItemStack) {
                    if (((ItemStack) o).count <= 0) {
                        return null;
                    }
                } else if (o instanceof FluidStack) {
                    if (((FluidStack) o).amount <= 0) {
                        return null;
                    }
                } else {
                    return null;
                }
                return o;
            }).filter(Objects::nonNull).collect(Collectors.toList());
            RecipeSymbol[] symbols = RecipeSymbol.arrayOf(objs);
            currentRecipe = recipeGroup.findRecipe(symbols, tier);
        }
    }
}
