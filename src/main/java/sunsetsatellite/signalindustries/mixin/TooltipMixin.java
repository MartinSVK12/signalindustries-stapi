package sunsetsatellite.signalindustries.mixin;

import net.glasslauncher.mods.alwaysmoreitems.api.Rarity;
import net.glasslauncher.mods.alwaysmoreitems.api.RarityProvider;
import net.glasslauncher.mods.alwaysmoreitems.gui.Tooltip;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Tooltip.class)
public abstract class TooltipMixin {

    @Shadow
    protected Rarity rarity;

    @Inject(method = "setTooltip(Lnet/minecraft/item/ItemStack;II)V", at = @At(value = "INVOKE", target = "Lnet/glasslauncher/mods/alwaysmoreitems/gui/Tooltip;commonInit()V", shift = At.Shift.BEFORE))
    public void setTooltip(ItemStack itemStack, int cursorX, int cursorY, CallbackInfo ci) {
        if (itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof RarityProvider rarityProvider) {
            this.rarity = rarityProvider.getRarity(itemStack);
        }
    }

}
