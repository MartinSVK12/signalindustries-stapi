package sunsetsatellite.signalindustries.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.LiquidBlock;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sunsetsatellite.signalindustries.data.SIBlocks;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin extends Block {

    private LiquidBlockMixin(int id, Material material) {
        super(id, material);
    }
}
