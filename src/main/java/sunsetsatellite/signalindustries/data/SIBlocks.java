package sunsetsatellite.signalindustries.data;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.modificationstation.stationapi.api.client.event.block.entity.BlockEntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockItemRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.template.block.TemplateNetherPortalBlock;
import sunsetsatellite.signalindustries.block.*;
import sunsetsatellite.signalindustries.block.base.ConnectedTextureBlock;
import sunsetsatellite.signalindustries.block.base.SIBlock;
import sunsetsatellite.signalindustries.block.base.TieredBlock;
import sunsetsatellite.signalindustries.block.entity.*;
import sunsetsatellite.signalindustries.block.entity.multiblock.InductionSmelterBlockEntity;
import sunsetsatellite.signalindustries.block.machine.*;
import sunsetsatellite.signalindustries.block.ore.DilithiumOreBlock;
import sunsetsatellite.signalindustries.block.ore.DimensionalShardOreBlock;
import sunsetsatellite.signalindustries.block.ore.MeteoriteIronOreBlock;
import sunsetsatellite.signalindustries.block.ore.SignaliteOreBlock;
import sunsetsatellite.signalindustries.render.RenderEncapsulator;
import sunsetsatellite.signalindustries.render.RenderFluidInBlock;
import sunsetsatellite.signalindustries.render.RenderMultiblock;
import sunsetsatellite.signalindustries.util.MachineTextures;
import sunsetsatellite.signalindustries.util.MultiblockPart;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.ArrayList;
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

    public static Block meteorite;
    public static Block meteoriteIronOre;
    public static Block signalumOre;
    public static Block dilithiumOre;
    public static Block dimensionalShardOre;

    public static Block cobblestoneBricks;
    public static Block crystalAlloyBricks;
    public static Block reinforcedCrystalAlloyBricks;

    public static Block signalumAlloyCoil;
    public static Block dilithiumCoil;
    public static Block awakenedAlloyCoil;

    public static Block glowingObsidian;
    public static Block realityFabric;
    public static Block rootedFabric;
    public static Block dilithiumCrystalBlock;
    public static Block ashenTreeLog;

    public static Block basicCasing;
    public static Block reinforcedCasing;
    public static ConnectedTextureBlock reinforcedCasing2;
    public static Block reinforcedGrate;
    public static Block reinforcedFrame;
    public static ConnectedTextureBlock awakenedCasing;
    public static ConnectedTextureBlock awakenedSocketCasing;
    public static ConnectedTextureBlock awakenedCasing2;
    public static ConnectedTextureBlock basicCasing2;
    public static ConnectedTextureBlock reinforcedGlass;

    public static Block prototypeFluidTank;

    public static Block prototypeEnergyCell;

    public static Block prototypeCrusher;
    public static Block basicCrusher;
    public static Block reinforcedCrusher;

    public static Block prototypeAlloySmelter;
    public static Block basicAlloySmelter;
    public static Block reinforcedAlloySmelter;

    public static Block prototypePlateFormer;
    public static Block basicPlateFormer;
    public static Block reinforcedPlateFormer;

    public static Block prototypeCrystalCutter;
    public static Block basicCrystalCutter;
    public static Block reinforcedCrystalCutter;

    public static Block prototypeExtractor;
    public static Block basicExtractor;

    public static Block basicFluidInputHatch;
    public static Block basicFluidOutputHatch;
    public static Block basicItemInputBus;
    public static Block basicItemOutputBus;

    public static Block reinforcedFluidInputHatch;
    public static Block reinforcedFluidOutputHatch;
    public static Block reinforcedItemInputBus;
    public static Block reinforcedItemOutputBus;

    public static Block basicEnergyConnector;
    public static Block awakenedEnergyConnector;
    public static Block reinforcedEnergyConnector;

    public static Block basicInductionSmelter;

    public static Block basicMarker;
    public static Block reinforcedBuilder;
    public static Block spatialEncapsulator;

    public static Block eternityPortal;

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

        rawCrystalBlock = simpleBlock("raw_crystal_block", "rawCrystalBlock",
                new MachineTextures()
                        .withDefaultTexture("saturated_crystal_block"),
                1, 1000, Material.METAL, Block.GLASS_SOUND_GROUP);

        emptyCrystalBlock = simpleBlock("empty_crystal_block", "emptyCrystalBlock",
                new MachineTextures()
                        .withDefaultTexture("empty_crystal_block"),
                1, 1000, Material.METAL, Block.GLASS_SOUND_GROUP);

        dilithiumBlock = simpleBlock("dilithium_block", "dilithiumBlock",
                new MachineTextures()
                        .withDefaultTexture("dilithium_block"),
                2, 1000, Material.METAL, Block.GLASS_SOUND_GROUP);

        awakenedSignalumCrystalBlock = simpleBlock("awakened_crystal_block", "awakenedSignalumCrystalBlock",
                new MachineTextures()
                        .withDefaultTexture("awakened_crystal_block"),
                5, 1000000, Material.METAL, Block.GLASS_SOUND_GROUP);

        cobblestoneBricks = simpleBlock("cobblestone_bricks", "prototype.bricks",
                new MachineTextures()
                        .withDefaultTexture("cobblestone_bricks"),
                1, 3, Material.STONE, Block.STONE_SOUND_GROUP);

        crystalAlloyBricks = simpleBlock("crystal_alloy_bricks", "basic.bricks",
                new MachineTextures()
                        .withDefaultTexture("crystal_alloy_bricks"),
                1, 3, Material.STONE, Block.STONE_SOUND_GROUP);

        reinforcedCrystalAlloyBricks = simpleBlock("reinforced_alloy_bricks", "reinforced.bricks",
                new MachineTextures()
                        .withDefaultTexture("reinforced_alloy_bricks"),
                1, 3, Material.STONE, Block.STONE_SOUND_GROUP);

        signalumAlloyCoil = simpleBlock(new CoilBlock("signalite_alloy_coil", Material.METAL), "signalumAlloyCoil", Block.METAL_SOUND_GROUP,
                new MachineTextures()
                        .withDefaultSouthTexture("signalum_alloy_coil_top")
                        .withDefaultNorthTexture("signalum_alloy_coil_top")
                        .withDefaultEastTexture("signalum_alloy_coil_2")
                        .withDefaultWestTexture("signalum_alloy_coil_2")
                        .withDefaultTopBottomTextures("signalum_alloy_coil"));

        dilithiumCoil = simpleBlock(new CoilBlock("dilithium_coil", Material.METAL), "dilithiumCoil", Block.METAL_SOUND_GROUP,
                new MachineTextures()
                        .withDefaultNorthTexture("dilithium_coil_top")
                        .withDefaultSouthTexture("dilithium_coil_top")
                        .withDefaultEastTexture("dilithium_coil")
                        .withDefaultWestTexture("dilithium_coil")
                        .withDefaultTopBottomTextures("dilithium_coil")
        );

        awakenedAlloyCoil = simpleBlock(new CoilBlock("awakened_alloy_coil", Material.METAL), "awakenedAlloyCoil", Block.METAL_SOUND_GROUP,
                new MachineTextures()
                        .withDefaultSouthTexture("awakened_alloy_coil_top")
                        .withDefaultNorthTexture("awakened_alloy_coil_top")
                        .withDefaultEastTexture("awakened_alloy_coil_2")
                        .withDefaultTopBottomTextures("awakened_alloy_coil")
                        .withDefaultWestTexture("awakened_alloy_coil_2")
                );

        basicMarker = simpleBlock("basic_marker", "basic.marker",
                new MachineTextures()
                        .withDefaultTexture("basic_marker"),
                1, 3, Material.METAL, Block.METAL_SOUND_GROUP);

        glowingObsidian = simpleBlock("glowing_obsidian", "glowingObsidian",
                new MachineTextures()
                        .withDefaultTexture("glowing_obsidian"),
                1, 3, Material.STONE, Block.STONE_SOUND_GROUP);

        realityFabric = simpleBlock("reality_fabric", "realityFabric",
                new MachineTextures()
                        .withDefaultTexture("reality_fabric"),
                150, 50000, Material.STONE, Block.STONE_SOUND_GROUP);

        rootedFabric = simpleBlock("rooted_fabric", "rootedFabric",
                new MachineTextures()
                        .withDefaultTexture("rooted_fabric"),
                50, 50000, Material.STONE, Block.STONE_SOUND_GROUP);

        dilithiumCrystalBlock = basicBlock(new DilithiumCrystalBlock("dilithium_crystal_block"), "dilithiumCrystalBlock",
                new MachineTextures()
                        .withDefaultTexture("dilithium_crystal_block")
        ).setLuminance(1).setHardness(20).setResistance(1000).setSoundGroup(Block.GLASS_SOUND_GROUP);

        ashenTreeLog = basicBlock(new AshenTreeLogBlock("ashen_tree_log"),"ashenTreeLog",new MachineTextures()
                .withDefaultTexture("eternal_tree_log")
                .withDefaultTopBottomTextures("eternal_tree_log_top_empty")
                .withActiveTexture("eternal_tree_log")
                .withActiveTopBottomTextures("eternal_tree_log_top")
        );

        meteorite = simpleBlock("meteorite", "meteorite",
                new MachineTextures()
                        .withDefaultTexture("meteorite"),
                1.5F, 10.0F, Material.STONE, Block.STONE_SOUND_GROUP);

        meteoriteIronOre = simpleBlock(new MeteoriteIronOreBlock("meteorite_iron_ore"), "meteoriteIronOre",
                new MachineTextures()
                        .withDefaultTexture("meteorite_iron_ore"));

        signalumOre = simpleBlock(new SignaliteOreBlock("signalite_ore"), "signalumOre",
                new MachineTextures()
                        .withDefaultTexture("signalum_ore").withOverbrightTextures("signalum_ore_overlay"));

        dilithiumOre = simpleBlock(new DilithiumOreBlock("dilithium_ore"), "dilithiumOre",
                new MachineTextures()
                        .withDefaultTexture("dilithium_ore"));

        dimensionalShardOre = simpleBlock(new DimensionalShardOreBlock("dimensional_shard_ore"), "dimensionalShardOre",
                new MachineTextures()
                        .withDefaultTexture("dimensional_shard_ore"));

        prototypeFluidTank = customBlock(new FluidTankBlock("prototype_fluid_tank", Material.STONE, Tier.PROTOTYPE),
                "prototype.fluidTank",
                Tier.PROTOTYPE,
                new MachineTextures()
                        .withDefaultTexture("fluid_tank_prototype")
        );

        prototypeEnergyCell = customBlock(new EnergyCellBlock("prototype_energy_cell", Material.STONE, Tier.PROTOTYPE),
                "prototype.energyCell",
                Tier.PROTOTYPE,
                new MachineTextures()
                        .withDefaultTexture("cell_prototype")
        );

        prototypeCrusher = customBlock(new CrusherBlock("prototype_crusher", Material.STONE, Tier.PROTOTYPE),
                "prototype.crusher",
                Tier.PROTOTYPE,
                new MachineTextures(Tier.PROTOTYPE)
                        .withDefaultTopTexture("crusher_prototype_top_inactive")
                        .withDefaultNorthTexture("crusher_prototype_side")
                        .withActiveTopTexture("crusher_prototype_top_active")
                        .withActiveNorthTexture("crusher_prototype_side")
                        .withOverbrightTopTexture("crusher_overlay")
        );

        basicCrusher = customBlock(new CrusherBlock("basic_crusher", Material.STONE, Tier.BASIC),
                "basic.crusher",
                Tier.BASIC,
                new MachineTextures(Tier.BASIC)
                        .withDefaultTopTexture("crusher_basic_top_inactive")
                        .withDefaultNorthTexture("crusher_basic_side")
                        .withActiveTopTexture("crusher_basic_top_active")
                        .withActiveNorthTexture("crusher_basic_side")
                        .withOverbrightTopTexture("crusher_overlay")
        );

        reinforcedCrusher = customBlock(new CrusherBlock("reinforced_crusher", Material.STONE, Tier.REINFORCED),
                "reinforced.crusher",
                Tier.REINFORCED,
                new MachineTextures(Tier.REINFORCED)
                        .withDefaultTopTexture("crusher_reinforced_top_inactive")
                        .withDefaultNorthTexture("crusher_reinforced_side")
                        .withActiveTopTexture("crusher_reinforced_top_active")
                        .withActiveNorthTexture("crusher_reinforced_side")
                        .withOverbrightTopTexture("crusher_overlay")
        );

        prototypeAlloySmelter = customBlock(new AlloySmelterBlock("prototype_alloy_smelter", Material.STONE, Tier.PROTOTYPE),
                "prototype.alloySmelter",
                Tier.PROTOTYPE,
                new MachineTextures(Tier.PROTOTYPE)
                        .withDefaultNorthTexture("alloy_smelter_prototype_inactive")
                        .withActiveNorthTexture("alloy_smelter_prototype_active")
                        .withOverbrightNorthTexture("alloy_smelter_overlay")
        );

        basicAlloySmelter = customBlock(new AlloySmelterBlock("basic_alloy_smelter", Material.METAL, Tier.BASIC),
                "basic.alloySmelter",
                Tier.BASIC,
                new MachineTextures(Tier.BASIC)
                        .withDefaultNorthTexture("alloy_smelter_basic_inactive")
                        .withActiveNorthTexture("alloy_smelter_basic_active")
                        .withOverbrightNorthTexture("alloy_smelter_overlay")
        );

        reinforcedAlloySmelter = customBlock(new AlloySmelterBlock("reinforced_alloy_smelter", Material.METAL, Tier.REINFORCED),
                "reinforced.alloySmelter",
                Tier.REINFORCED,
                new MachineTextures(Tier.REINFORCED)
                        .withDefaultNorthTexture("alloy_smelter_reinforced_inactive")
                        .withActiveNorthTexture("alloy_smelter_reinforced_active")
                        .withOverbrightNorthTexture("alloy_smelter_reinforced_overlay")
        );

        prototypePlateFormer = customBlock(new PlateFormerBlock("prototype_plate_former", Material.STONE, Tier.PROTOTYPE),
                "prototype.plateFormer",
                Tier.PROTOTYPE,
                new MachineTextures(Tier.PROTOTYPE)
                        .withDefaultNorthTexture("plate_former_prototype_inactive")
                        .withActiveNorthTexture("plate_former_prototype_active")
                        .withOverbrightNorthTexture("plate_former_overlay")
        );

        basicPlateFormer = customBlock(new PlateFormerBlock("basic_plate_former", Material.METAL, Tier.BASIC),
                "basic.plateFormer",
                Tier.BASIC,
                new MachineTextures(Tier.BASIC)
                        .withDefaultNorthTexture("plate_former_basic_inactive")
                        .withActiveNorthTexture("plate_former_basic_active")
                        .withOverbrightNorthTexture("plate_former_overlay")
        );

        reinforcedPlateFormer = customBlock(new PlateFormerBlock("reinforced_plate_former", Material.METAL, Tier.REINFORCED),
                "reinforced.plateFormer",
                Tier.REINFORCED,
                new MachineTextures(Tier.REINFORCED)
                        .withDefaultNorthTexture("plate_former_reinforced_inactive")
                        .withActiveNorthTexture("plate_former_reinforced_active")
                        .withOverbrightNorthTexture("plate_former_overlay")
        );

        prototypeCrystalCutter = customBlock(new CrystalCutterBlock("prototype_crystal_cutter", Material.STONE, Tier.PROTOTYPE),
                "prototype.crystalCutter",
                Tier.PROTOTYPE,
                new MachineTextures(Tier.PROTOTYPE)
                        .withDefaultNorthTexture("crystal_cutter_prototype_inactive")
                        .withActiveNorthTexture("crystal_cutter_prototype_active")
                        .withOverbrightNorthTexture("cutter_overlay")
        );

        basicCrystalCutter = customBlock(new CrystalCutterBlock("basic_crystal_cutter", Material.METAL, Tier.BASIC),
                "basic.crystalCutter",
                Tier.BASIC,
                new MachineTextures(Tier.BASIC)
                        .withDefaultNorthTexture("crystal_cutter_basic_inactive")
                        .withActiveNorthTexture("crystal_cutter_basic_active")
                        .withOverbrightNorthTexture("cutter_overlay")
        );

        reinforcedCrystalCutter = customBlock(new CrystalCutterBlock("reinforced_crystal_cutter", Material.METAL, Tier.REINFORCED),
                "reinforced.crystalCutter",
                Tier.REINFORCED,
                new MachineTextures(Tier.REINFORCED)
                        .withDefaultNorthTexture("crystal_cutter_reinforced_inactive")
                        .withActiveNorthTexture("crystal_cutter_reinforced_active")
                        .withOverbrightNorthTexture("reinforced_cutter_overlay")
        );

        prototypeExtractor = customBlock(new ExtractorBlock("prototype_extractor", Material.STONE, Tier.PROTOTYPE),
                "prototype.extractor",
                Tier.PROTOTYPE,
                new MachineTextures(Tier.PROTOTYPE)
                        .withDefaultSideTextures("extractor_prototype_side_empty")
                        .withActiveSideTextures("extractor_prototype_side_active")
                        .withOverbrightSideTextures("extractor_overlay")
        );

        basicItemInputBus = customBlock(new ItemBusBlock("basic_item_input_bus", Material.METAL, Tier.BASIC, MultiblockPart.Type.ITEM, MultiblockPart.IO.INPUT),
                "basic.itemInputBus",
                Tier.BASIC,
                new MachineTextures(Tier.BASIC)
                        .withDefaultTexture("basic_input_bus")
        );

        basicItemOutputBus = customBlock(new ItemBusBlock("basic_item_output_bus", Material.METAL, Tier.BASIC, MultiblockPart.Type.ITEM, MultiblockPart.IO.OUTPUT),
                "basic.itemOutputBus",
                Tier.BASIC,
                new MachineTextures(Tier.BASIC)
                        .withDefaultTexture("basic_output_bus")
        );


        basicFluidInputHatch = customBlock(new FluidHatchBlock("basic_fluid_input_hatch", Material.METAL, Tier.BASIC, MultiblockPart.Type.FLUID, MultiblockPart.IO.INPUT),
                "basic.fluidInputHatch",
                Tier.BASIC,
                new MachineTextures(Tier.BASIC)
                        .withDefaultTexture("basic_fluid_input_hatch")
        );

        basicFluidOutputHatch = customBlock(new FluidHatchBlock("basic_fluid_output_hatch", Material.METAL, Tier.BASIC, MultiblockPart.Type.FLUID, MultiblockPart.IO.OUTPUT),
                "basic.fluidOutputHatch",
                Tier.BASIC,
                new MachineTextures(Tier.BASIC)
                        .withDefaultTexture("basic_fluid_output_hatch")
        );

        reinforcedItemInputBus = customBlock(new ItemBusBlock("reinforced_item_input_bus", Material.METAL, Tier.REINFORCED, MultiblockPart.Type.ITEM, MultiblockPart.IO.INPUT),
                "reinforced.itemInputBus",
                Tier.REINFORCED,
                new MachineTextures(Tier.REINFORCED)
                        .withDefaultTexture("reinforced_input_bus")
        );

        reinforcedItemOutputBus = customBlock(new ItemBusBlock("reinforced_item_output_bus", Material.METAL, Tier.REINFORCED, MultiblockPart.Type.ITEM, MultiblockPart.IO.OUTPUT),
                "reinforced.itemOutputBus",
                Tier.REINFORCED,
                new MachineTextures(Tier.REINFORCED)
                        .withDefaultTexture("reinforced_output_bus")
        );


        reinforcedFluidInputHatch = customBlock(new FluidHatchBlock("reinforced_fluid_input_hatch", Material.METAL, Tier.REINFORCED, MultiblockPart.Type.FLUID, MultiblockPart.IO.INPUT),
                "reinforced.fluidInputHatch",
                Tier.REINFORCED,
                new MachineTextures(Tier.REINFORCED)
                        .withDefaultTexture("reinforced_fluid_input_hatch")
        );

        reinforcedFluidOutputHatch = customBlock(new FluidHatchBlock("reinforced_fluid_output_hatch", Material.METAL, Tier.REINFORCED, MultiblockPart.Type.FLUID, MultiblockPart.IO.OUTPUT),
                "reinforced.fluidOutputHatch",
                Tier.REINFORCED,
                new MachineTextures(Tier.REINFORCED)
                        .withDefaultTexture("reinforced_fluid_output_hatch")
        );


        basicEnergyConnector = customBlock(new EnergyConnectorBlock("basic_energy_connector", Material.METAL, Tier.BASIC, MultiblockPart.Type.ENERGY, MultiblockPart.IO.N_A),
                "basic.energyConnector",
                Tier.BASIC,
                new MachineTextures(Tier.BASIC)
                        .withDefaultTexture("basic_energy_connector")
        );

        reinforcedEnergyConnector = customBlock(new EnergyConnectorBlock("reinforced_energy_connector", Material.METAL, Tier.REINFORCED, MultiblockPart.Type.ENERGY, MultiblockPart.IO.N_A),
                "reinforced.energyConnector",
                Tier.REINFORCED,
                new MachineTextures(Tier.REINFORCED)
                        .withDefaultTexture("reinforced_energy_connector")
        );

        awakenedEnergyConnector = customBlock(new EnergyConnectorBlock("awakened_energy_connector", Material.METAL, Tier.AWAKENED, MultiblockPart.Type.ENERGY, MultiblockPart.IO.N_A),
                "awakened.energyConnector",
                Tier.AWAKENED,
                new MachineTextures(Tier.AWAKENED)
                        .withDefaultTexture("awakened_energy_connector")
        );

        basicInductionSmelter = customBlock(new InductionSmelterBlock("basic_induction_smelter", Material.METAL, Tier.BASIC),
                "basic.inductionSmelter",
                Tier.BASIC,
                new MachineTextures(Tier.BASIC)
                        .withDefaultTopTexture("basic_induction_smelter_top_inactive")
                        .withDefaultNorthTexture("basic_induction_smelter_front_inactive")
                        .withActiveTopTexture("basic_induction_smelter_top_active")
                        .withActiveNorthTexture("basic_induction_smelter_front_active")
                        .withOverbrightTopTexture("induction_smelter_top_overlay")
                        .withOverbrightNorthTexture("induction_smelter_front_overlay")
        );

        spatialEncapsulator = customBlock(new EncapsulatorBlock("awakened_encapsulator", Material.METAL, Tier.AWAKENED),
                "awakened.encapsulator",
                Tier.AWAKENED,
                new MachineTextures(Tier.AWAKENED)
                        .withDefaultNorthTexture("awakened_encapsulator_front_inactive")
                        .withActiveNorthTexture("awakened_encapsulator_front_active")
        );

        basicCasing = customBlock(new CasingBlock("basic_casing", Material.METAL, Tier.BASIC),
                "basic.casing",
                Tier.BASIC,
                new MachineTextures()
                        .withDefaultTexture("basic_casing")
        );

        basicCasing2 = (ConnectedTextureBlock) customBlock(new ConnectedTextureBlock("basic_casing_2", Material.METAL, Tier.BASIC, "basic_casing_2", new ArrayList<>()),
                "basic.casing2",
                Tier.BASIC,
                new MachineTextures()
                        .withDefaultTexture("basic_casing_2_0")
        );

        reinforcedCasing = customBlock(new CasingBlock("reinforced_casing", Material.METAL, Tier.REINFORCED),
                "reinforced.casing",
                Tier.REINFORCED,
                new MachineTextures()
                        .withDefaultTexture("reinforced_casing")
        );

        reinforcedCasing2 = (ConnectedTextureBlock) customBlock(new ConnectedTextureBlock("reinforced_casing_2", Material.METAL, Tier.REINFORCED, "reinforced_casing_2", new ArrayList<>()),
                "reinforced.casing2",
                Tier.BASIC,
                new MachineTextures()
                        .withDefaultTexture("reinforced_casing_2_0")
        );

        reinforcedFrame = customBlock(new CasingBlock("reinforced_frame", Material.METAL, Tier.REINFORCED),
                "reinforced.frame",
                Tier.REINFORCED,
                new MachineTextures()
                        .withDefaultTexture("reinforced_frame")
        );

        reinforcedGrate = customBlock(new CasingBlock("reinforced_grate", Material.METAL, Tier.REINFORCED),
                "reinforced.grate",
                Tier.REINFORCED,
                new MachineTextures()
                        .withDefaultTexture("reinforced_grate")
        );


        reinforcedGlass = (ConnectedTextureBlock) customBlock(new ConnectedTextureBlock("reinforced_glass", Material.METAL, Tier.REINFORCED, "reinforced_glass", new ArrayList<>()),
                "reinforced.glass",
                Tier.REINFORCED,
                new MachineTextures()
                        .withDefaultTexture("reinforced_glass_0")
        );

        awakenedCasing = (ConnectedTextureBlock) customBlock(new ConnectedTextureBlock("awakened_casing", Material.METAL, Tier.AWAKENED, "awakened_casing", new ArrayList<>()),
                "awakened.casing",
                Tier.AWAKENED,
                new MachineTextures()
                        .withDefaultTexture("awakened_casing_0")
        );
        awakenedSocketCasing = (ConnectedTextureBlock) customBlock(new ConnectedTextureBlock("awakened_socket_casing", Material.METAL, Tier.AWAKENED, "awakened_socket_casing", new ArrayList<>()),
                "awakened.casing.socket",
                Tier.AWAKENED,
                new MachineTextures()
                        .withDefaultTexture("awakened_socket_casing_0")
        );
        awakenedCasing2 = (ConnectedTextureBlock) customBlock(new ConnectedTextureBlock("awakened_casing_2", Material.METAL, Tier.AWAKENED, "awakened_casing_2", new ArrayList<>()),
                "awakened.casing2",
                Tier.AWAKENED,
                new MachineTextures()
                        .withDefaultTexture("awakened_casing_2_0")
        );

        eternityPortal = new EternityPortalBlock("eternity_portal").setTranslationKey(NAMESPACE, "eternityPortal");
        //energyFlowing = new TemplateFlowingLiquidBlock(NAMESPACE.id("energy_flowing"),Material.WATER).setTranslationKey(NAMESPACE, "signalumEnergy.flowing");
        //energyStill = new TemplateStillLiquidBlock(NAMESPACE.id("energy_still"),Material.WATER).setTranslationKey(NAMESPACE, "signalumEnergy.still");

        //burntSignalumFlowing = new TemplateFlowingLiquidBlock(NAMESPACE.id("burnt_energy_flowing"),Material.WATER).setTranslationKey(NAMESPACE, "burntSignalum.flowing");
        //burntSignalumStill = new TemplateStillLiquidBlock(NAMESPACE.id("burnt_energy_still"),Material.WATER).setTranslationKey(NAMESPACE, "burntSignalum.still");

        //worldResinFlowing = new TemplateFlowingLiquidBlock(NAMESPACE.id("world_resin_flowing"),Material.WATER).setTranslationKey(NAMESPACE, "worldResin.flowing");
        //worldResinStill = new TemplateStillLiquidBlock(NAMESPACE.id("world_resin_still"),Material.WATER).setTranslationKey(NAMESPACE, "worldResin.still");
    }

    public static Block simpleBlock(String name, String lang, MachineTextures textures){
        Material material = Material.STONE;
        BlockSoundGroup sound = Block.STONE_SOUND_GROUP;
        Block block = new SIBlock(name, material).setTranslationKey(NAMESPACE, lang).setHardness(1).setResistance(3).setSoundGroup(sound);
        blockTextures.put(block, textures);
        return block;
    }

    public static Block simpleBlock(String name, String lang, MachineTextures textures, float hardness, float resistance, Material material, BlockSoundGroup sound){
        Block block = new SIBlock(name, material).setTranslationKey(NAMESPACE, lang).setHardness(hardness).setResistance(resistance).setSoundGroup(sound);
        blockTextures.put(block, textures);
        return block;
    }


    public static Block simpleBlock(Block block, String lang, MachineTextures textures){
        BlockSoundGroup sound = Block.STONE_SOUND_GROUP;
        return simpleBlock(block, lang, sound, textures);
    }

    public static Block simpleBlock(Block block, String lang, BlockSoundGroup sound, MachineTextures textures){
        block.setHardness(1).setResistance(3).setSoundGroup(sound).setTranslationKey(NAMESPACE, lang);
        blockTextures.put(block, textures);
        return block;
    }

    public static Block basicBlock(Block block, String lang, MachineTextures textures){
        block.setTranslationKey(NAMESPACE, lang);
        blockTextures.put(block, textures);
        return block;
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
        Block block = new TieredBlock(name, material, tier).setTranslationKey(NAMESPACE, lang).setHardness(1).setResistance(3).setSoundGroup(sound);
        blockTextures.put(block, textures);
        return block;
    }

    public static Block customBlock(Block block, String lang, Tier tier, MachineTextures textures){
        //Material material = Material.STONE;
        BlockSoundGroup sound = Block.STONE_SOUND_GROUP;
        switch (tier) {
            case BASIC, REINFORCED, AWAKENED, INFINITE  -> {
                //material = Material.METAL;
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
        event.register(CrusherBlockEntity.class, NAMESPACE.id("crusher").toString());
        event.register(AlloySmelterBlockEntity.class, NAMESPACE.id("alloy_smelter").toString());
        event.register(PlateFormerBlockEntity.class, NAMESPACE.id("plate_former").toString());
        event.register(CrystalCutterBlockEntity.class, NAMESPACE.id("crystal_cutter").toString());
        event.register(ExtractorBlockEntity.class, NAMESPACE.id("extractor").toString());
        event.register(ItemBusBlockEntity.class, NAMESPACE.id("item_bus").toString());
        event.register(FluidHatchBlockEntity.class, NAMESPACE.id("fluid_hatch").toString());
        event.register(EnergyConnectorBlockEntity.class, NAMESPACE.id("energy_connector").toString());
        event.register(InductionSmelterBlockEntity.class, NAMESPACE.id("induction_smelter").toString());
        event.register(EncapsulatorBlockEntity.class, NAMESPACE.id("spatial_encapsulator").toString());
    }

    @EventListener
    public static void registerBlockEntityRenderers(BlockEntityRendererRegisterEvent event){
        event.renderers.put(FluidTankBlockEntity.class, new RenderFluidInBlock());
        event.renderers.put(EncapsulatorBlockEntity.class, new RenderEncapsulator());
        event.renderers.put(InductionSmelterBlockEntity.class, new RenderMultiblock());
    }

    @EventListener
    public static void registerBlockItems(BlockItemRegistryEvent event){

    }

}
