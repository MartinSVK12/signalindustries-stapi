package sunsetsatellite.signalindustries.dim;

import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import sunsetsatellite.signalindustries.data.SIBlocks;
import sunsetsatellite.signalindustries.worldgen.AshenTreeFeature;

import java.util.List;
import java.util.Random;

public class EternityBiome extends Biome {

    public EternityBiome(){
        super();

        this.topBlockId = (byte) SIBlocks.realityFabric.id;
        this.soilBlockId = (byte) SIBlocks.realityFabric.id;

        this.setName("Eternity");
        this.setGrassColor(0xFFFFFF);
        this.setFoliageColor(0xFFFFFF);

        this.spawnableMonsters.clear();
        this.spawnablePassive.clear();
        this.spawnableWaterCreatures.clear();
    }

    @Override
    public Feature getRandomTreeFeature(Random random) {
        return null;
    }

    @Override
    public int getSkyColor(float temperature) {
        return 0xB2B2B2;
    }
}
