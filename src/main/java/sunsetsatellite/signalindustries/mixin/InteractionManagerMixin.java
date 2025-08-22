package sunsetsatellite.signalindustries.mixin;

import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.client.InteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import sunsetsatellite.signalindustries.fluid.FluidPickupController;
import sunsetsatellite.signalindustries.fluid.FluidScreenHandler;

@Mixin(InteractionManager.class)
public class InteractionManagerMixin implements FluidPickupController {
    @Override
    public FluidStack onFluidPickUpFromInventory(int syncId, int slotId, int button, boolean shift, boolean control, PlayerEntity player) {
        if(player.currentScreenHandler instanceof FluidScreenHandler){
            return ((FluidScreenHandler) player.currentScreenHandler).onFluidSlotClick(slotId, button, shift, player);
        }
        return null;
    }
}
