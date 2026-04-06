package sunsetsatellite.signalindustries.screen.handler;

import net.danygames2014.nyalib.fluid.FluidSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import sunsetsatellite.signalindustries.block.entity.base.FluidItemContainerBlockEntity;
import sunsetsatellite.signalindustries.block.entity.base.TieredBlockEntity;
import sunsetsatellite.signalindustries.interfaces.Tiered;
import sunsetsatellite.signalindustries.screen.handler.base.MachineScreenHandler;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.Objects;

public class ItemBusScreenHandler extends MachineScreenHandler {
    public ItemBusScreenHandler(PlayerInventory inv, FluidItemContainerBlockEntity tile) {
        super(inv, tile);

        if(tile instanceof Tiered tiered){
            if (tiered.getTier() == Tier.BASIC) {
                for (int i = 0; i < 2; ++i) {
                    for (int l = 0; l < 2; ++l) {
                        this.addSlot(new Slot(tile, l + i * 2, 71 + l * 18, 26 + i * 18));
                    }
                }
            } else {
                for (int i = 0; i < 3; ++i) {
                    for (int l = 0; l < 3; ++l) {
                        this.addSlot(new Slot(tile, l + i * 3, 62 + l * 18, 17 + i * 18));
                    }
                }
            }

            for(int j = 0; j < 3; j++)
            {
                for(int i1 = 0; i1 < 9; i1++)
                {
                    addSlot(new Slot(inv, i1 + j * 9 + 9, 8 + i1 * 18, 84 + j * 18));
                }

            }
        }


        for(int k = 0; k < 9; k++)
        {
            addSlot(new Slot(inv, k, 8 + k * 18, 142));
        }
    }
}
