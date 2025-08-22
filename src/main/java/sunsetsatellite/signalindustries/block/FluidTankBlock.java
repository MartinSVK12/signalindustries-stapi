package sunsetsatellite.signalindustries.block;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.util.Identifier;
import sunsetsatellite.signalindustries.block.base.TieredMachineBlock;
import sunsetsatellite.signalindustries.block.entity.FluidTankBlockEntity;
import sunsetsatellite.signalindustries.screen.handler.FluidTankScreenHandler;
import sunsetsatellite.signalindustries.util.Tier;

public class FluidTankBlock extends TieredMachineBlock {
    public FluidTankBlock(Identifier identifier, Material material, Tier tier) {
        super(identifier, material, tier, FluidTankBlockEntity::new, "open_fluid_tank", FluidTankScreenHandler.class);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
