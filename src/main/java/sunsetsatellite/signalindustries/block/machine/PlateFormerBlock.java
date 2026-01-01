package sunsetsatellite.signalindustries.block.machine;

import net.minecraft.block.material.Material;
import sunsetsatellite.signalindustries.block.base.TieredMachineBlock;
import sunsetsatellite.signalindustries.block.entity.PlateFormerBlockEntity;
import sunsetsatellite.signalindustries.screen.handler.PlateFormerScreenHandler;
import sunsetsatellite.signalindustries.util.Tier;

public class PlateFormerBlock extends TieredMachineBlock {
    public PlateFormerBlock(String identifier, Material material, Tier tier) {
        super(identifier, material, tier, PlateFormerBlockEntity::new, "open_plate_former", PlateFormerScreenHandler.class);
    }
}
