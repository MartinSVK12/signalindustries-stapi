package sunsetsatellite.signalindustries.item;

import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Formatting;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.catalyst.multiblocks.Multiblock;
import sunsetsatellite.signalindustries.util.SIMultiblock;

public class BlueprintItem extends TemplateItem implements CustomTooltipProvider {
    public BlueprintItem(Identifier identifier) {
        super(identifier);
    }

    @Override
    public @NotNull String[] getTooltip(ItemStack stack, String originalTooltip) {
        String key = stack.getStationNbt().getString("multiblock");
        String key2 = stack.getStationNbt().getString("structure");
        if (!key.isEmpty()) {
            SIMultiblock multiblock = (SIMultiblock) Multiblock.multiblocks.get(key.replace("multiblock.signalindustries.", ""));
            return new String[]{originalTooltip, "Tier: " + multiblock.tier.getTextColor() + multiblock.tier.getRank() + "\n" + Formatting.BLUE + multiblock.getTranslatedName()};
        } else if (!key2.isEmpty()) {
            return new String[]{originalTooltip, Formatting.GRAY + key2};
        }
        return new String[]{originalTooltip, Formatting.GRAY + "Blank"};
    }
}
