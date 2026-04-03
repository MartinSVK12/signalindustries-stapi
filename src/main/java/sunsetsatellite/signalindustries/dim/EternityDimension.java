package sunsetsatellite.signalindustries.dim;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.dimension.Dimension;
import net.modificationstation.stationapi.api.world.dimension.StationDimension;
import sunsetsatellite.signalindustries.data.SIDimensions;

public class EternityDimension extends Dimension {

    public EternityDimension(int id){
        SIDimensions.ETERNITY = this;
        this.id = id;
    }

    @Override
    protected void initBiomeSource() {
        this.biomeSource = new EternityBiomeSource(world);
    }

    @Override
    public ChunkSource createChunkGenerator() {
        return new EternityChunkGenerator(world, world.getSeed());
    }

    @Override
    public float getTimeOfDay(long time, float tickDelta) {
        return 1f;
    }

    @Override
    public boolean isValidSpawnPoint(int x, int z) {
        return true;
    }

    @Override
    public boolean hasWorldSpawn() {
        return false;
    }

    @Override
    public Vec3d getFogColor(float timeOfDay, float tickDelta) {
        return Vec3d.createCached(0.7,0.7,0.7);
    }

    @Override
    public float[] getBackgroundColor(float timeOfDay, float tickDelta) {
        return null;
    }

    @Override
    public float getCloudHeight() {
        return -1;
    }

    @Override
    public boolean hasGround() {
        return true;
    }
}
