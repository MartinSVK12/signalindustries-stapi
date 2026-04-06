package sunsetsatellite.signalindustries;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.danygames2014.nyalib.fluid.FluidStack;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkHandler;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.event.init.InitFinishedEvent;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.event.mod.PostInitEvent;
import net.modificationstation.stationapi.api.event.world.WorldPropertiesEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.nbt.NbtOps;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;
import sunsetsatellite.catalyst.multiblocks.CustomStructure;
import sunsetsatellite.catalyst.multiblocks.Multiblock;
import sunsetsatellite.catalyst.multiblocks.Structure;
import sunsetsatellite.signalindustries.block.machine.InductionSmelterBlock;
import sunsetsatellite.signalindustries.data.*;
import sunsetsatellite.signalindustries.item.BlueprintItem;
import sunsetsatellite.signalindustries.util.MeteorLocation;
import sunsetsatellite.signalindustries.util.SIMultiblock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SignalIndustries {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Entrypoint.Logger
    public static Logger LOGGER;

    public static List<MeteorLocation> meteorLocations = new ArrayList<>();
    public static HashMap<String, CustomStructure> customStructures = new HashMap<>();

    @ConfigRoot(
            value = "general",
            visibleName = "General",
            index = 0
    )
    public static final SIConfig config = new SIConfig();

    @EventListener
    public void onInit(InitEvent event) {
        LOGGER.info("Signal Industries is loading... Shine!");
    }

    @EventListener
    public void onPostInit(InitFinishedEvent event){
        SIMultiblocks.init();
        SIRecipes.registerSpecial();
    }

    @EventListener
    public void worldLoad(WorldPropertiesEvent.Load event){
        customStructures.clear();
    }

    public static void addMeteorLocation(MeteorLocation location){
        meteorLocations.add(location);
        /*if(EnvironmentHelper.isServerEnvironment()){
            NetworkHandler.sendToAllPlayers(new NetworkMessageMeteorLocationSync(location));
        }*/
    }

    public static int getEnergyBurnTime(FluidStack stack) {
        if(stack == null) {
            return 0;
        } else {
            return stack.isFluidEqual(new FluidStack(SIFluids.ENERGY)) ? 200 : 0;
        }
    }

    public static Structure getStructureFromBlueprint(ItemStack blueprint, World world) {
        if (blueprint != null && blueprint.getItem() instanceof BlueprintItem) {
            String key = blueprint.getStationNbt().getString("multiblock");
            String customKey = blueprint.getStationNbt().getString("structure");
            if (!key.isEmpty()) {
                return Multiblock.multiblocks.get(key.replace("multiblock.signalindustries.", ""));
            } else if (!customKey.isEmpty()) {
                if (SignalIndustries.customStructures.containsKey(customKey)) {
                    return SignalIndustries.customStructures.get(customKey);
                } else {
                    CustomStructure structure = new CustomStructure(SignalIndustries.NAMESPACE.toString(), customKey, world, false, false);
                    SignalIndustries.customStructures.put(customKey, structure);
                    return structure;
                }
            }
            return null;
        }
        return null;
    }

}
