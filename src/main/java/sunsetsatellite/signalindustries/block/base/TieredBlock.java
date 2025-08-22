package sunsetsatellite.signalindustries.block.base;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.catalyst.core.util.model.LayeredCubeModel;
import sunsetsatellite.signalindustries.interfaces.Tiered;
import sunsetsatellite.signalindustries.util.Tier;

public class TieredBlock extends SIBlock implements Tiered, LayeredCubeModel {
    public final Tier tier;

    public TieredBlock(Identifier identifier, Material material, Tier tier) {
        super(identifier, material);
        this.tier = tier;
    }

    @Override
    public Tier getTier() {
        return tier;
    }

    @Override
    public @NotNull String[] getTooltip(ItemStack itemStack, String s) {
        return new String[]{s, "Tier: " + tier.getTextColor() + tier.getRank()};
    }


}
