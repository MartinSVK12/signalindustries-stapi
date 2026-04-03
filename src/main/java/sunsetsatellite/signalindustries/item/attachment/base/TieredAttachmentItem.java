package sunsetsatellite.signalindustries.item.attachment.base;

import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.signalindustries.interfaces.Tiered;
import sunsetsatellite.signalindustries.util.AttachmentPoint;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.ArrayList;
import java.util.List;

public abstract class TieredAttachmentItem extends AttachmentItem implements Tiered {
    public final Tier tier;

    public TieredAttachmentItem(Identifier identifier, List<AttachmentPoint> attachmentPoints, Tier tier) {
        super(identifier, attachmentPoints);
        this.tier = tier;
    }

    @Override
    public @NotNull String[] getTooltip(ItemStack itemStack, String s) {
        List<String> tooltip = new ArrayList<>(List.of(super.getTooltip(itemStack, s)));
        tooltip.add("Tier: " + tier.getTextColor() + tier.getRank());
        return tooltip.toArray(new String[0]);
    }

    @Override
    public Tier getTier() {
        return tier;
    }
}
