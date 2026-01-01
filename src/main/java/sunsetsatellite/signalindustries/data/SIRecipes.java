package sunsetsatellite.signalindustries.data;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.signalindustries.recipes.*;
import sunsetsatellite.signalindustries.recipes.base.RecipeGroupSI;
import sunsetsatellite.signalindustries.recipes.base.RecipeNamespaceSI;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachine;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachineFluid;

import java.util.List;

public class SIRecipes {

    public static RecipeNamespaceSI SIGNAL_INDUSTRIES;
    public static RecipeGroupSI<RecipeEntryMachine> CRUSHER;
    public static RecipeGroupSI<RecipeEntryMachine> ALLOY_SMELTER;
    public static RecipeGroupSI<RecipeEntryMachine> PLATE_FORMER;
    public static RecipeGroupSI<RecipeEntryMachine> CRYSTAL_CUTTER;
    public static RecipeGroupSI<RecipeEntryMachineFluid> EXTRACTOR;

    @EventListener
    public void registerRecipes(RecipeRegisterEvent event) {
        RecipeRegisterEvent.Vanilla type = RecipeRegisterEvent.Vanilla.fromType(event.recipeId);

        if (type == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPELESS) {

            SIGNAL_INDUSTRIES = new RecipeNamespaceSI();

            CRUSHER = new RecipeGroupSI<>(List.of(new ItemStack(SIBlocks.prototypeCrusher)));
            ALLOY_SMELTER = new RecipeGroupSI<>(List.of(new ItemStack(SIBlocks.prototypeAlloySmelter)));
            PLATE_FORMER = new RecipeGroupSI<>(List.of(new ItemStack(SIBlocks.prototypePlateFormer)));
            CRYSTAL_CUTTER = new RecipeGroupSI<>(List.of(new ItemStack(SIBlocks.prototypeCrystalCutter)));
            EXTRACTOR = new RecipeGroupSI<>(List.of(new ItemStack(SIBlocks.prototypeExtractor)));

            new CrusherRecipes().addRecipes(CRUSHER);
            new AlloySmelterRecipes().addRecipes(ALLOY_SMELTER);
            new PlateFormerRecipes().addRecipes(PLATE_FORMER);
            new CrystalCutterRecipes().addRecipes(CRYSTAL_CUTTER);
            new ExtractorRecipes().addRecipes(EXTRACTOR);

            SIGNAL_INDUSTRIES.register("crusher",CRUSHER);
            SIGNAL_INDUSTRIES.register("alloy_smelter",ALLOY_SMELTER);
            SIGNAL_INDUSTRIES.register("plate_former",PLATE_FORMER);
            SIGNAL_INDUSTRIES.register("crystal_cutter",CRYSTAL_CUTTER);
            SIGNAL_INDUSTRIES.register("extractor",EXTRACTOR);

            Catalyst.RECIPES.register("signalindustries",SIGNAL_INDUSTRIES);
        }

        if (type == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED) {

        }

        if (type == RecipeRegisterEvent.Vanilla.SMELTING) {

        }
    }

}
