package sunsetsatellite.signalindustries.block.entity.base;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.catalyst.core.util.BlockEntityInit;
import sunsetsatellite.signalindustries.interfaces.Tiered;
import sunsetsatellite.signalindustries.util.Tier;

public class TieredBlockEntity extends CoverableBlockEntity implements Tiered, BlockEntityInit {
    public Tier tier;

    @Override
    public void init(Block block) {
        tier = ((Tiered) block).getTier();
    }

    @Override
    public Tier getTier() {
        return tier;
    }

    @Override
    public @NotNull String[] getTooltip(ItemStack itemStack, String s) {
        throw new UnsupportedOperationException();
    }
}
