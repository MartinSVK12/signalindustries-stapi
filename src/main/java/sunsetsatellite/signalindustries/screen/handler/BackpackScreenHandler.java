package sunsetsatellite.signalindustries.screen.handler;

import net.danygames2014.nyalib.fluid.FluidSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import sunsetsatellite.signalindustries.invs.BackpackInventory;
import sunsetsatellite.signalindustries.item.attachment.BackpackAttachmentItem;
import sunsetsatellite.signalindustries.util.BackpackPlayerSlot;
import sunsetsatellite.signalindustries.util.BackpackSlot;

public class BackpackScreenHandler extends ScreenHandler {

    public BackpackInventory inventory;

    public BackpackScreenHandler(PlayerInventory player, int slot) {

        ItemStack playerStack = player.getStack(slot);
        inventory = new BackpackInventory(player, playerStack);

        ItemStack stack = inventory.stack;
        if(stack != null && stack.getItem() instanceof BackpackAttachmentItem backpack){
            switch (backpack.tier){
                case BASIC -> {
                    for (int y = 0; y < 2; y++) {
                        addFluidSlot(new FluidSlot(inventory, y, 174, 36 + 18 * y));
                    }

                    int numberOfRows = 27 / 9;
                    int i = (numberOfRows - 4) * 18;
                    for (int j = 0; j < numberOfRows; j++) {
                        for (int i1 = 0; i1 < 9; i1++) {
                            addSlot(new BackpackSlot(inventory, i1 + j * 9, 8 + i1 * 18, 18 + j * 18));
                        }

                    }

                    for (int k = 0; k < 3; k++) {
                        for (int j1 = 0; j1 < 9; j1++) {
                            addSlot(new BackpackPlayerSlot(player, j1 + k * 9 + 9, 8 + j1 * 18, 104 + k * 18 + i));
                        }

                    }

                    for (int l = 0; l < 9; l++) {
                        addSlot(new BackpackPlayerSlot(player, l, 8 + l * 18, 162 + i));
                    }
                }
                case REINFORCED -> {
                    for (int y = 0; y < 4; y++) {
                        addFluidSlot(new FluidSlot(inventory, y, 174, 36 + 18 * y));
                    }

                    int numberOfRows = (27 * 2) / 9;
                    int i = (numberOfRows - 4) * 18;
                    for (int j = 0; j < numberOfRows; j++) {
                        for (int i1 = 0; i1 < 9; i1++) {
                            addSlot(new BackpackSlot(inventory, i1 + j * 9, 8 + i1 * 18, 18 + j * 18));
                        }

                    }

                    for (int k = 0; k < 3; k++) {
                        for (int j1 = 0; j1 < 9; j1++) {
                            addSlot(new BackpackPlayerSlot(player, j1 + k * 9 + 9, 8 + j1 * 18, 104 + k * 18 + i));
                        }

                    }

                    for (int l = 0; l < 9; l++) {
                        addSlot(new BackpackPlayerSlot(player, l, 8 + l * 18, 162 + i));
                    }
                }
            }
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
