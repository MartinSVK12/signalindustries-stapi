package sunsetsatellite.signalindustries.dim;

import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.LoadingDisplay;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.gen.feature.Feature;
import net.modificationstation.stationapi.impl.world.chunk.FlattenedChunk;
import sunsetsatellite.signalindustries.data.SIBlocks;
import sunsetsatellite.signalindustries.util.noise.CombinedPerlinNoise;
import sunsetsatellite.signalindustries.util.noise.PerlinNoise;
import sunsetsatellite.signalindustries.worldgen.AshenTreeFeature;
import sunsetsatellite.signalindustries.worldgen.DilithiumCrystalFeature;
import sunsetsatellite.signalindustries.worldgen.ObeliskFeature;

import java.util.Random;

public class EternityChunkGenerator implements ChunkSource {

    private final Random random;
    private final World world;
    private final CombinedPerlinNoise combinedA;
    private final CombinedPerlinNoise combinedB;
    private final CombinedPerlinNoise combinedC;
    private final CombinedPerlinNoise combinedD;
    private final PerlinNoise octavesA;
    private final PerlinNoise octavesB;

    private static final int CHUNK_SIZE_X = 16;
    private static final int CHUNK_SIZE_Z = 16;
    
    public EternityChunkGenerator(World world, long seed) {
        this.world = world;
        this.random = new Random(seed);

        combinedA = new CombinedPerlinNoise(new PerlinNoise(seed, 8, 0), new PerlinNoise(seed, 8, 8));
        combinedB = new CombinedPerlinNoise(new PerlinNoise(seed, 8, 16), new PerlinNoise(seed, 8, 24));
        combinedC = new CombinedPerlinNoise(new PerlinNoise(seed, 8, 32), new PerlinNoise(seed, 8, 40));
        combinedD = new CombinedPerlinNoise(new PerlinNoise(seed, 8, 48), new PerlinNoise(seed, 8, 56));

        octavesA = new PerlinNoise(seed, 6, 64);
        octavesB = new PerlinNoise(seed, 8, 70);
    }

    public void buildTerrain(int chunkX, int chunkZ, FlattenedChunk chunk) {
        final float mod = 1.3F;

        final int[] heightMap = new int[CHUNK_SIZE_X * CHUNK_SIZE_Z];

        // Raising..
        for (int x = 0; x < CHUNK_SIZE_X; x++)
        {
            for (int z = 0; z < CHUNK_SIZE_Z; z++)
            {
                final double noiseA = combinedA.get((chunkX * CHUNK_SIZE_X + x) * mod, (chunkZ * CHUNK_SIZE_Z + z) * mod) / 6.0D + -4;
                double noiseB = combinedB.get((chunkX * CHUNK_SIZE_X + x) * mod, (chunkZ * CHUNK_SIZE_Z + z) * mod) / 5.0D + 10.0D + -4;
                if (octavesA.get(chunkX * CHUNK_SIZE_X + x, chunkZ * CHUNK_SIZE_Z + z) / 8.0D > 0.0D)
                {
                    noiseB = noiseA;
                }

                double height;
                if ((height = Math.max(noiseA, noiseB) / 2.0D) < 0.0D)
                {
                    height *= 0.8D;
                }

                heightMap[x + z * CHUNK_SIZE_X] = (int) height;
            }
        }

        // Eroding..
        for (int x = 0; x < CHUNK_SIZE_X; x++)
        {
            for (int z = 0; z < CHUNK_SIZE_Z; z++)
            {
                final double val = combinedC.get((chunkX * CHUNK_SIZE_X + x) << 1, (chunkZ * CHUNK_SIZE_Z + z) << 1) / 8.0D;
                final int val2 = combinedD.get((chunkX * CHUNK_SIZE_X + x) << 1, (chunkZ * CHUNK_SIZE_Z + z) << 1) > 0.0D ? 1 : 0;
                if (val > 2.0D)
                {
                    final int newHeight = ((heightMap[x + z * CHUNK_SIZE_X] - val2) / 2 << 1) + val2;
                    heightMap[x + z * CHUNK_SIZE_X] = newHeight;
                }
            }
        }

        // Soiling..
        for (int x = 0; x < CHUNK_SIZE_X; x++)
        {
            for (int z = 0; z < CHUNK_SIZE_Z; z++)
            {
                final int val = (int) (octavesB.get(chunkX * CHUNK_SIZE_X + x, chunkZ * CHUNK_SIZE_Z + z) / 24.0D) - 4;
                int newHeight;
                final int val2 = (newHeight = heightMap[x + z * CHUNK_SIZE_X] + 64) + val;
                heightMap[x + z * CHUNK_SIZE_X] = Math.max(newHeight, val2);
                if (heightMap[x + z * CHUNK_SIZE_X] > 128 - 2)
                {
                    heightMap[x + z * CHUNK_SIZE_X] = 128 - 2;
                }

                if (heightMap[x + z * CHUNK_SIZE_X] < 1)
                {
                    heightMap[x + z * CHUNK_SIZE_X] = 1;
                }

                for (int y = 0; y < 128; y++)
                {
                    int blockID = 0;

                    if (y < newHeight)
                    {
                        blockID = SIBlocks.realityFabric.id;
                    }

                    if (y == 0)
                    {
                        blockID = Block.BEDROCK.id;
                    }

                    chunk.setBlock(x,y,z,blockID);
                }
            }
        }
    }
    @Override
    public Chunk getChunk(int chunkX, int chunkZ) {
        this.random.setSeed((long)chunkX * 341873128712L + (long)chunkZ * 132897987541L);
        FlattenedChunk flatChunk = new FlattenedChunk(world, chunkX, chunkZ);
        buildTerrain(chunkX, chunkZ, flatChunk);
        flatChunk.populateHeightMap();
        return flatChunk;
    }

    @Override
    public void decorate(ChunkSource source, int chunkX, int chunkZ) {
        int x = chunkX * 16;
        int z = chunkZ * 16;
        int y = this.world.getTopY(x, z);
        if(random.nextInt(16) == 0){
            Feature tree = new AshenTreeFeature(0, SIBlocks.ashenTreeLog.id);
            tree.generate(world,random,x,y,z);
        }
        if(random.nextInt(128) == 0){
            Feature obelisk = new ObeliskFeature();
            obelisk.generate(world,random,x,y,z);
        }
        if(random.nextInt(64) == 0){
            Feature crystal = new DilithiumCrystalFeature();
            crystal.generate(world,random,x,y,z);
        }
    }


    @Override
    public boolean isChunkLoaded(int x, int z) {
        return true;
    }

    @Override
    public Chunk loadChunk(int chunkX, int chunkZ) {
        return getChunk(chunkX, chunkZ);
    }

    @Override
    public boolean save(boolean saveEntities, LoadingDisplay display) {
        return true;
    }

    @Override
    public boolean tick() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String getDebugInfo() {
        return "EternityChunkGenerator";
    }
}
