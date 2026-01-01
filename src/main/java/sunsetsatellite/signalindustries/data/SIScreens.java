package sunsetsatellite.signalindustries.data;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.gui.screen.GuiHandler;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.registry.Registry;
import sunsetsatellite.signalindustries.block.entity.*;
import sunsetsatellite.signalindustries.screen.*;

import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class SIScreens {

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerScreenHandlers(GuiHandlerRegistryEvent event) {
        Registry.register(event.registry, NAMESPACE.id("open_fluid_tank"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) (p, i)-> new FluidTankScreen(p.inventory, ((FluidTankBlockEntity) i)), FluidTankBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_energy_cell"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) (p, i)-> new EnergyCellScreen(p.inventory, ((EnergyCellBlockEntity) i)), EnergyCellBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_crusher"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) (p, i)-> new CrusherScreen(p.inventory, ((CrusherBlockEntity) i)), CrusherBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_alloy_smelter"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) (p, i)-> new AlloySmelterScreen(p.inventory, ((AlloySmelterBlockEntity) i)), AlloySmelterBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_crystal_cutter"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) (p, i)-> new CrystalCutterScreen(p.inventory, ((CrystalCutterBlockEntity) i)), CrystalCutterBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_plate_former"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) (p, i)-> new PlateFormerScreen(p.inventory, ((PlateFormerBlockEntity) i)), PlateFormerBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_extractor"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) (p, i)-> new ExtractorScreen(p.inventory, ((ExtractorBlockEntity) i)), ExtractorBlockEntity::new));

    }

}
