package sunsetsatellite.signalindustries.block.entity;

import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import sunsetsatellite.catalyst.core.util.BlockInstance;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.vector.Vec3i;
import sunsetsatellite.catalyst.multiblocks.StructureSaver;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.block.CasingBlock;
import sunsetsatellite.signalindustries.block.entity.base.TieredMachineBaseBlockEntity;
import sunsetsatellite.signalindustries.data.SIBlocks;
import sunsetsatellite.signalindustries.data.SIFluids;
import sunsetsatellite.signalindustries.data.SIItems;
import sunsetsatellite.signalindustries.item.PositionChipItem;
import sunsetsatellite.signalindustries.util.Boostable;

import java.util.ArrayList;
import java.util.UUID;

import static net.modificationstation.stationapi.api.state.property.Properties.HORIZONTAL_FACING;

public class EncapsulatorBlockEntity extends TieredMachineBaseBlockEntity implements Boostable {

    public BlockInstance originMarker = null;
    public BlockInstance heightMarker = null;
    public BlockInstance widthMarker = null;
    public BlockInstance depthMarker = null;
    public ArrayList<BlockInstance> structure = new ArrayList<>();
    public Vec3i size = new Vec3i();

    public int energySlot;
    public int cost = 0;
    public int duration = 200;

    public State state = State.NONE;

    public enum State {
        NONE,
        STORING,
        CUTTING
    }

    @Override
    public String getName() {
        return "container.signalindustries.encapsulator.name";
    }

    public EncapsulatorBlockEntity() {
        itemContents = new ItemStack[3];
        fluidContents = new FluidStack[1];
        fluidCapacity = new int[1];
        fluidCapacity[0] = (Short.MAX_VALUE * 2) + 1;
        acceptedFluids.clear();
        acceptedFluids.add(new ArrayList<>());
        acceptedFluids.get(0).add(SIFluids.ENERGY);
        energySlot = 0;
    }

    @Override
    public void tick() {
        super.tick();
        if (world == null) return;
        world.setBlocksDirty(x, y, z, x, y, z);
        if (!canProcess() && state != State.NONE) state = State.NONE;
        Block block = getBlock();
        if (block != null) {
            if (!disabled) work();
        }
        Direction side = getDirection().getOpposite();
        if (heightMarker == null || widthMarker == null || depthMarker == null || originMarker == null) {
            Block originMarker = side.getBlock(world, this);
            if (originMarker == SIBlocks.basicMarker) {
                this.originMarker = new BlockInstance(originMarker, new Vec3i(x, y, z).add(side.getVec()), -1, SIBlocks.basicMarker.getDefaultState(), null);
                int x = this.originMarker.pos.x;
                int y = this.originMarker.pos.y;
                int z = this.originMarker.pos.z;
                Vec3i origin = new Vec3i(x, y, z);
                for (int i = 0; i < 255; i++) {
                    Vec3i pos = new Vec3i(x, i, z);
                    if (origin.equals(pos)) continue;
                    if (world.getBlockState(x, i, z).getBlock() == originMarker) {
                        heightMarker = new BlockInstance(pos.getBlock(world), pos, -1, SIBlocks.basicMarker.getDefaultState(), null);
                        break;
                    }
                }
                for (int i = -64; i <= 64; i++) {
                    Vec3i heightPos = new Vec3i(x + i, y, z);
                    Vec3i widthPos = new Vec3i(x, y, z + i);
                    if (heightPos.equals(origin) || widthPos.equals(origin)) continue;
                    if (world.getBlockState(x + i, y, z).getBlock() == originMarker) {
                        depthMarker = new BlockInstance(heightPos.getBlock(world), heightPos, -1, SIBlocks.basicMarker.getDefaultState(), null);
                    }
                    if (world.getBlockState(x, y, z + i).getBlock() == originMarker) {
                        widthMarker = new BlockInstance(widthPos.getBlock(world), widthPos, -1, SIBlocks.basicMarker.getDefaultState(), null);
                    }
                    if (depthMarker != null && widthMarker != null) {
                        break;
                    }
                }
                if (!areMarkersValid()) {
                    reset();
                }
            }
        } else {
            if (areMarkersValid()) {
                structure.clear();
                int ox = originMarker.pos.x;
                int oy = originMarker.pos.y;
                int oz = originMarker.pos.z;

                int hy = heightMarker.pos.y;
                int dx = depthMarker.pos.x;
                int wz = widthMarker.pos.z;

                int offsetX = ox - dx;
                int offsetY = oy - hy;
                int offsetZ = oz - wz;

                int startX = ox + 1;
                int endX = ox - offsetX;

                if (endX < startX) {
                    int temp = endX;
                    endX = startX;
                    startX = temp;
                }

                int startZ = oz + 1;
                int endZ = oz - offsetZ;
                if (endZ < startZ) {
                    int temp = endZ;
                    endZ = startZ;
                    startZ = temp;
                }

                int startY = oy + 1;
                int endY = oy - offsetY;
                if (endY < startY) {
                    int temp = endY;
                    endY = startY;
                    startY = temp;
                }

                int centerOffsetX = (endX - startX) / 2;
                int centerOffsetY = (endY - startY) / 2;
                int centerOffsetZ = (endZ - startZ) / 2;

                int centerX = startX + centerOffsetX;
                int centerY = startY + centerOffsetY;
                int centerZ = startZ + centerOffsetZ;

                if(itemContents[2] != null && itemContents[2].getItem() instanceof PositionChipItem){
                    PositionChipItem chip = (PositionChipItem) itemContents[2].getItem();
                    Vec3i position = chip.getPosition(itemContents[2]);
                    if(position != null && world.getBlockId(position.x, position.y, position.z) != 0) {
                        centerX = position.x;
                        centerY = position.y;
                        centerZ = position.z;
                    }
                }

                size = new Vec3i(Math.abs(offsetX), Math.abs(offsetY), Math.abs(offsetZ));

                for (int i = startX; i < endX; i++) {
                    for (int j = startY; j < endY; j++) {
                        for (int k = startZ; k < endZ; k++) {
                            if (world.getBlockId(i, j, k) == 0) continue;
                            if (world.getBlockState(i, j, k).getBlock().getHardness() == -1) continue;

                            structure.add(new BlockInstance(world.getBlockState(i, j, k).getBlock(), new Vec3i(i - centerX, j - centerY, k - centerZ), world.getBlockMeta(i, j, k), world.getBlockState(i,j,k), world.getBlockEntity(i, j, k)));
                        }
                    }
                }
            } else {
                reset();
            }
        }
    }

