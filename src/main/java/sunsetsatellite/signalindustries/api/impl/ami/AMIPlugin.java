package sunsetsatellite.signalindustries.api.impl.ami;

import net.glasslauncher.mods.alwaysmoreitems.api.*;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.util.Identifier;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.CatalystMultipart;

import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class AMIPlugin implements ModPluginProvider {
    @Override
    public String getName() {
        return "SignalIndustries";
    }

    @Override
    public Identifier getId() {
        return NAMESPACE.id("si");
    }

    @Override
    public void onAMIHelpersAvailable(AMIHelpers amiHelpers) {

    }

    @Override
    public void onItemRegistryAvailable(ItemRegistry itemRegistry) {

    }

    @Override
    public void register(ModRegistry modRegistry) {

    }

    @Override
    public void onRecipeRegistryAvailable(RecipeRegistry recipeRegistry) {

    }

    @Override
    public SyncableRecipe deserializeRecipe(NbtCompound nbtCompound) {
        return null;
    }

    @Override
    public void updateBlacklist(AMIHelpers amiHelpers) {

    }
}
