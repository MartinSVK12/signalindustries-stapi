package sunsetsatellite.signalindustries.data;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.signalindustries.recipes.CrusherRecipes;
import sunsetsatellite.signalindustries.recipes.base.RecipeGroupSI;
import sunsetsatellite.signalindustries.recipes.base.RecipeNamespaceSI;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryMachine;

import java.util.List;

public class SIRecipes {

    public static RecipeNamespaceSI SIGNAL_INDUSTRIES;
    public static RecipeGroupSI<RecipeEntryMachine> CRUSHER;

    @EventListener
    public void registerRecipes(RecipeRegisterEvent event) {
        RecipeRegisterEvent.Vanilla type = RecipeRegisterEvent.Vanilla.fromType(event.recipeId);

        if (type == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPELESS) {

            SIGNAL_INDUSTRIES = new RecipeNamespaceSI();

            CRUSHER = new RecipeGroupSI<>(List.of(new ItemStack(SIBlocks.prototypeCrusher)));

            new CrusherRecipes().addRecipes(CRUSHER);

            SIGNAL_INDUSTRIES.register("crusher",CRUSHER);
            Catalyst.RECIPES.register("signalindustries",SIGNAL_INDUSTRIES);
        }

        if (type == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED) {

        }

        if (type == RecipeRegisterEvent.Vanilla.SMELTING) {

        }
    }

}
