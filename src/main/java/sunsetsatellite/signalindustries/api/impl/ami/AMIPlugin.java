package sunsetsatellite.signalindustries.api.impl.ami;

import net.glasslauncher.mods.alwaysmoreitems.api.*;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.util.Identifier;
import sunsetsatellite.signalindustries.api.impl.ami.category.*;
import sunsetsatellite.signalindustries.api.impl.ami.handler.*;
import sunsetsatellite.signalindustries.data.SIRecipes;

import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class AMIPlugin implements ModPluginProvider {
    @Override
    public String getName() {
        return "Signal Industries";
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
        modRegistry.addRecipeHandlers(new CrusherRecipeHandler());
        modRegistry.addRecipeHandlers(new AlloySmelterRecipeHandler());
        modRegistry.addRecipeHandlers(new PlateFormerRecipeHandler());
        modRegistry.addRecipeHandlers(new CrystalCutterRecipeHandler());
        modRegistry.addRecipeHandlers(new ExtractorRecipeHandler());
        modRegistry.addRecipeCategories(new CrusherRecipeCategory());
        modRegistry.addRecipeCategories(new AlloySmelterRecipeCategory());
        modRegistry.addRecipeCategories(new PlateFormerRecipeCategory());
        modRegistry.addRecipeCategories(new CrystalCutterRecipeCategory());
        modRegistry.addRecipeCategories(new ExtractorRecipeCategory());
        modRegistry.addRecipes(SIRecipes.CRUSHER.getAllRecipes());
        modRegistry.addRecipes(SIRecipes.ALLOY_SMELTER.getAllRecipes());
        modRegistry.addRecipes(SIRecipes.PLATE_FORMER.getAllRecipes());
        modRegistry.addRecipes(SIRecipes.CRYSTAL_CUTTER.getAllRecipes());
        modRegistry.addRecipes(SIRecipes.EXTRACTOR.getAllRecipes());
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
