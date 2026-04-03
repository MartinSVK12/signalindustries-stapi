package sunsetsatellite.signalindustries.data;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.registry.DimensionRegistryEvent;
import net.modificationstation.stationapi.api.registry.DimensionContainer;
import sunsetsatellite.signalindustries.dim.EternityDimension;

import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class SIDimensions {

    public static EternityDimension ETERNITY;

    @EventListener
    public void registerDimensions(DimensionRegistryEvent event) {
        event.registry.register(NAMESPACE.id("eternity"), new DimensionContainer<>(EternityDimension::new));
    }

}
