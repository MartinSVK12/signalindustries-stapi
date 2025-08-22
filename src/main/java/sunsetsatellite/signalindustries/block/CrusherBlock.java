package sunsetsatellite.signalindustries.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.screen.ScreenHandler;
import net.modificationstation.stationapi.api.util.Identifier;
import sunsetsatellite.signalindustries.block.base.TieredMachineBlock;
import sunsetsatellite.signalindustries.block.entity.CrusherBlockEntity;
import sunsetsatellite.signalindustries.screen.handler.CrusherScreenHandler;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.function.Supplier;

public class CrusherBlock extends TieredMachineBlock {
    public CrusherBlock(Identifier identifier, Material material, Tier tier) {
        super(identifier, material, tier, CrusherBlockEntity::new, "open_crusher", CrusherScreenHandler.class);
    }
}
