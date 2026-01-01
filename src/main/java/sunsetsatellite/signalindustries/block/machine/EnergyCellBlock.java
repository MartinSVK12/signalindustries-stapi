package sunsetsatellite.signalindustries.block.machine;

import net.minecraft.block.material.Material;
import sunsetsatellite.signalindustries.block.base.TieredMachineBlock;
import sunsetsatellite.signalindustries.block.entity.EnergyCellBlockEntity;
import sunsetsatellite.signalindustries.screen.handler.EnergyCellScreenHandler;
import sunsetsatellite.signalindustries.util.Tier;

public class EnergyCellBlock extends TieredMachineBlock {
    public EnergyCellBlock(String identifier, Material material, Tier tier) {
        super(identifier, material, tier, EnergyCellBlockEntity::new, "open_energy_cell", EnergyCellScreenHandler.class);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
