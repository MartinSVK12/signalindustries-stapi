package sunsetsatellite.signalindustries.block.machine;

import net.minecraft.block.material.Material;
import sunsetsatellite.signalindustries.block.base.TieredMachineBlock;
import sunsetsatellite.signalindustries.block.entity.AlloySmelterBlockEntity;
import sunsetsatellite.signalindustries.screen.handler.AlloySmelterScreenHandler;
import sunsetsatellite.signalindustries.util.Tier;

public class AlloySmelterBlock extends TieredMachineBlock {
    public AlloySmelterBlock(String identifier, Material material, Tier tier) {
        super(identifier, material, tier, AlloySmelterBlockEntity::new, "open_alloy_smelter", AlloySmelterScreenHandler.class);
    }
}
