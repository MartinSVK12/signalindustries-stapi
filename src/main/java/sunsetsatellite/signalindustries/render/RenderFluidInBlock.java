package sunsetsatellite.signalindustries.render;

import net.danygames2014.nyalib.capability.CapabilityHelper;
import net.danygames2014.nyalib.capability.block.fluidhandler.FluidHandlerBlockCapability;
import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.modificationstation.stationapi.api.block.States;
import net.modificationstation.stationapi.api.client.StationRenderAPI;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.signalindustries.util.InventoryBlockView;

public class RenderFluidInBlock extends BlockEntityRenderer {

    public InventoryBlockView blockView;
    private BlockRenderManager renderManager;

    @Override
    public void render(BlockEntity blockEntity, double i, double j, double k, float tickDelta) {
        int x = blockEntity.x;
        int y = blockEntity.y;
        int z = blockEntity.z;

        FluidHandlerBlockCapability cap = CapabilityHelper.getCapability(blockEntity.world, x, y, z, FluidHandlerBlockCapability.class);
        if(cap == null) return;
        FluidStack fluid = cap.getFluid(0, null);
        if(fluid == null) return;
        int amount = fluid.amount;
        int capacity = cap.getFluidCapacity(0, null);
        float v = amount / (float) capacity;
        v = MathHelper.clamp(v, 0, 1);
        if(blockView == null) blockView = new InventoryBlockView(blockEntity.world);
        blockView.setBlockStateWithMetadata(0,0,0, fluid.fluid.getFlowingBlock().getDefaultState(),0);
        if(renderManager == null) renderManager = new BlockRenderManager(blockView);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glTranslated(i, j, k);
        GL11.glScaled(0.97f,v - 0.03f,0.97f);
        GL11.glTranslated(0.015,0.015,0.015);
        Tessellator.INSTANCE.startQuads();
        StationRenderAPI.getBakedModelManager().getAtlas(Atlases.GAME_ATLAS_TEXTURE).bindTexture();
        Tessellator.INSTANCE.color(1,1,1,0.70f);
        Tessellator.INSTANCE.disableColor();
        renderManager.renderBlock(fluid.fluid.getFlowingBlock(), 0,0,0);
        Tessellator.INSTANCE.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
}
