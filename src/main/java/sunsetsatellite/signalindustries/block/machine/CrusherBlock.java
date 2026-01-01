package sunsetsatellite.signalindustries.block.machine;

import net.minecraft.block.material.Material;
import sunsetsatellite.signalindustries.block.base.TieredMachineBlock;
import sunsetsatellite.signalindustries.block.entity.CrusherBlockEntity;
import sunsetsatellite.signalindustries.screen.handler.CrusherScreenHandler;
import sunsetsatellite.signalindustries.util.Tier;

public class CrusherBlock extends TieredMachineBlock {
    public CrusherBlock(String identifier, Material material, Tier tier) {
        super(identifier, material, tier, CrusherBlockEntity::new, "open_crusher", CrusherScreenHandler.class);
    }
}
