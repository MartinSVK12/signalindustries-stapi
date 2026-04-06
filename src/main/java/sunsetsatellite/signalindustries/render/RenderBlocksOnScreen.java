package sunsetsatellite.signalindustries.render;

import net.minecraft.block.Block;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.StationRenderAPI;
import net.modificationstation.stationapi.api.client.render.RendererAccess;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.catalyst.core.util.BlockInstance;
import sunsetsatellite.catalyst.core.util.mixin.interfaces.GlobalAlphaSwitch;
import sunsetsatellite.catalyst.core.util.vector.Vec3i;
import sunsetsatellite.signalindustries.util.InventoryBlockView;

import java.util.List;
import java.util.Random;

public class RenderBlocksOnScreen {

    public InventoryBlockView blockView;

    public void drawBlock(List<BlockInstance> blocks, Tessellator tessellator, World world, double i, double j, double k, float alpha) {
        if(blockView == null) {
            blockView = new InventoryBlockView(world, blocks);
        } else {
            blockView.clear();
            blockView.init(blocks);
        }

        //if(renderManager == null)
        BlockRenderManager renderManager = new BlockRenderManager(blockView);
        //renderManager.skipFaceCulling = true;
        for (BlockInstance block : blocks) {
            GL11.glPushMatrix();
            GL11.glTranslated(i, j, k);
            //GL11.glTranslated(i + block.pos.x, j + block.pos.y, k + block.pos.z);
            drawBlock(tessellator, renderManager, block.block, block.pos, alpha);
            if(block.tile != null){
                BlockEntityRenderDispatcher dispatcher = BlockEntityRenderDispatcher.INSTANCE;
                GL11.glColor4f(1,1,1,alpha);
                GL11.glTranslated(-0.5f,-0.5f,-0.5f);
                ((GlobalAlphaSwitch) Tessellator.INSTANCE).setGlobalAlpha(alpha);
                dispatcher.render(block.tile, block.pos.x, block.pos.y, block.pos.z, 0.0F);
                ((GlobalAlphaSwitch) Tessellator.INSTANCE).setGlobalAlpha(1f);
            }
            GL11.glPopMatrix();
        }
    }

    public void drawBlock(Tessellator tessellator, BlockRenderManager renderManager, Block block, Vec3i pos, float alpha) {
        StationRenderAPI.getBakedModelManager().getAtlas(Atlases.GAME_ATLAS_TEXTURE).bindTexture();
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Tessellator.INSTANCE.startQuads();
        Tessellator.INSTANCE.setOffset(-0.5f,-0.5f,-0.5f);
        Tessellator.INSTANCE.color(1,1,1,alpha);
        ((GlobalAlphaSwitch) Tessellator.INSTANCE).setGlobalAlpha(alpha);
        renderManager.render(block, pos.x, pos.y, pos.z);
        ((GlobalAlphaSwitch) Tessellator.INSTANCE).setGlobalAlpha(1);
        Tessellator.INSTANCE.setOffset(0,0,0);
        Tessellator.INSTANCE.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

}
