package sunsetsatellite.signalindustries.util.noise;

import java.util.Arrays;
import java.util.Random;

public abstract class BasePerlinNoise<T extends BaseImprovedNoise>
    extends SurfaceNoise
{
    private final T[] noiseLevels;
    private final int levels;

    public BasePerlinNoise(long seed, int levels)
    {
        this(seed, levels, 0);
    }

    public BasePerlinNoise(long seed, int levels, int preLevels)
    {
        Random random = new Random(seed);

        for (int i = 0; i < preLevels; i++)
        {
            random.nextDouble();
            random.nextDouble();
            random.nextDouble();

            for (int j = 0; j < 256; j++)
            {
                random.nextInt(256 - j);
            }
        }
        this.levels = levels;
        this.noiseLevels = newNoiseLevels(random, levels);
    }

    protected abstract T[] newNoiseLevels(Random random, int numLevels);

    public double get(double x, double y)
    {
        double out = 0.0D;
        double levelScale = 1.0D;

        for(int i = 0; i < levels; i++)
        {
            out += noiseLevels[i].getValue(x * levelScale, y * levelScale) / levelScale;
            levelScale /= 2;
        }

        return out;
    }

    public double[] get(double[] noiseArray, double x, double y, double z,
                        int xSize, int ySize, int zSize, double scaleX, double scaleY,
                        double scaleZ)
    {
        if (noiseArray == null)
        {
            noiseArray = new double[xSize * ySize * zSize];
        }
        else
        {
            Arrays.fill(noiseArray, 0.0D);
        }

        double levelScale = 1.0D;
        for(int i = 0; i < levels; i++)
        {
            noiseLevels[i].add(noiseArray, x, y, z, xSize, ySize, zSize, scaleX * levelScale, scaleY * levelScale, scaleZ * levelScale, levelScale);
            levelScale /= 2D;
        }

        return noiseArray;
    }

    public double[] get(double[] densityArray, int x, int z, int xSize, int zSize, double scaleX, double scaleZ)
    {
        return get(densityArray, x, 0, z, xSize, 1, zSize, scaleX, 1.0, scaleZ);
    }
}
