package sunsetsatellite.signalindustries;

import net.danygames2014.nyalib.fluid.FluidStack;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;
import sunsetsatellite.signalindustries.data.SIFluids;

public class SignalIndustries {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Entrypoint.Logger
    public static Logger LOGGER;

    @EventListener
    public void onInit(InitEvent event) {
        LOGGER.info("Signal Industries is loading... Shine!");
    }

    public static int getEnergyBurnTime(FluidStack stack) {
        if(stack == null) {
            return 0;
        } else {
            return stack.isFluidEqual(new FluidStack(SIFluids.ENERGY)) ? 200 : 0;
        }
    }

}
