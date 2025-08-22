package sunsetsatellite.signalindustries.block.entity.base;

import net.minecraft.nbt.NbtCompound;
import sunsetsatellite.signalindustries.util.ActiveForm;

public abstract class TieredMachineBaseBlockEntity extends TieredBlockEntity implements ActiveForm {

    public int fuelBurnTicks = 0;
    public int fuelMaxBurnTicks = 0;
    public int progressTicks = 0;
    public int progressMaxTicks = 200;
    public float speedMultiplier = 1;
    public float yield = 1;
    public boolean disabled = false;

    @Override
    public boolean isActive() {
        return fuelBurnTicks > 0;
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public void tick() {
        super.tick();
        if(world.isRemote) return;
        if(getBlock() != null){
            applyModifiers();
        }
    }

    public void applyModifiers() {
        speedMultiplier = 1;
        yield = 1;
    }

    public int getTieredProgressDuration(int defaultTicks){
        return (int) (((float) defaultTicks / (tier.ordinal()+1)) / speedMultiplier);
    }

    public int getProgressScaled(int paramInt) {
        return this.progressTicks * paramInt / progressMaxTicks;
    }

    public int getBurnTimeScaled(int paramInt) {
        if(this.fuelMaxBurnTicks == 0) {
            this.fuelMaxBurnTicks = 200;
        }
        return this.fuelBurnTicks * paramInt / this.fuelMaxBurnTicks;
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.putShort("BurnTime", (short)this.fuelBurnTicks);
        tag.putShort("ProcessTime", (short)this.progressTicks);
        tag.putShort("MaxBurnTime", (short)this.fuelMaxBurnTicks);
        tag.putInt("MaxProcessTime",this.progressMaxTicks);
        tag.putBoolean("Disabled",disabled);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        fuelBurnTicks = tag.getShort("BurnTime");
        progressTicks = tag.getShort("ProcessTime");
        progressMaxTicks = tag.getInt("MaxProcessTime");
        fuelMaxBurnTicks = tag.getShort("MaxBurnTime");
        disabled = tag.getBoolean("Disabled");
    }
}
