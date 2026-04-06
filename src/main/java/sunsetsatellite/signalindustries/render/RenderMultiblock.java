package sunsetsatellite.signalindustries.render;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.StationRenderAPI;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.catalyst.core.util.BlockInstance;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.mixin.interfaces.GlobalAlphaSwitch;
import sunsetsatellite.catalyst.core.util.vector.Vec3i;
import sunsetsatellite.catalyst.multiblocks.IMultiblock;
import sunsetsatellite.catalyst.multiblocks.Multiblock;
import sunsetsatellite.signalindustries.block.entity.base.FluidItemContainerBlockEntity;
import sunsetsatellite.signalindustries.util.InventoryBlockView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RenderMultiblock extends BlockEntityRenderer {

    public InventoryBlockView blockView;

    @Override
    public void render(BlockEntity blockEntity, double x, double y, double z, float tickDelta) {
        if(blockEntity instanceof IMultiblock m && blockEntity instanceof FluidItemContainerBlockEntity tile){
            World world = blockEntity.world;
            Vec3i pos = tile.getPosition();
            Direction dir = tile.getDirection().getOpposite();
            if(m.getMultiblock() == null) return;
            Multiblock multiblock = m.getMultiblock().data;
            List<BlockInstance> blocks = multiblock.getBlocks(pos, dir);
            List<BlockInstance> substitutions = multiblock.getSubstitutions(pos, dir);
            if(blockView == null) {
                blockView = new InventoryBlockView(world, blocks);
            } else {
                blockView.clear();
                blockView.init(blocks);
            }
            BlockRenderManager renderManager = new BlockRenderManager(blockView);

            for (BlockInstance block : blocks) {
                if(!block.exists(world)){
                    boolean foundSub = substitutions.stream().anyMatch((BI) -> BI.pos.equals(block.pos) && BI.exists(world));
                    if(!foundSub) {
                        GL11.glPushMatrix();
                        //GL11.glTranslated(x, y, z);
                        //GL11.glTranslatef((float) x + (block.pos.x - pos.x) + 0.5f, (float) y + (block.pos.y - pos.y) + 0.5f, (float) z + (block.pos.z - pos.z) + 0.5f);
                        //GL11.glTranslated(pos.x, pos.y, pos.z);

                        if(world.getBlockId(block.pos.x, block.pos.y, block.pos.z) != 0){
                            //GL11.glScalef(1.1f, 1.1f, 1.1f);
                            GL11.glTranslated(x - pos.x + 0.5f, y - pos.y + 0.5f, z - pos.z + 0.5f);
                            drawBlock(renderManager, block.block, block.pos, new Color(255,0,0, 230));
                        } else {
                            //GL11.glScalef(0.75f, 0.75f, 0.75f);
                            GL11.glTranslated(x - pos.x + 0.5f, y - pos.y + 0.5f, z - pos.z + 0.5f);
                            drawBlock(renderManager, block.block, block.pos, new Color(255,0,0, 191));
                        }

                        GL11.glPopMatrix();
                    }
                }
            }
        }
    }

    public void drawBlock(BlockRenderManager renderManager, Block block, Vec3i pos, Color color) {
        StationRenderAPI.getBakedModelManager().getAtlas(Atlases.GAME_ATLAS_TEXTURE).bindTexture();
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Tessellator.INSTANCE.startQuads();
        Tessellator.INSTANCE.setOffset(-0.5f,-0.5f,-0.5f);
        Tessellator.INSTANCE.color(color.getRed() / 255f,color.getBlue() / 255f,color.getGreen() / 255f,color.getAlpha() / 255f);
        ((GlobalAlphaSwitch) Tessellator.INSTANCE).setGlobalAlpha(color.getAlpha() / 255f);
        renderManager.render(block, pos.x, pos.y, pos.z);
        ((GlobalAlphaSwitch) Tessellator.INSTANCE).setGlobalAlpha(1);
        Tessellator.INSTANCE.setOffset(0,0,0);
        Tessellator.INSTANCE.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
}
