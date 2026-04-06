package sunsetsatellite.signalindustries.block.entity.base;

import net.minecraft.nbt.NbtCompound;
import sunsetsatellite.catalyst.core.util.TickTimer;
import sunsetsatellite.signalindustries.interfaces.HasIOPreview;
import sunsetsatellite.signalindustries.util.ActiveForm;
import sunsetsatellite.signalindustries.util.IOPreview;

public abstract class TieredMachineBaseBlockEntity extends TieredBlockEntity implements ActiveForm, HasIOPreview {

    public int fuelBurnTicks = 0;
    public int fuelMaxBurnTicks = 0;
    public int progressTicks = 0;
    public int progressMaxTicks = 200;
    public float speedMultiplier = 1;
    public float yield = 1;
    public boolean disabled = false;
    public IOPreview preview = IOPreview.NONE;
    public TickTimer IOPreviewTimer = new TickTimer(this,this::disableIOPreview,20,false);

    @Override
    public void disableIOPreview() {
        preview = IOPreview.NONE;
    }

    @Override
    public void setTemporaryIOPreview(IOPreview preview, int ticks) {
        IOPreviewTimer.value = ticks;
        IOPreviewTimer.max = ticks;
        IOPreviewTimer.unpause();
        this.preview = preview;
    }

    @Override
    public IOPreview getPreview() {
        return preview;
    }

    @Override
    public void setPreview(IOPreview preview) {
        this.preview = preview;
    }

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
        IOPreviewTimer.tick();
        if(getBlock() != null){
            applyModifiers();
        }
    }

    public void applyModifiers() {
        speedMultiplier = 1;
        yield = 1;
    }

    public int getProgressDuration(int defaultTicks) {
        return (int) (defaultTicks / speedMultiplier);
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
