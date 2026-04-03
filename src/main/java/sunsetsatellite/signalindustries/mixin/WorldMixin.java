package sunsetsatellite.signalindustries.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sunsetsatellite.signalindustries.data.SIDimensions;

@Mixin(World.class)
public class WorldMixin {
    @Shadow
    @Final
    public Dimension dimension;

    @Environment(EnvType.CLIENT)
    @Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
    public void getSkyColor(Entity partialTicks, float par2, CallbackInfoReturnable<Vec3d> cir) {
        if(dimension == SIDimensions.ETERNITY){
            cir.setReturnValue(Vec3d.create(0.7D, 0.7D, 0.7D));
        }
    }
}
