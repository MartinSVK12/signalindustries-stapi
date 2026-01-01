package sunsetsatellite.signalindustries.recipes;


import net.danygames2014.nyalib.fluid.FluidStack;
import net.danygames2014.nyalib.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.recipe.RecipeGroup;
import sunsetsatellite.signalindustries.data.SIItems;
import sunsetsatellite.signalindustries.recipes.base.MachineRecipesBase;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryCrystalCutter;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachine;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.signalindustries.util.RecipeSymbol;
import sunsetsatellite.signalindustries.util.Tier;

public class CrystalCutterRecipes implements MachineRecipesBase<RecipeGroup<RecipeEntryMachine>> {
    @Override
    public void addRecipes(RecipeGroup<RecipeEntryMachine> group) {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("saturation",1000);
        nbt.putInt("size",1);
        NbtCompound nbt2 = new NbtCompound();
        nbt2.putInt("size",1);
        group.register(
                "signalum_crystal",
                new RecipeEntryCrystalCutter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new FluidStack(Fluids.WATER,1000)),
                                new RecipeSymbol(new ItemStack(SIItems.rawSignalumCrystal,8))
                        },
                        new ItemStack(SIItems.signalumCrystal,1,0),
                        new RecipeProperties(200,80,0, Tier.PROTOTYPE,true)
                )
        );
        group.register(
                "signalum_crystal_battery",
                new RecipeEntryCrystalCutter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new FluidStack(Fluids.WATER,1000)),
                                new RecipeSymbol(new ItemStack(SIItems.signalumCrystal,1))
                        },
                        Catalyst.newItemStack(SIItems.signalumCrystalBattery, 1, 0, nbt),
                        new RecipeProperties(100,80,1, Tier.PROTOTYPE,false)
                )
        );
        group.register(
                "empty_signalum_crystal",
                new RecipeEntryCrystalCutter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new FluidStack(Fluids.WATER,1000)),
                                new RecipeSymbol(new ItemStack(SIItems.rawSignalumCrystal,8))
                        },
                        Catalyst.newItemStack(SIItems.signalumCrystalEmpty,1,0,nbt2),
                        new RecipeProperties(200,80,2, Tier.PROTOTYPE,true)
                )
        );
        group.register(
                "basic_signalum_crystal",
                new RecipeEntryCrystalCutter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new FluidStack(Fluids.WATER,1000)),
                                new RecipeSymbol(new ItemStack(SIItems.rawSignalumCrystal,4))
                        },
                        Catalyst.newItemStack(SIItems.signalumCrystal,1,0,nbt),
                        new RecipeProperties(200,80,0, Tier.BASIC,false)
                )
        );
        group.register(
                "basic_empty_signalum_crystal",
                new RecipeEntryCrystalCutter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new FluidStack(Fluids.WATER,1000)),
                                new RecipeSymbol(new ItemStack(SIItems.rawSignalumCrystal,4))
                        },
                        Catalyst.newItemStack(SIItems.signalumCrystalEmpty,1,0,nbt2),
                        new RecipeProperties(200,80,2, Tier.BASIC,false)
                )
        );
        group.register(
                "volatile_signalum_crystal",
                new RecipeEntryCrystalCutter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new FluidStack(Fluids.WATER,1000)),
                                new RecipeSymbol(new ItemStack(SIItems.signalumCrystal,1))
                        },
                        new ItemStack(SIItems.volatileSignalumCrystal,4,0),
                        new RecipeProperties(200,80,3, Tier.BASIC,false)
                )
        );
        group.register(
                "crystal_chip",
                new RecipeEntryCrystalCutter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new FluidStack(Fluids.WATER,500)),
                                new RecipeSymbol(new ItemStack(SIItems.rawSignalumCrystal,2))
                        },
                        new ItemStack(SIItems.crystalChip,1,0),
                        new RecipeProperties(100,80,4, Tier.BASIC,false)
                )
        );
        group.register(
                "pure_crystal_chip",
                new RecipeEntryCrystalCutter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new FluidStack(Fluids.WATER,2000)),
                                new RecipeSymbol(new ItemStack(SIItems.signalumCrystal,1))
                        },
                        new ItemStack(SIItems.pureCrystalChip,2,0),
                        new RecipeProperties(100,80,5, Tier.REINFORCED,false)
                )
        );
        group.register(
                "dimensional_chip",
                new RecipeEntryCrystalCutter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new FluidStack(Fluids.WATER,2000)),
                                new RecipeSymbol(new ItemStack(SIItems.dimensionalShard,1))
                        },
                        new ItemStack(SIItems.dimensionalChip,2,0),
                        new RecipeProperties(200,160,6, Tier.REINFORCED,false)
                )
        );
        group.register(
                "dilithium_chip",
                new RecipeEntryCrystalCutter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new FluidStack(Fluids.WATER,2000)),
                                new RecipeSymbol(new ItemStack(SIItems.dilithiumShard,1))
                        },
                        new ItemStack(SIItems.dilithiumChip,2,0),
                        new RecipeProperties(100,160,7, Tier.REINFORCED,false)
                )
        );
        group.register(
                "signalum_alloy_mesh",
                new RecipeEntryCrystalCutter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new FluidStack(Fluids.WATER,1000)),
                                new RecipeSymbol(new ItemStack(SIItems.saturatedSignalumAlloyPlate,1))
                        },
                        new ItemStack(SIItems.signalumAlloyMesh,1,0),
                        new RecipeProperties(100,80,8, Tier.BASIC,false)
                )
        );
        group.register(
                "krowka",
                new RecipeEntryCrystalCutter(
                        new RecipeSymbol[]{
                                new RecipeSymbol(new ItemStack(SIItems.caramelPlate,1))
                        },
                        new ItemStack(SIItems.krowka,8,0),
                        new RecipeProperties(50,20,9, Tier.PROTOTYPE,false)
                )
        );
    }
}
