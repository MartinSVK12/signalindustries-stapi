package sunsetsatellite.signalindustries.fluid;
import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.entity.player.PlayerEntity;

public interface FluidPickupController {

    FluidStack onFluidPickUpFromInventory(int syncId, int slotId, int button, boolean shift, boolean control, PlayerEntity player);
}
