package sunsetsatellite.signalindustries.data;

import net.danygames2014.nyalib.event.FluidRegistryEvent;
import net.danygames2014.nyalib.fluid.Fluid;
import net.danygames2014.nyalib.fluid.FluidBuilder;
import net.mine_diver.unsafeevents.listener.EventListener;

import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class SIFluids {

    public static Fluid ENERGY;
    public static Fluid BURNT_ENERGY;
    public static Fluid WORLD_RESIN;

    @EventListener
    public void registerFluids(FluidRegistryEvent event) {
        event.register(ENERGY = new FluidBuilder(NAMESPACE.id("energy"),NAMESPACE.id("block/signalum_energy_transparent"),NAMESPACE.id("block/signalum_energy_transparent"))
                .color(0xFF0000)
                .placeableInWorld(false)
                .disableAutomaticBucketRegistration()
                .build());
        event.register(BURNT_ENERGY = new FluidBuilder(NAMESPACE.id("burnt_energy"),NAMESPACE.id("block/burnt_signalum"),NAMESPACE.id("block/burnt_signalum"))
                .color(0x400000)
                .placeableInWorld(false)
                .disableAutomaticBucketRegistration()
                .build());
        event.register(WORLD_RESIN = new FluidBuilder(NAMESPACE.id("world_resin"),NAMESPACE.id("block/world_resin_transparent"),NAMESPACE.id("block/world_resin_transparent"))
                .color(0xFF00FF)
                .placeableInWorld(false)
                .disableAutomaticBucketRegistration()
                .build());
    }

}
