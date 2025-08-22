package sunsetsatellite.signalindustries.data;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockItemRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.template.block.TemplateFlowingLiquidBlock;
import net.modificationstation.stationapi.api.template.block.TemplateStillLiquidBlock;
import sunsetsatellite.signalindustries.block.EnergyCellBlock;
import sunsetsatellite.signalindustries.block.FluidTankBlock;
import sunsetsatellite.signalindustries.block.base.SIBlock;
import sunsetsatellite.signalindustries.block.base.TieredBlock;
import sunsetsatellite.signalindustries.block.entity.EnergyCellBlockEntity;
import sunsetsatellite.signalindustries.block.entity.FluidTankBlockEntity;
import sunsetsatellite.signalindustries.util.MachineTextures;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.HashMap;

import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class SIBlocks {

    public static HashMap<Block, MachineTextures> blockTextures = new HashMap<>();

    public static Block prototypeMachineCore;
    public static Block basicMachineCore;
    public static Block reinforcedMachineCore;
    public static Block awakenedMachineCore;

    public static Block dilithiumBlock;
    public static Block emptyCrystalBlock;
    public static Block rawCrystalBlock;
    public static Block awakenedSignalumCrystalBlock;

    public static Block signalumOre;
    public static Block dilithiumOre;
    public static Block dimensionalShardOre;

    public static Block energyStill;
    public static Block energyFlowing;
    public static Block burntSignalumFlowing;
    public static Block burntSignalumStill;
    public static Block worldResinStill;
    public static Block worldResinFlowing;

    public static Block cobblestoneBricks;
    public static Block crystalAlloyBricks;
    public static Block reinforcedCrystalAlloyBricks;

    public static Block prototypeFluidTank;

    public static Block prototypeEnergyCell;

    @EventListener
    public static void registerBlocks(BlockRegistryEvent event){
        prototypeMachineCore = simpleTieredBlock(
                "prototype_machine_block",
                "prototype.machine",
                Tier.PROTOTYPE,
                new MachineTextures()
                        .withDefaultTexture("machine_prototype")
        );
        basicMachineCore = simpleTieredBlock(
                "basic_machine_block",
                "basic.machine",
                Tier.BASIC,
                new MachineTextures()
                        .withDefaultTexture("machine_basic")
        );
        reinforcedMachineCore = simpleTieredBlock(
                "reinforced_machine_block",
                "reinforced.machine",
                Tier.REINFORCED,
                new MachineTextures()
                        .withDefaultTexture("machine_reinforced")
        );
        awakenedMachineCore = simpleTieredBlock(
                "awakened_machine_block",
                "awakened.machine",
                Tier.AWAKENED,
                new MachineTextures()
                        .withDefaultTexture("machine_awakened")
        );

        rawCrystalBlock = new SIBlock(NAMESPACE.id("raw_crystal_block"), Material.METAL)
                .setTranslationKey(NAMESPACE, "rawCrystalBlock")
                .setSoundGroup(Block.GLASS_SOUND_GROUP)
                .setHardness(1)
                .setResistance(1000);
        blockTextures.put(rawCrystalBlock, new MachineTextures().withDefaultTexture("saturated_crystal_block"));

        emptyCrystalBlock = new SIBlock(NAMESPACE.id("empty_crystal_block"), Material.METAL)
                .setTranslationKey(NAMESPACE, "emptyCrystalBlock")
                .setSoundGroup(Block.GLASS_SOUND_GROUP)
                .setHardness(1)
                .setResistance(1000);
        blockTextures.put(emptyCrystalBlock, new MachineTextures().withDefaultTexture("empty_crystal_block"));

        dilithiumBlock = new SIBlock(NAMESPACE.id("dilithium_block"), Material.METAL)
                .setTranslationKey(NAMESPACE, "dilithiumBlock")
                .setSoundGroup(Block.GLASS_SOUND_GROUP)
                .setLuminance(1)
                .setHardness(2)
                .setResistance(1000);
        blockTextures.put(dilithiumBlock, new MachineTextures().withDefaultTexture("dilithium_block"));

        awakenedSignalumCrystalBlock = new SIBlock(NAMESPACE.id("awakened_crystal_block"), Material.METAL)
                .setTranslationKey(NAMESPACE, "awakenedSignalumCrystalBlock")
                .setSoundGroup(Block.GLASS_SOUND_GROUP)
                .setLuminance(1)
                .setHardness(5)
                .setResistance(1000000);
        blockTextures.put(awakenedSignalumCrystalBlock, new MachineTextures().withDefaultTexture("awakened_crystal_block"));

        cobblestoneBricks = new SIBlock(NAMESPACE.id("cobblestone_bricks"), Material.STONE)
                .setTranslationKey(NAMESPACE, "prototype.bricks")
                .setSoundGroup(Block.STONE_SOUND_GROUP)
                .setHardness(1)
                .setResistance(3);
        blockTextures.put(cobblestoneBricks, new MachineTextures().withDefaultTexture("cobblestone_bricks"));

        crystalAlloyBricks = new SIBlock(NAMESPACE.id("crystal_alloy_bricks"), Material.STONE)
                .setTranslationKey(NAMESPACE, "basic.bricks")
                .setSoundGroup(Block.STONE_SOUND_GROUP)
                .setHardness(1)
                .setResistance(3);
        blockTextures.put(crystalAlloyBricks, new MachineTextures().withDefaultTexture("crystal_alloy_bricks"));

        reinforcedCrystalAlloyBricks = new SIBlock(NAMESPACE.id("reinforced_alloy_bricks"), Material.STONE)
                .setTranslationKey(NAMESPACE, "reinforced.bricks")
                .setSoundGroup(Block.STONE_SOUND_GROUP)
                .setHardness(1)
                .setResistance(3);
        blockTextures.put(reinforcedCrystalAlloyBricks, new MachineTextures().withDefaultTexture("reinforced_alloy_bricks"));

        prototypeFluidTank = customBlock(new FluidTankBlock(NAMESPACE.id("prototype_fluid_tank"), Material.STONE, Tier.PROTOTYPE),
                "prototype.fluidTank",
                Tier.PROTOTYPE,
                new MachineTextures()
                        .withDefaultTexture("fluid_tank_prototype")
        );

        prototypeEnergyCell = customBlock(new EnergyCellBlock(NAMESPACE.id("prototype_energy_cell"), Material.STONE, Tier.PROTOTYPE),
                "prototype.energyCell",
                Tier.PROTOTYPE,
                new MachineTextures()
                        .withDefaultTexture("cell_prototype")
        );

        energyFlowing = new TemplateFlowingLiquidBlock(NAMESPACE.id("energy_flowing"),Material.WATER).setTranslationKey(NAMESPACE, "signalumEnergy.flowing");
        energyStill = new TemplateStillLiquidBlock(NAMESPACE.id("energy_still"),Material.WATER).setTranslationKey(NAMESPACE, "signalumEnergy.still");

        burntSignalumFlowing = new TemplateFlowingLiquidBlock(NAMESPACE.id("burnt_energy_flowing"),Material.WATER).setTranslationKey(NAMESPACE, "burntSignalum.flowing");
        burntSignalumStill = new TemplateStillLiquidBlock(NAMESPACE.id("burnt_energy_still"),Material.WATER).setTranslationKey(NAMESPACE, "burntSignalum.still");

        worldResinFlowing = new TemplateFlowingLiquidBlock(NAMESPACE.id("world_resin_flowing"),Material.WATER).setTranslationKey(NAMESPACE, "worldResin.flowing");
        worldResinStill = new TemplateStillLiquidBlock(NAMESPACE.id("world_resin_still"),Material.WATER).setTranslationKey(NAMESPACE, "worldResin.still");
    }

    public static Block simpleTieredBlock(String name, String lang, Tier tier, MachineTextures textures){
        Material material = Material.STONE;
        BlockSoundGroup sound = Block.STONE_SOUND_GROUP;
        switch (tier) {
            case BASIC, REINFORCED, AWAKENED, INFINITE  -> {
                material = Material.METAL;
                sound = Block.METAL_SOUND_GROUP;
            }
        }
        Block block = new TieredBlock(NAMESPACE.id(name), material, tier).setTranslationKey(NAMESPACE, lang).setHardness(1).setResistance(3).setSoundGroup(sound);
        blockTextures.put(block, textures);
        return block;
    }

    public static Block customBlock(Block block, String lang, Tier tier, MachineTextures textures){
        Material material = Material.STONE;
        BlockSoundGroup sound = Block.STONE_SOUND_GROUP;
        switch (tier) {
            case BASIC, REINFORCED, AWAKENED, INFINITE  -> {
                material = Material.METAL;
                sound = Block.METAL_SOUND_GROUP;
            }
        }
        block.setTranslationKey(NAMESPACE, lang).setHardness(1).setResistance(3).setSoundGroup(sound);
        blockTextures.put(block, textures);
        return block;
    }

    @EventListener
    public static void registerBlockEntities(BlockEntityRegisterEvent event) {
        event.register(FluidTankBlockEntity.class, NAMESPACE.id("fluid_tank").toString());
        event.register(EnergyCellBlockEntity.class, NAMESPACE.id("energy_cell").toString());
    }

    @EventListener
    public static void registerBlockItems(BlockItemRegistryEvent event){

    }

}
