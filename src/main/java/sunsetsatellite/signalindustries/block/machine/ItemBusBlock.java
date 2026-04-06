package sunsetsatellite.signalindustries.block.machine;

import net.minecraft.block.material.Material;
import sunsetsatellite.signalindustries.block.base.MultiblockPartBaseBlock;
import sunsetsatellite.signalindustries.block.base.TieredMachineBlock;
import sunsetsatellite.signalindustries.block.entity.ItemBusBlockEntity;
import sunsetsatellite.signalindustries.screen.handler.ItemBusScreenHandler;
import sunsetsatellite.signalindustries.util.MultiblockPart;
import sunsetsatellite.signalindustries.util.Tier;

public class ItemBusBlock extends MultiblockPartBaseBlock {
    public ItemBusBlock(String identifier, Material material, Tier tier, MultiblockPart.Type type, MultiblockPart.IO io) {
        super(identifier, material, tier, ItemBusBlockEntity::new, "open_item_bus", ItemBusScreenHandler.class, type, io);
    }
}
