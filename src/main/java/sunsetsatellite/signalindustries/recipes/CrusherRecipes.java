package sunsetsatellite.signalindustries.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sunsetsatellite.catalyst.core.util.recipe.RecipeGroup;
import sunsetsatellite.signalindustries.data.SIBlocks;
import sunsetsatellite.signalindustries.data.SIItems;
import sunsetsatellite.signalindustries.recipes.base.MachineRecipesBase;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryCrusher;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachine;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.catalyst.core.util.recipe.RecipeSymbol;
import sunsetsatellite.signalindustries.util.Tier;

public class CrusherRecipes implements MachineRecipesBase<RecipeGroup<RecipeEntryMachine>> {
    @Override
    public void addRecipes(RecipeGroup<RecipeEntryMachine> group) {
        group.register(
                "cobble_to_gravel",
                new RecipeEntryCrusher(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(Block.COBBLESTONE))
                        },
                        new ItemStack(Block.GRAVEL,1),
                        new RecipeProperties(200,40, Tier.PROTOTYPE,false)
                )
        );
        group.register(
                "gravel_to_sand",
                new RecipeEntryCrusher(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(Block.GRAVEL,1))
                        },
                        new ItemStack(Block.SAND,1),
                        new RecipeProperties(200,40, Tier.PROTOTYPE,false)
                )
        );
        group.register(
                "prototype_raw_dust",
                new RecipeEntryCrusher(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.rawSignalumCrystal))
                        },
                        new ItemStack(SIItems.saturatedSignalumCrystalDust,1),
                        new RecipeProperties(200,40, Tier.PROTOTYPE,false)
                )
        );
        group.register(
                "prototype_coal_dust",
                new RecipeEntryCrusher(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(Item.COAL))
                        },
                        new ItemStack(SIItems.coalDust,1),
                        new RecipeProperties(200,40, Tier.PROTOTYPE,false)
                )
        );
        group.register(
                "prototype_raw_iron_dust",
                new RecipeEntryCrusher(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(Block.IRON_ORE))
                        },
                        new ItemStack(SIItems.ironDust,1),
                        new RecipeProperties(100,40, Tier.PROTOTYPE,true)
                )
        );
        group.register(
                "prototype_raw_gold_dust",
                new RecipeEntryCrusher(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(Block.GOLD_ORE))
                        },
                        new ItemStack(SIItems.goldDust,1),
                        new RecipeProperties(100,40, Tier.PROTOTYPE,true)
                )
        );
        group.register(
                "basic_raw_iron_dust",
                new RecipeEntryCrusher(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(Block.IRON_ORE))
                        },
                        new ItemStack(SIItems.ironDust,2),
                        new RecipeProperties(100,40, Tier.BASIC,false)
                )
        );
        group.register(
                "basic_raw_gold_dust",
                new RecipeEntryCrusher(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(Block.GOLD_ORE))
                        },
                        new ItemStack(SIItems.goldDust,2),
                        new RecipeProperties(100,40, Tier.BASIC,false)
                )
        );
        group.register(
                "prototype_empty_dust",
                new RecipeEntryCrusher(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.signalumCrystalEmpty))
                        },
                        new ItemStack(SIItems.emptySignalumCrystalDust,2),
                        new RecipeProperties(200,40, Tier.PROTOTYPE,true)
                )
        );
        group.register(
                "basic_empty_dust",
                new RecipeEntryCrusher(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.signalumCrystalEmpty))
                        },
                        new ItemStack(SIItems.emptySignalumCrystalDust,4),
                        new RecipeProperties(200,40, Tier.BASIC,false)
                )
        );
        group.register(
                "prototype_dust",
                new RecipeEntryCrusher(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.signalumCrystal))
                        },
                        new ItemStack(SIItems.saturatedSignalumCrystalDust,2),
                        new RecipeProperties(200,40, Tier.PROTOTYPE,true)
                )
        );
        group.register(
                "basic_dust",
                new RecipeEntryCrusher(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.signalumCrystal))
                        },
                        new ItemStack(SIItems.saturatedSignalumCrystalDust,4),
                        new RecipeProperties(200,40, Tier.BASIC,false)
                )
        );
        group.register(
                "basic_nether_coal_dust",
                new RecipeEntryCrusher(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.emberCoal))
                        },
                        new ItemStack(SIItems.emberCoalDust,1),
                        new RecipeProperties(100,80, Tier.BASIC,false)
                )
        );
        group.register(
                "basic_tiny_nether_coal_dust",
                new RecipeEntryCrusher(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.emberCoalDust))
                        },
                        new ItemStack(SIItems.tinyEmberCoalDust,9),
                        new RecipeProperties(100,80, Tier.BASIC,false)
                )
        );
        group.register(
                "string_of_reality",
                new RecipeEntryCrusher(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIBlocks.rootedFabric))
                        },
                        new ItemStack(SIItems.realityString,2),
                        new RecipeProperties(300,160, Tier.REINFORCED,false).setChance(0.25f)
                )
        );
    }
}
