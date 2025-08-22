package sunsetsatellite.signalindustries.render;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.modificationstation.stationapi.api.block.States;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.signalindustries.util.InventoryBlockView;

public class RenderFluidInBlock extends BlockEntityRenderer {
    @Override
    public void render(BlockEntity blockEntity, double i, double j, double k, float tickDelta) {
        int x = (int) i;
        int y = (int) j;
        int z = (int) k;
        InventoryBlockView blockView = new InventoryBlockView(blockEntity.world);
        blockView.setBlockStateWithMetadata(x,y,z, Block.FLOWING_WATER.getDefaultState(),0);
        BlockRenderManager blockRenderManager = new BlockRenderManager(blockView);

        GL11.glPushMatrix();
        GL11.glTranslated(i, j, k);
        blockRenderManager.renderBlock(Block.FLOWING_WATER, x,y,z);
        GL11.glPopMatrix();
    }
}
