package sunsetsatellite.signalindustries.mixin;

import net.glasslauncher.mods.alwaysmoreitems.api.RarityProvider;
import net.glasslauncher.mods.alwaysmoreitems.gui.AMITooltipSystem;
import net.minecraft.item.BlockItem;
import net.modificationstation.stationapi.api.client.event.gui.screen.container.TooltipBuildEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AMITooltipSystem.class, remap = false)
public abstract class AMITooltipSystemMixin {

    @Inject(method = "yourTooltipsAreNowModified", at = @At("HEAD"))
    private static void yourTooltipsAreNowModified(TooltipBuildEvent event, CallbackInfo ci) {
        if(event.tooltip.isEmpty()) {
            return;
        }

        if (event.itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof RarityProvider rarityProvider) {
            event.tooltip.set(0, rarityProvider.getRarity(event.itemStack) + event.tooltip.get(0));
        }
    }

}
