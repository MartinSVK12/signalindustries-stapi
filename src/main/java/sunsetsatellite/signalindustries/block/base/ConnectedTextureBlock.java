package sunsetsatellite.signalindustries.block.base;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.jetbrains.annotations.Nullable;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.Side;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.BitSet;
import java.util.List;

public class ConnectedTextureBlock extends TieredBlock {

    public String textureBase;
    public List<Block> connectsTo;
    public Atlas.Sprite[] texCoord;

    public static final int[][] relCoords = {
            {2, 5, 3, 4}, //up 0
            {2, 5, 3, 4}, //down 1
            {1, 4, 0, 5}, //north 2
            {1, 5, 0, 4}, //south 3
            {1, 3, 0, 2}, //east 4
            {1, 2, 0, 3} //west 5
    };

    public ConnectedTextureBlock(String identifier, Material material, Tier tier, String textureBase, List<Block> connectsTo) {
        super(identifier, material, tier);
        this.textureBase = textureBase;
        this.connectsTo = connectsTo;
    }

    @Override
    public Atlas.@Nullable Sprite getLayerTexture(BlockView view, BlockStateView blockStateView, int x, int y, int z, int meta, int side, int layer) {
        int state = checkNeighbors(blockStateView, x, y, z);
        BitSet bits = intToBitSet(state, 6), subbits = new BitSet(4);
        for (int i = 0; i < 4; i++) {
            subbits.set(i, bits.get(relCoords[side][i]));
        }
        return texCoord[toInt(subbits)];
    }

    public int checkNeighbors(BlockStateView world, int x, int y, int z) {
        int state = 0;
        for (Direction dir : Direction.values()) {
            int j = x + dir.getVec().x, k = y + dir.getVec().y, l = z + dir.getVec().z; //offset coords
            if (world.getBlockState(j, k, l).getBlock() == this || connectsTo.contains(world.getBlockState(j,k,l).getBlock())) state += (int) Math.pow(2, dir.getSideNumber());
        }
        return state & 0x3F;
    }

    public static int toInt(BitSet s) {
        int v = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.get(i)) v |= (1 << i);
        }
        return v;
    }

    public static BitSet intToBitSet(int v, int l) {
        BitSet b = new BitSet(l);
        int i = 0;
        while (v != 0) {
            if (v % 2 != 0) b.set(i);
            ++i;
            v = v >>> 1;
        }
        return b;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Environment(EnvType.CLIENT)
    public boolean isSideVisible(BlockView blockView, int x, int y, int z, int side) {
        int var6 = blockView.getBlockId(x, y, z);
        return (this.isOpaque() || var6 != this.id) && super.isSideVisible(blockView, x, y, z, side);
    }

    @Environment(EnvType.CLIENT)
    public int getRenderLayer() {
        return 0;
    }
}
