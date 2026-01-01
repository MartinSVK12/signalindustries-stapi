package sunsetsatellite.signalindustries.block.machine;

import net.minecraft.block.material.Material;
import sunsetsatellite.signalindustries.block.base.TieredMachineBlock;
import sunsetsatellite.signalindustries.block.entity.AlloySmelterBlockEntity;
import sunsetsatellite.signalindustries.block.entity.ExtractorBlockEntity;
import sunsetsatellite.signalindustries.screen.handler.AlloySmelterScreenHandler;
import sunsetsatellite.signalindustries.screen.handler.ExtractorScreenHandler;
import sunsetsatellite.signalindustries.util.Tier;

public class ExtractorBlock extends TieredMachineBlock {
    public ExtractorBlock(String identifier, Material material, Tier tier) {
        super(identifier, material, tier, ExtractorBlockEntity::new, "open_extractor", ExtractorScreenHandler.class);
    }
}
