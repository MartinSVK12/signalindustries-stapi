package sunsetsatellite.signalindustries;

import net.danygames2014.nyalib.fluid.FluidStack;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.network.NetworkHandler;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;
import sunsetsatellite.signalindustries.data.SIConfig;
import sunsetsatellite.signalindustries.data.SIFluids;
import sunsetsatellite.signalindustries.util.MeteorLocation;

import java.util.ArrayList;
import java.util.List;

public class SignalIndustries {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Entrypoint.Logger
    public static Logger LOGGER;

    public static List<MeteorLocation> meteorLocations = new ArrayList<>();

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

}
