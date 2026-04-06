package sunsetsatellite.signalindustries.interfaces;

import net.minecraft.block.entity.BlockEntity;

public interface MultiblockPartTile {
    boolean isConnected();

    BlockEntity getConnectedTileEntity();

    boolean connect(BlockEntity tileEntity);
}
