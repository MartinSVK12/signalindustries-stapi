package sunsetsatellite.signalindustries.util.noise;

import java.util.Arrays;
import java.util.Random;

public class PerlinSimplexNoise extends SurfaceNoise
{
    private final SimplexNoise[] noiseLevels;
    private final int levels;

    public PerlinSimplexNoise(Random random, int levels)
    {
        this.levels = levels;
        noiseLevels = new SimplexNoise[levels];
        for(int i = 0; i < levels; i++)
        {
            noiseLevels[i] = new SimplexNoise(random);
        }
    }

    public double getMaximumValue()
    {
        return getMaximumValue(0.5);
    }

    // Gets the maximum value for a given value of d5. For those values, the range is from -getMaximumValue to getMaximumValue.
    public double getMaximumValue(double d5)
    {
        double max = 0.0;

        double scaleB = 1.0;
        for (int l = 0; l < levels; l++)
        {
            max += 1.0 * (0.55D / scaleB);
            scaleB *= d5;
        }

        return max;
    }

    public double[] getValue(double[] out, double x, double z, int xSize, int zSize, double xScale, double zScale, double d4)
    {
        return getValue(out, x, z, xSize, zSize, xScale, zScale, d4, 0.5D);
    }

    public double[] getValue(double[] out, double x, double z, int xSize, int zSize, double xScale, double zScale, double d4, double d5)
    {
        xScale /= 1.5D;
        zScale /= 1.5D;

        if (out == null || out.length < xSize * zSize)
        {
            out = new double[xSize * zSize];
        }
        else
        {
            Arrays.fill(out, 0.0D);
        }

        double scaleA = 1.0D;
        double scaleB = 1.0D;
        for(int l = 0; l < levels; l++)
        {
            noiseLevels[l].add(out, x, z, xSize, zSize, xScale * scaleA, zScale * scaleA, 0.55D / scaleB);
            scaleA *= d4;
            scaleB *= d5;
        }

        return out;
    }
}
