package sunsetsatellite.signalindustries.block.base;

import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.jetbrains.annotations.Nullable;
import sunsetsatellite.catalyst.core.util.model.LayeredCubeModel;
import sunsetsatellite.catalyst.core.util.model.TextureLayer;

public class SIBlock extends TemplateBlock implements LayeredCubeModel {

    public final TextureLayer textures = new TextureLayer(0);

    public SIBlock(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    public TextureLayer[] getTextureLayers() {
        return new TextureLayer[]{textures};
    }

    @Override
    public boolean isLayerFullbright(int layer) {
        return false;
    }

    @Override
    public Atlas.@Nullable Sprite getLayerTexture(BlockView view, BlockStateView blockStateView, int x, int y, int z, int meta, int side, int layer) {
        return textures.get(side);
    }

    @Override
    public int getTexture(int side) {
        return textures.get(side) != null ? textures.get(side).index : 0;
    }

    @Override
    public boolean renderLayer(BlockView view, int x, int y, int z, int meta, int layer) {
        return true;
    }
}
