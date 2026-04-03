package sunsetsatellite.signalindustries.item.attachment;

import net.danygames2014.nyalib.fluid.item.ManagedFluidHandlerItem;
import net.danygames2014.nyalib.item.item.ManagedItemHandlerItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.util.Identifier;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.interfaces.PowerSuit;
import sunsetsatellite.signalindustries.invs.BackpackInventory;
import sunsetsatellite.signalindustries.item.attachment.base.TieredAttachmentItem;
import sunsetsatellite.signalindustries.screen.handler.BackpackScreenHandler;
import sunsetsatellite.signalindustries.util.AttachmentPoint;
import sunsetsatellite.signalindustries.util.Tier;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class BackpackAttachmentItem extends TieredAttachmentItem implements ManagedItemHandlerItem, ManagedFluidHandlerItem {
    public BackpackAttachmentItem(String id, List<AttachmentPoint> attachmentPoints, Tier tier) {
        super(NAMESPACE.id(id), attachmentPoints, tier);
        switch (tier) {
            case BASIC -> {
                for (int i = 0; i < 2; i++) {
                    addFluidSlot(2000);
                }
                for (int i = 0; i < 27; i++) {
                    addItemSlot();
                }
            }
            case REINFORCED -> {
                for (int i = 0; i < 4; i++) {
                    addFluidSlot(4000);
                }
                for (int i = 0; i < 27 * 2; i++) {
                    addItemSlot();
                }
            }
        }
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        GuiHelper.openGUI(user,NAMESPACE.id("open_backpack"), new BackpackInventory(user.inventory, user.inventory.getStack(user.inventory.selectedSlot)), new BackpackScreenHandler(user.inventory, user.inventory.selectedSlot));
        return super.use(stack, world, user);
    }

    @Override
    public void tick(ItemStack stack, PowerSuit signalumPowerSuit, PlayerEntity player, World world, int slot) {

    }
}
