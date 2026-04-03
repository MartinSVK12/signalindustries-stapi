package sunsetsatellite.signalindustries.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.platform.Lighting;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.signalindustries.data.SIDimensions;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow
    private World world;

    @Shadow
    private int starsGlList;

    @Shadow
    private TextureManager textureManager;

    @Shadow
    private int darkSkyGlList;

    @Shadow
    private Minecraft client;

    @Shadow
    private int lightSkyGlList;

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    public void eternitySky(float tickDelta, CallbackInfo ci) {
        if (this.world.dimension == SIDimensions.ETERNITY) {
//            GL11.glPushMatrix();
//            GL11.glBindTexture(3553, this.textureManager.getTextureId("/assets/signalindustries/stationapi/textures/misc/stars.png"));
//            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//            GL11.glCallList(starsGlList);
//            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//            GL11.glDisable(GL11.GL_TEXTURE_2D);
//            GL11.glDisable(GL11.GL_BLEND);
//            GL11.glEnable(GL11.GL_ALPHA_TEST);
//            GL11.glEnable(GL11.GL_FOG);
//            GL11.glPopMatrix();
//            GL11.glColor3f(1.0F, 1.0F, 1.0F);
//
//            GL11.glEnable(GL11.GL_TEXTURE_2D);
//            GL11.glDepthMask(true);
            GL11.glDisable(3553);
            Vec3d var2 = this.world.getSkyColor(this.client.camera, tickDelta);
            float var3 = (float)var2.x;
            float var4 = (float)var2.y;
            float var5 = (float)var2.z;
            if (this.client.options.anaglyph3d) {
                float var6 = (var3 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
                float var7 = (var3 * 30.0F + var4 * 70.0F) / 100.0F;
                float var8 = (var3 * 30.0F + var5 * 70.0F) / 100.0F;
                var3 = var6;
                var4 = var7;
                var5 = var8;
            }

            GL11.glColor3f(var3, var4, var5);
            GL11.glDepthMask(false);
            GL11.glEnable(2912);
            GL11.glColor3f(var3, var4, var5);
            GL11.glCallList(this.lightSkyGlList);
            GL11.glDisable(2912);
            GL11.glDisable(3008);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            Lighting.turnOff();

            GL11.glEnable(3553);
            GL11.glBlendFunc(770, 1);
            GL11.glPushMatrix();
            float var19 = 1.0F;
            float var21 = 0.0F;
            float var22 = 0.0F;
            float var23 = 0.0F;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, var19);
            GL11.glTranslatef(var21, var22, var23);
            GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
            GL11.glDisable(3553);
            float[] starColor = new float[]{1,1,1,1};
            GL11.glColor4f(starColor[0], starColor[1], starColor[2], starColor[3]);
            GL11.glCallList(this.starsGlList);

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(3042);
            GL11.glEnable(3008);
            GL11.glEnable(2912);
            GL11.glPopMatrix();

            //ground
            GL11.glColor3f(var3 * 0.2F + 0.04F, var4 * 0.2F + 0.04F, var5 * 0.2F + 0.04F);

            GL11.glDisable(3553);
            GL11.glCallList(this.darkSkyGlList);
            GL11.glEnable(3553);
            GL11.glDepthMask(true);
            ci.cancel();
        }
    }

    @Inject(method = "renderClouds", at = @At("HEAD"), cancellable = true)
    public void renderClouds(float par1, CallbackInfo ci) {
        if (this.world.dimension == SIDimensions.ETERNITY) {
            ci.cancel();
        }
    }

}
