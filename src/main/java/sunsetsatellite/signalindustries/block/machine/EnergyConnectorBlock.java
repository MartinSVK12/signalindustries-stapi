package sunsetsatellite.signalindustries.block.machine;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.screen.ScreenHandler;
import sunsetsatellite.signalindustries.block.base.MultiblockPartBaseBlock;
import sunsetsatellite.signalindustries.block.base.TieredMachineBlock;
import sunsetsatellite.signalindustries.block.entity.EnergyConnectorBlockEntity;
import sunsetsatellite.signalindustries.block.entity.FluidHatchBlockEntity;
import sunsetsatellite.signalindustries.screen.handler.EnergyConnectorScreenHandler;
import sunsetsatellite.signalindustries.screen.handler.FluidHatchScreenHandler;
import sunsetsatellite.signalindustries.util.MultiblockPart;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.function.Supplier;

public class EnergyConnectorBlock extends MultiblockPartBaseBlock {
    public EnergyConnectorBlock(String identifier, Material material, Tier tier, MultiblockPart.Type type, MultiblockPart.IO io) {
        super(identifier, material, tier, EnergyConnectorBlockEntity::new, "open_energy_connector", EnergyConnectorScreenHandler.class, type, io);
    }
}