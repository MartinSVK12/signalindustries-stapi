package sunsetsatellite.signalindustries.recipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sunsetsatellite.catalyst.core.util.recipe.RecipeGroup;
import sunsetsatellite.signalindustries.data.SIBlocks;
import sunsetsatellite.signalindustries.data.SIItems;
import sunsetsatellite.signalindustries.recipes.base.MachineRecipesBase;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryAlloySmelter;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachine;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.signalindustries.util.RecipeSymbol;
import sunsetsatellite.signalindustries.util.Tier;

public class AlloySmelterRecipes implements MachineRecipesBase<RecipeGroup<RecipeEntryMachine>> {
    @Override
    public void addRecipes(RecipeGroup<RecipeEntryMachine> group) {
        group.register(
                "steel_ingot",
                new RecipeEntryAlloySmelter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(Item.IRON_INGOT)),
                                new RecipeSymbol(new ItemStack(SIItems.tinyEmberCoalDust))
                        },
                        new ItemStack(SIItems.steelIngot,1),
                        new RecipeProperties(200,40,Tier.PROTOTYPE,true)
                )
        );
        group.register(
                "basic_steel_ingot",
                new RecipeEntryAlloySmelter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(Item.IRON_INGOT)),
                                new RecipeSymbol(new ItemStack(SIItems.tinyEmberCoalDust))
                        },
                        new ItemStack(SIItems.steelIngot,2),
                        new RecipeProperties(200,40,Tier.BASIC,false)
                )
        );
        group.register(
                "crystal_alloy_ingot",
                new RecipeEntryAlloySmelter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.steelIngot)),
                                new RecipeSymbol(new ItemStack(SIItems.emptySignalumCrystalDust))
                        },
                        new ItemStack(SIItems.crystalAlloyIngot,1),
                        new RecipeProperties(200,40,Tier.PROTOTYPE,true)
                )
        );
        group.register(
                "crystal_alloy_ingot_2",
                new RecipeEntryAlloySmelter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.steelIngot)),
                                new RecipeSymbol(new ItemStack(SIItems.emptySignalumCrystalDust))
                        },
                        new ItemStack(SIItems.crystalAlloyIngot,2),
                        new RecipeProperties(200,40,Tier.BASIC,false)
                )
        );
        group.register(
                "reinforced_crystal_alloy_ingot",
                new RecipeEntryAlloySmelter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.crystalAlloyIngot)),
                                new RecipeSymbol(new ItemStack(SIBlocks.glowingObsidian,2))
                        },
                        new ItemStack(SIItems.reinforcedCrystalAlloyIngot,1),
                        new RecipeProperties(200,80,Tier.BASIC,false)
                )
        );
        group.register(
                "condensed_milk_can",
                new RecipeEntryAlloySmelter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(Item.MILK_BUCKET)),
                                new RecipeSymbol(new ItemStack(Item.SUGAR,8))
                        },
                        new ItemStack(SIItems.condensedMilkCan,1),
                        new RecipeProperties(100,20,Tier.PROTOTYPE,false).setConsumeContainers()
                )
        );
        group.register(
                "caramel_bucket",
                new RecipeEntryAlloySmelter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.condensedMilkCan)),
                                new RecipeSymbol(new ItemStack(Item.SUGAR,4))
                        },
                        new ItemStack(SIItems.bucketCaramel,1),
                        new RecipeProperties(100,20,Tier.PROTOTYPE,false).setConsumeContainers()
                )
        );
        group.register(
                "void_alloy_ingot",
                new RecipeEntryAlloySmelter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.reinforcedCrystalAlloyIngot,2)),
                                new RecipeSymbol(new ItemStack(SIItems.realityString,8))
                        },
                        new ItemStack(SIItems.voidAlloyIngot,1),
                        new RecipeProperties(400,120,Tier.REINFORCED,false)
                )
        );
    }
}
