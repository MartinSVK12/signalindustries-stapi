package sunsetsatellite.signalindustries.block.machine;

import net.minecraft.block.material.Material;
import sunsetsatellite.signalindustries.block.base.MultiblockPartBaseBlock;
import sunsetsatellite.signalindustries.block.base.TieredMachineBlock;
import sunsetsatellite.signalindustries.block.entity.EnergyConnectorBlockEntity;
import sunsetsatellite.signalindustries.block.entity.FluidHatchBlockEntity;
import sunsetsatellite.signalindustries.block.entity.ItemBusBlockEntity;
import sunsetsatellite.signalindustries.screen.handler.EnergyConnectorScreenHandler;
import sunsetsatellite.signalindustries.screen.handler.FluidHatchScreenHandler;
import sunsetsatellite.signalindustries.screen.handler.ItemBusScreenHandler;
import sunsetsatellite.signalindustries.util.MultiblockPart;
import sunsetsatellite.signalindustries.util.Tier;

public class FluidHatchBlock extends MultiblockPartBaseBlock {
    public FluidHatchBlock(String identifier, Material material, Tier tier, MultiblockPart.Type type, MultiblockPart.IO io) {
        super(identifier, material, tier, FluidHatchBlockEntity::new, "open_fluid_hatch", FluidHatchScreenHandler.class, type, io);
    }
}