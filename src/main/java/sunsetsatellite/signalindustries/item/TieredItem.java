package sunsetsatellite.signalindustries.item;

import net.glasslauncher.mods.alwaysmoreitems.api.Rarity;
import net.glasslauncher.mods.alwaysmoreitems.api.RarityProvider;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.signalindustries.interfaces.Tiered;
import sunsetsatellite.signalindustries.util.Tier;

public class TieredItem extends TemplateItem implements Tiered {
    public final Tier tier;

    public TieredItem(Identifier identifier, Tier tier) {
        super(identifier);
        this.tier = tier;
    }

    @Override
    public @NotNull String[] getTooltip(ItemStack itemStack, String s) {
        return new String[]{s, "Tier: " + tier.getTextColor() + tier.getRank()};
    }

    @Override
    public Tier getTier() {
        return tier;
    }
}
