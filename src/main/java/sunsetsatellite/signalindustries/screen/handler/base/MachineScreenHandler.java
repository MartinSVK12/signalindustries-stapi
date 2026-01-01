package sunsetsatellite.signalindustries.screen.handler.base;

import net.danygames2014.nyalib.capability.CapabilityHelper;
import net.danygames2014.nyalib.capability.item.fluidhandler.FluidHandlerItemCapability;
import net.danygames2014.nyalib.fluid.FluidSlot;
import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import sunsetsatellite.signalindustries.block.entity.base.FluidItemContainerBlockEntity;

public class MachineScreenHandler extends ScreenHandler {

    public FluidItemContainerBlockEntity tile;
    public PlayerInventory inv;

    public MachineScreenHandler(PlayerInventory inv, FluidItemContainerBlockEntity tile) {
        this.inv = inv;
        this.tile = tile;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return tile.canPlayerUse(player);
    }
}
