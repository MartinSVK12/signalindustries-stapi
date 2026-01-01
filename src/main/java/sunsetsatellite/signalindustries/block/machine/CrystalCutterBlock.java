package sunsetsatellite.signalindustries.block.machine;

import net.minecraft.block.material.Material;
import sunsetsatellite.signalindustries.block.base.TieredMachineBlock;
import sunsetsatellite.signalindustries.block.entity.CrystalCutterBlockEntity;
import sunsetsatellite.signalindustries.screen.handler.CrystalCutterScreenHandler;
import sunsetsatellite.signalindustries.util.Tier;

public class CrystalCutterBlock extends TieredMachineBlock {
    public CrystalCutterBlock(String identifier, Material material, Tier tier) {
        super(identifier, material, tier, CrystalCutterBlockEntity::new, "open_crystal_cutter", CrystalCutterScreenHandler.class);
    }
}
