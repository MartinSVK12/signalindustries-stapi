package sunsetsatellite.signalindustries.interfaces;

import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import sunsetsatellite.signalindustries.util.Tier;

public interface Tiered extends CustomTooltipProvider {

    Tier getTier();
}
