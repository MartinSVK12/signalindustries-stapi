package sunsetsatellite.signalindustries.data;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.modificationstation.stationapi.api.client.gui.screen.GuiHandler;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.registry.Registry;
import sunsetsatellite.signalindustries.block.entity.*;
import sunsetsatellite.signalindustries.block.entity.base.FluidItemContainerBlockEntity;
import sunsetsatellite.signalindustries.block.entity.multiblock.InductionSmelterBlockEntity;
import sunsetsatellite.signalindustries.invs.BackpackInventory;
import sunsetsatellite.signalindustries.screen.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class SIScreens {

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerScreenHandlers(GuiHandlerRegistryEvent event) {

        Registry.register(event.registry, NAMESPACE.id("open_fluid_tank"), make(FluidTankScreen.class, FluidTankBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_energy_cell"), make(EnergyCellScreen.class, EnergyCellBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_crusher"), make(CrusherScreen.class, CrusherBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_alloy_smelter"), make(AlloySmelterScreen.class, AlloySmelterBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_crystal_cutter"), make(CrystalCutterScreen.class, CrystalCutterBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_plate_former"), make(PlateFormerScreen.class, PlateFormerBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_extractor"), make(ExtractorScreen.class, ExtractorBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_item_bus"), make(ItemBusScreen.class, ItemBusBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_fluid_hatch"), make(FluidHatchScreen.class, FluidHatchBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_energy_connector"), make(EnergyConnectorScreen.class, EnergyConnectorBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_induction_smelter"), make(MultiblockScreen.class, InductionSmelterBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_encapsulator"), make(EncapsulatorScreen.class, EncapsulatorBlockEntity::new));
        Registry.register(event.registry, NAMESPACE.id("open_backpack"),
                new GuiHandler(
                        (GuiHandler.ScreenFactoryNoMessage) (p, i) ->
                                new BackpackScreen(p.inventory, p.inventory.selectedSlot), BackpackInventory::new));
    }

    public static GuiHandler make(Class<? extends Screen> screen, GuiHandler.InventoryFactory blockEntity){
        try {
            Constructor<? extends Screen> constructor = screen.getDeclaredConstructor(PlayerInventory.class, FluidItemContainerBlockEntity.class);
            return new GuiHandler((GuiHandler.ScreenFactoryNoMessage) (p, i)-> {
                try {
                    return constructor.newInstance(p.inventory, (FluidItemContainerBlockEntity) i);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }, blockEntity);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
