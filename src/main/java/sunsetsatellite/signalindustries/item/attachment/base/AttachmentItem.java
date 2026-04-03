package sunsetsatellite.signalindustries.item.attachment.base;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Formatting;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.signalindustries.interfaces.Attachment;
import sunsetsatellite.signalindustries.interfaces.PowerSuit;
import sunsetsatellite.signalindustries.util.AttachmentPoint;

import java.util.ArrayList;
import java.util.List;

public abstract class AttachmentItem extends TemplateItem implements Attachment, CustomTooltipProvider {

    public final List<AttachmentPoint> attachmentPoints;

    public AttachmentItem(Identifier identifier, List<AttachmentPoint> attachmentPoints) {
        super(identifier);
        this.attachmentPoints = attachmentPoints;
    }

    @Override
    public @NotNull String[] getTooltip(ItemStack itemStack, String s) {
        List<String> tooltip = new ArrayList<>();
        tooltip.add(s);
        tooltip.add(Formatting.YELLOW + "Attachment" + Formatting.WHITE);
        for (AttachmentPoint attachmentPoint : attachmentPoints) {
            tooltip.add("- " + attachmentPoint);
        }
        return tooltip.toArray(new String[0]);
    }

    @Override
    public List<AttachmentPoint> getAttachmentPoints() {
        return attachmentPoints;
    }

    @Override
    public void activate(ItemStack stack, PowerSuit signalumPowerSuit, PlayerEntity player, World world, boolean shift, boolean ctrl, boolean alt) {

    }

    @Override
    public void altActivate(ItemStack stack, PowerSuit signalumPowerSuit, PlayerEntity player, World world, boolean shift, boolean ctrl, boolean alt) {

    }

    public abstract void tick(ItemStack stack, PowerSuit signalumPowerSuit, PlayerEntity player, World world, int slot);

    @Override
    public void renderWhenAttached(PlayerEntity player, PowerSuit signalumPowerSuit, BipedEntityModel modelBipedMain, ItemStack stack) {

    }
}
