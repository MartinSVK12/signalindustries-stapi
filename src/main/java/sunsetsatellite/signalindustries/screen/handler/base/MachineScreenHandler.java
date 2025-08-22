package sunsetsatellite.signalindustries.screen.handler.base;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import sunsetsatellite.signalindustries.block.entity.base.FluidItemContainerBlockEntity;
import sunsetsatellite.signalindustries.fluid.FluidScreenHandler;

public class MachineScreenHandler extends FluidScreenHandler {

    public FluidItemContainerBlockEntity tile;
    public PlayerInventory inv;

    public MachineScreenHandler(PlayerInventory inv, FluidItemContainerBlockEntity tile) {
        super(tile);
        this.inv = inv;
        this.tile = tile;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return tile.canPlayerUse(player);
    }
}
