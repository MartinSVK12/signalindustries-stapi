package sunsetsatellite.signalindustries.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.item.Items;
import sunsetsatellite.catalyst.core.util.recipe.RecipeGroup;
import sunsetsatellite.signalindustries.data.SIBlocks;
import sunsetsatellite.signalindustries.data.SIItems;
import sunsetsatellite.signalindustries.recipes.base.MachineRecipesBase;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryCrusher;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryPlateFormer;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachine;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.signalindustries.util.RecipeSymbol;
import sunsetsatellite.signalindustries.util.Tier;

public class PlateFormerRecipes implements MachineRecipesBase<RecipeGroup<RecipeEntryMachine>> {
    @Override
    public void addRecipes(RecipeGroup<RecipeEntryMachine> group) {
        group.register(
                "stone_plate",
                new RecipeEntryPlateFormer(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(Block.STONE))
                        },
                        new ItemStack(SIItems.stonePlate,2),
                        new RecipeProperties(100,20, Tier.PROTOTYPE,false)
                )
        );
        group.register(
                "cobblestone_plate",
                new RecipeEntryPlateFormer(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(Block.COBBLESTONE))
                        },
                        new ItemStack(SIItems.cobblestonePlate,2),
                        new RecipeProperties(100,20, Tier.PROTOTYPE,false)
                )
        );
        group.register(
                "steel_plate",
                new RecipeEntryPlateFormer(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.steelIngot))
                        },
                        new ItemStack(SIItems.steelPlate,1),
                        new RecipeProperties(200,20, Tier.PROTOTYPE,false)
                )
        );
        group.register(
                "crystal_alloy_plate",
                new RecipeEntryPlateFormer(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.crystalAlloyIngot))
                        },
                        new ItemStack(SIItems.crystalAlloyPlate,1),
                        new RecipeProperties(200,20, Tier.PROTOTYPE,false)
                )
        );
        group.register(
                "caramel_plate",
                new RecipeEntryPlateFormer(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.bucketCaramel))
                        },
                        new ItemStack(SIItems.caramelPlate,1),
                        new RecipeProperties(200,20, Tier.PROTOTYPE,false)
                )
        );
        group.register(
                "saturated_alloy_plate",
                new RecipeEntryPlateFormer(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.saturatedSignalumAlloyIngot))
                        },
                        new ItemStack(SIItems.saturatedSignalumAlloyPlate,1),
                        new RecipeProperties(200,40, Tier.BASIC,false)
                )
        );
        group.register(
                "reinforced_crystal_alloy_plate",
                new RecipeEntryPlateFormer(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.reinforcedCrystalAlloyIngot))
                        },
                        new ItemStack(SIItems.reinforcedCrystalAlloyPlate,1),
                        new RecipeProperties(200,40, Tier.BASIC,false)
                )
        );
        group.register(
                "dilithium_plate",
                new RecipeEntryPlateFormer(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.dilithiumShard,2))
                        },
                        new ItemStack(SIItems.dilithiumPlate,1),
                        new RecipeProperties(200,80, Tier.REINFORCED,false)
                )
        );
        group.register(
                "void_alloy_plate",
                new RecipeEntryPlateFormer(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.voidAlloyIngot))
                        },
                        new ItemStack(SIItems.voidAlloyPlate,1),
                        new RecipeProperties(400,120, Tier.REINFORCED,false)
                )
        );
    }
}
