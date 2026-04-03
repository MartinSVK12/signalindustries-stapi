package sunsetsatellite.signalindustries.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.modificationstation.stationapi.impl.worldgen.BiomeColorsImpl;
import net.modificationstation.stationapi.impl.worldgen.FogRendererImpl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.signalindustries.data.SIDimensions;

@Mixin(FogRendererImpl.class)
public class FogRendererImplMixin {

    @Shadow
    @Final
    private static float[] FOG_COLOR;

    @Inject(method = "setupFog", at = @At("HEAD"),cancellable = true)
    private static void setupFog(Minecraft minecraft, float delta, CallbackInfo ci) {
        if(minecraft.world.dimension == SIDimensions.ETERNITY){
            Vec3d color = SIDimensions.ETERNITY.getFogColor(0,delta);
            FOG_COLOR[0] = (float) color.x - 0.1f;
            FOG_COLOR[1] = (float) color.y - 0.1f;
            FOG_COLOR[2] = (float) color.z - 0.1f;
            ci.cancel();
        }
    }

}
