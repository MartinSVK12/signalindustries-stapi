package sunsetsatellite.signalindustries.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.jetbrains.annotations.Nullable;
import sunsetsatellite.signalindustries.block.base.TieredBlock;
import sunsetsatellite.signalindustries.util.Tier;

public class CasingBlock extends TieredBlock {

    public CasingBlock(String identifier, Material material, Tier tier) {
        super(identifier, material, tier);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
