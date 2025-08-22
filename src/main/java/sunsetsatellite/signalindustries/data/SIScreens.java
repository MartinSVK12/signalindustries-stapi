package sunsetsatellite.signalindustries.data;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.gui.screen.GuiHandler;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.registry.Registry;
import sunsetsatellite.signalindustries.block.FluidTankBlock;
import sunsetsatellite.signalindustries.block.entity.CrusherBlockEntity;
import sunsetsatellite.signalindustries.block.entity.EnergyCellBlockEntity;
import sunsetsatellite.signalindustries.block.entity.FluidTankBlockEntity;
import sunsetsatellite.signalindustries.screen.CrusherScreen;
import sunsetsatellite.signalindustries.screen.EnergyCellScreen;
import sunsetsatellite.signalindustries.screen.FluidTankScreen;

import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class SIScreens {

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerScreenHandlers(GuiHandlerRegistryEvent event) {
        Registry.register(event.registry, NAMESPACE.id("open_fluid_tank"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) (p, i)-> new FluidTankScreen(p.inventory, ((FluidTankBlockEntity) i)), FluidTankBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_energy_cell"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) (p, i)-> new EnergyCellScreen(p.inventory, ((EnergyCellBlockEntity) i)), EnergyCellBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_crusher"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) (p, i)-> new CrusherScreen(p.inventory, ((CrusherBlockEntity) i)), CrusherBlockEntity::new));

    }

}
