package sunsetsatellite.signalindustries.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.world.BlockStateView;
import sunsetsatellite.catalyst.core.util.model.FullyRotatableBlock;
import sunsetsatellite.catalyst.core.util.model.LayeredCubeModel;
import sunsetsatellite.signalindustries.SignalIndustries;

public class CoilBlock extends FullyRotatableBlock implements LayeredCubeModel {
    public CoilBlock(String identifier, Material material) {
        super(Identifier.of(SignalIndustries.NAMESPACE,identifier), material);
    }

    @Override
    public boolean renderLayer(BlockView view, BlockStateView blockStateView, int x, int y, int z, int meta, int layer) {
        return layer == 0;
    }
}
