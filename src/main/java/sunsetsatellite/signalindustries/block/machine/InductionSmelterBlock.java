package sunsetsatellite.signalindustries.block.machine;

import net.minecraft.block.material.Material;
import sunsetsatellite.signalindustries.block.base.TieredMachineBlock;
import sunsetsatellite.signalindustries.block.entity.ExtractorBlockEntity;
import sunsetsatellite.signalindustries.block.entity.multiblock.InductionSmelterBlockEntity;
import sunsetsatellite.signalindustries.screen.handler.ExtractorScreenHandler;
import sunsetsatellite.signalindustries.screen.handler.MultiblockScreenHandler;
import sunsetsatellite.signalindustries.util.Tier;

public class InductionSmelterBlock extends TieredMachineBlock {
    public InductionSmelterBlock(String identifier, Material material, Tier tier) {
        super(identifier, material, tier, InductionSmelterBlockEntity::new, "open_induction_smelter", MultiblockScreenHandler.class);
    }
}
