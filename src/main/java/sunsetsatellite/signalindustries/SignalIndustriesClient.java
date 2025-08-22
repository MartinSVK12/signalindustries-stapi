package sunsetsatellite.signalindustries;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.event.mod.InitEvent;

import static sunsetsatellite.signalindustries.SignalIndustries.LOGGER;

public class SignalIndustriesClient {

    @EventListener
    public void onInit(InitEvent event) {
        LOGGER.info("Client is being initialized...");
    }


}
