package sunsetsatellite.signalindustries.screen.handler;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import sunsetsatellite.signalindustries.block.entity.base.FluidItemContainerBlockEntity;
import sunsetsatellite.signalindustries.screen.handler.base.MachineScreenHandler;
import sunsetsatellite.signalindustries.util.SlotFluid;

public class CrusherScreenHandler extends MachineScreenHandler {
    public CrusherScreenHandler(PlayerInventory inv, FluidItemContainerBlockEntity tile) {
        super(inv, tile);

        SlotFluid slot = new SlotFluid(tile, 0, 56,53);
        addFluidSlot(slot);

        this.addSlot(new Slot(tile, 0, 56, 17));
        this.addSlot(new Slot(tile, 1, 116, 35));

        for(int j = 0; j < 3; j++)
        {
            for(int i1 = 0; i1 < 9; i1++)
            {
                addSlot(new Slot(inv, i1 + j * 9 + 9, 8 + i1 * 18, 84 + j * 18));
            }

        }

        for(int k = 0; k < 9; k++)
        {
            addSlot(new Slot(inv, k, 8 + k * 18, 142));
        }
    }
}
