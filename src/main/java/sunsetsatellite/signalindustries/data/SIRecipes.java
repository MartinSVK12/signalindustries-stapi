package sunsetsatellite.signalindustries.data;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.recipe.RecipeSymbol;
import sunsetsatellite.signalindustries.recipes.*;
import sunsetsatellite.signalindustries.recipes.base.RecipeGroupSI;
import sunsetsatellite.signalindustries.recipes.base.RecipeNamespaceSI;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachine;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachineFluid;

import java.util.Collections;
import java.util.List;

import static sunsetsatellite.signalindustries.data.SIBlocks.*;

public class SIRecipes {

    public static RecipeNamespaceSI SIGNAL_INDUSTRIES;
    public static RecipeGroupSI<RecipeEntryMachine> CRUSHER;
    public static RecipeGroupSI<RecipeEntryMachine> ALLOY_SMELTER;
    public static RecipeGroupSI<RecipeEntryMachine> PLATE_FORMER;
    public static RecipeGroupSI<RecipeEntryMachine> CRYSTAL_CUTTER;
    public static RecipeGroupSI<RecipeEntryMachineFluid> EXTRACTOR;
    public static RecipeGroupSI<RecipeEntryMachine> INDUCTION_SMELTER;

    @EventListener
    public void registerRecipes(RecipeRegisterEvent event) {
        RecipeRegisterEvent.Vanilla type = RecipeRegisterEvent.Vanilla.fromType(event.recipeId);

        if (type == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPELESS) {

            SIGNAL_INDUSTRIES = new RecipeNamespaceSI();

            CRUSHER = new RecipeGroupSI<>(List.of(new ItemStack(prototypeCrusher),new ItemStack(basicCrusher),new ItemStack(reinforcedCrusher)));
            ALLOY_SMELTER = new RecipeGroupSI<>(List.of(new ItemStack(prototypeAlloySmelter), new ItemStack(basicAlloySmelter), new ItemStack(reinforcedAlloySmelter)));
            PLATE_FORMER = new RecipeGroupSI<>(List.of(new ItemStack(prototypePlateFormer), new ItemStack(basicPlateFormer), new ItemStack(reinforcedPlateFormer)));
            CRYSTAL_CUTTER = new RecipeGroupSI<>(List.of(new ItemStack(prototypeCrystalCutter), new ItemStack(basicCrystalCutter), new ItemStack(reinforcedCrystalCutter)));
            EXTRACTOR = new RecipeGroupSI<>(List.of(new ItemStack(prototypeExtractor)));
            INDUCTION_SMELTER = new RecipeGroupSI<>(Collections.singletonList(new ItemStack(SIBlocks.basicInductionSmelter)));

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
            SIGNAL_INDUSTRIES.register("induction_smelter",INDUCTION_SMELTER);

            Catalyst.RECIPES.register("signalindustries",SIGNAL_INDUSTRIES);
        }

        if (type == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED) {

        }

        if (type == RecipeRegisterEvent.Vanilla.SMELTING) {

        }
    }

    public static void registerSpecial() {
        new InductionSmelterRecipes().addRecipes(SIRecipes.INDUCTION_SMELTER);
    }

}
