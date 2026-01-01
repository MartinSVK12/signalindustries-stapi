package sunsetsatellite.signalindustries.data;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.modificationstation.stationapi.api.client.event.block.entity.BlockEntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockItemRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import sunsetsatellite.signalindustries.block.*;
import sunsetsatellite.signalindustries.block.base.ConnectedTextureBlock;
import sunsetsatellite.signalindustries.block.base.SIBlock;
import sunsetsatellite.signalindustries.block.base.TieredBlock;
import sunsetsatellite.signalindustries.block.entity.*;
import sunsetsatellite.signalindustries.block.machine.*;
import sunsetsatellite.signalindustries.block.ore.DilithiumOreBlock;
import sunsetsatellite.signalindustries.block.ore.DimensionalShardOreBlock;
import sunsetsatellite.signalindustries.block.ore.MeteoriteIronOreBlock;
import sunsetsatellite.signalindustries.block.ore.SignaliteOreBlock;
import sunsetsatellite.signalindustries.util.MachineTextures;
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

    public static Block glowingObsidian;
    public static Block realityFabric;
    public static Block rootedFabric;

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

    public static Block prototypeAlloySmelter;

    public static Block prototypePlateFormer;

    public static Block prototypeCrystalCutter;

    public static Block prototypeExtractor;

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

        prototypeAlloySmelter = customBlock(new AlloySmelterBlock("prototype_alloy_smelter", Material.STONE, Tier.PROTOTYPE),
                "prototype.alloySmelter",
                Tier.PROTOTYPE,
                new MachineTextures(Tier.PROTOTYPE)
                        .withDefaultNorthTexture("alloy_smelter_prototype_inactive")
                        .withActiveNorthTexture("alloy_smelter_prototype_active")
                        .withOverbrightNorthTexture("alloy_smelter_overlay")
        );

        prototypePlateFormer = customBlock(new PlateFormerBlock("prototype_plate_former", Material.STONE, Tier.PROTOTYPE),
                "prototype.plateFormer",
                Tier.PROTOTYPE,
                new MachineTextures(Tier.PROTOTYPE)
                        .withDefaultNorthTexture("plate_former_prototype_inactive")
                        .withActiveNorthTexture("plate_former_prototype_active")
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

        prototypeExtractor = customBlock(new ExtractorBlock("prototype_extractor", Material.STONE, Tier.PROTOTYPE),
                "prototype.extractor",
                Tier.PROTOTYPE,
                new MachineTextures(Tier.PROTOTYPE)
                        .withDefaultSideTextures("extractor_prototype_side_empty")
                        .withActiveSideTextures("extractor_prototype_side_active")
                        .withOverbrightSideTextures("extractor_overlay")
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
        block.setHardness(1).setResistance(3).setSoundGroup(sound).setTranslationKey(NAMESPACE, lang);
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
    }

    @EventListener
    public static void registerBlockEntityRenderers(BlockEntityRendererRegisterEvent event){
        //event.renderers.put(FluidTankBlockEntity.class, new RenderFluidInBlock());
    }

    @EventListener
    public static void registerBlockItems(BlockItemRegistryEvent event){

    }

}
