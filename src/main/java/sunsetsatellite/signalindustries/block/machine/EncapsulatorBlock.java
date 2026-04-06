package sunsetsatellite.signalindustries.block.machine;

import net.minecraft.block.material.Material;
import sunsetsatellite.signalindustries.block.base.TieredMachineBlock;
import sunsetsatellite.signalindustries.block.entity.EncapsulatorBlockEntity;
import sunsetsatellite.signalindustries.block.entity.multiblock.InductionSmelterBlockEntity;
import sunsetsatellite.signalindustries.screen.handler.EncapsulatorScreenHandler;
import sunsetsatellite.signalindustries.screen.handler.MultiblockScreenHandler;
import sunsetsatellite.signalindustries.util.Tier;

public class EncapsulatorBlock extends TieredMachineBlock {
    public EncapsulatorBlock(String identifier, Material material, Tier tier) {
        super(identifier, material, tier, EncapsulatorBlockEntity::new, "open_encapsulator", EncapsulatorScreenHandler.class);
    }
}
