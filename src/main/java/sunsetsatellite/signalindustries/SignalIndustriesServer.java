package sunsetsatellite.signalindustries;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.mod.InitEvent;

import static sunsetsatellite.signalindustries.SignalIndustries.LOGGER;

public class SignalIndustriesServer {

    @EventListener
    public void onInit(InitEvent event) {
        LOGGER.info("Server is being initialized...");
    }

}
