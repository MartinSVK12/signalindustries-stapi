package sunsetsatellite.signalindustries.dim;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;

public class EternityBiomeSource extends BiomeSource {
    public static final Biome ETERNITY = new EternityBiome();

    public EternityBiomeSource(World world) {
        super(world);
    }

    @Override
    public Biome[] getBiomesInArea(Biome[] biomes, int x, int z, int width, int depth) {
        biomes = new Biome[width * depth];
        this.temperatureMap = new double[width * depth];
        this.downfallMap = new double[width * depth];
        this.weirdnessMap = new double[width * depth];

        for (int i = 0; i < biomes.length; i++) {
            biomes[i] = ETERNITY;

            temperatureMap[i] = 0.0;
            downfallMap[i] = 0.0;
            weirdnessMap[i] = 1.0;
        }

        return biomes;
    }
}
