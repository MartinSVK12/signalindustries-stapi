package sunsetsatellite.signalindustries.interfaces;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sunsetsatellite.signalindustries.util.AttachmentPoint;

import java.util.List;

public interface Attachment {
    List<AttachmentPoint> getAttachmentPoints();

    void activate(ItemStack stack, PowerSuit signalumPowerSuit, PlayerEntity player, World world, boolean shift, boolean ctrl, boolean alt);

    void altActivate(ItemStack stack, PowerSuit signalumPowerSuit, PlayerEntity player, World world, boolean shift, boolean ctrl, boolean alt);

    void renderWhenAttached(PlayerEntity player, PowerSuit signalumPowerSuit, BipedEntityModel modelBipedMain, ItemStack stack);
}