    public void work() {
        if (world.isRemote) return;
        boolean update = false;
        if (fuelBurnTicks > 0) {
            fuelBurnTicks--;
        }
        if (areAllInputsNull()) {
            state = State.NONE;
            progressTicks = 0;
            duration = 200;
            cost = 0;
        } else if (canProcess()) {
            if(state == State.STORING && !structure.isEmpty()){
                duration = Math.min(300, 5 * (size.x * size.y * size.z));
                cost = Math.max(4096, Math.min(65535,16 * (size.x * size.y * size.z)));
            }
            progressMaxTicks = getProgressDuration(duration);
        }
        if (!world.isRemote) {
            if (progressTicks == 0 && canProcess()) {
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

    public void processItem() {
        ItemStack item = itemContents[0];
        if (item != null && (item.getItem().equals(SIItems.blueprint) || item.getItem().equals(SIItems.goldprint)) && world != null) {
            itemContents[0] = null;
            itemContents[1] = item;
            BlockInstance origin = null;
            if(itemContents[2] != null && itemContents[2].getItem() instanceof PositionChipItem chip){
                Vec3i position = chip.getPosition(itemContents[2]);
                if(position != null && world.getBlockId(position.x, position.y, position.z) != 0) {
                    origin = new BlockInstance(position, world);
                }
            }
            if (!structure.isEmpty()) {
                if(origin != null) {
                    structure.forEach((B)->{
                        if(B.block instanceof CasingBlock){
                            B.meta = -1;
                        }
                    });
                }
                NbtCompound data = StructureSaver.serialize("structure", structure, state == State.CUTTING, origin);
                UUID id = StructureSaver.save(data, world);
                if (id == null) return;
                item.getStationNbt().putString("structure", id.toString());
                if (state == State.CUTTING) {
                    structure.forEach(B -> {
                        if (B.block.getHardness() != -1) {
                            world.setBlock(B.pos.x, B.pos.y, B.pos.z, 0);
                        }
                    });
                }
            }
        }
        state = State.NONE;
    }

    public boolean canProcess() {
        if (itemContents[0] == null) return false;
        if (itemContents[1] != null) return false;
        if (state == State.NONE) return false;
        if (state == State.STORING) {
            return itemContents[0].getItem().equals(SIItems.blueprint);
        }
        if (state == State.CUTTING) {
            return itemContents[0].getItem().equals(SIItems.goldprint);
        }
        return false;
    }

    public boolean areAllInputsNull() {
        return itemContents[0] == null;
    }

    public boolean fuel() {
        int burn = SignalIndustries.getEnergyBurnTime(fluidContents[energySlot]);
        if (burn > 0 && canProcess() && fuelBurnTicks <= 0) {
            if (fluidContents[energySlot].amount >= cost) {
                progressMaxTicks = getProgressDuration(duration);
                fuelMaxBurnTicks = fuelBurnTicks = burn;
                fluidContents[energySlot].amount -= cost;
                if (fluidContents[energySlot].amount == 0) {
                    fluidContents[energySlot] = null;
                }
                return true;
            }
        }
        return false;
    }

    public boolean areMarkersValid() {
        if (widthMarker == null || heightMarker == null || depthMarker == null || originMarker == null || world == null)
            return false;
        return heightMarker.exists(world) && widthMarker.exists(world) && depthMarker.exists(world) && originMarker.exists(world);
    }

    public void reset() {
        heightMarker = null;
        widthMarker = null;
        depthMarker = null;
        originMarker = null;
        structure.clear();
        size = new Vec3i();
        cost = 0;
        duration = 200;
    }

    @Override
    public void buttonClicked(int id, int button, int channel) {
        super.buttonClicked(id, button, channel);
        reset();

        if (id == 2) {
            state = State.CUTTING;
        } else if (id == 3) {
            state = State.STORING;
        }

    }
}
