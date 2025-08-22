package sunsetsatellite.signalindustries.data;

import net.danygames2014.nyalib.event.FluidRegistryEvent;
import net.danygames2014.nyalib.fluid.Fluid;
import net.mine_diver.unsafeevents.listener.EventListener;

import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class SIFluids {

    public static Fluid ENERGY;
    public static Fluid BURNT_ENERGY;
    public static Fluid WORLD_RESIN;

    @EventListener
    public void registerFluids(FluidRegistryEvent event) {
        event.register(ENERGY = new Fluid(NAMESPACE.id("energy"),SIBlocks.energyStill,SIBlocks.energyFlowing));
        event.register(BURNT_ENERGY = new Fluid(NAMESPACE.id("burnt_energy"),SIBlocks.burntSignalumStill,SIBlocks.burntSignalumFlowing));
        event.register(WORLD_RESIN = new Fluid(NAMESPACE.id("world_resin"),SIBlocks.worldResinStill,SIBlocks.worldResinFlowing));
    }

}
