package sunsetsatellite.signalindustries.data;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;

public class SIConfig {
    @ConfigEntry(
            name = "Iron Meteor Chance",
            description = "Bigger number = more rare",
            minValue = 1L,
            maxValue = Integer.MAX_VALUE
    )
    public Integer ironMeteorChance = 256;
    @ConfigEntry(
            name = "Signalite Meteor Chance",
            description = "Bigger number = more rare",
            minValue = 1L,
            maxValue = Integer.MAX_VALUE
    )
    public Integer signaliteMeteorChance = 512;
    @ConfigEntry(
            name = "Dilithium Meteor Chance",
            description = "Bigger number = more rare",
            minValue = 1L,
            maxValue = Integer.MAX_VALUE
    )
    public Integer dilithiumMeteorChance = 1024;

}
