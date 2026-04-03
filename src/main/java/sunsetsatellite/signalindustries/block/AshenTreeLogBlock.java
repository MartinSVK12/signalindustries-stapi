package sunsetsatellite.signalindustries.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.world.BlockStateView;
import sunsetsatellite.catalyst.core.util.model.RotatableBlock;

import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class AshenTreeLogBlock extends RotatableBlock {

    public static final BooleanProperty CONTAINS_RESIN = BooleanProperty.of("contains_resin");

    public AshenTreeLogBlock(String id) {
        super(NAMESPACE.id(id), Material.WOOD);
        setDefaultState(getDefaultState().with(CONTAINS_RESIN,false));
    }

    @Override
    public boolean renderLayer(BlockView view, BlockStateView blockStateView, int x, int y, int z, int meta, int layer) {
        if(layer == 1) {
            return blockStateView.getBlockState(x, y, z).get(CONTAINS_RESIN);
        };
        return true;
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        super.onPlaced(world, x, y, z);
        world.setBlockState(x,y,z,getDefaultState().with(CONTAINS_RESIN,false));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CONTAINS_RESIN);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getDefaultState().with(CONTAINS_RESIN,false);
    }
}
