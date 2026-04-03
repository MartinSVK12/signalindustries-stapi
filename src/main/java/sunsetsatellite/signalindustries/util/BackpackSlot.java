package sunsetsatellite.signalindustries.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import sunsetsatellite.signalindustries.item.attachment.BackpackAttachmentItem;

public class BackpackSlot extends Slot {
    public BackpackSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean isLocked(int index, ItemStack stackInSlot, PlayerEntity player) {
        if(stackInSlot != null){
            if(stackInSlot.getItem() instanceof BackpackAttachmentItem){
                return true;
            }
        }
        return super.isLocked(index, stackInSlot, player);
    }
}
