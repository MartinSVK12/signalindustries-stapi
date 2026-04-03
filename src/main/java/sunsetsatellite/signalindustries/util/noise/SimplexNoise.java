package sunsetsatellite.signalindustries.util.noise;

import java.util.Random;

public class SimplexNoise
{
    private static final int[][] GRAD = {
            {  1,  1,  0 }, { -1,  1,  0 }, {  1, -1,  0 },
            { -1, -1,  0 }, {  1,  0,  1 }, { -1,  0,  1 },
            {  1,  0, -1 }, { -1,  0, -1 }, {  0,  1,  1 },
            {  0, -1,  1 }, {  0,  1, -1 }, {  0, -1, -1 }
    };
    private static final double F2 = 0.5D * (Math.sqrt(3D) - 1.0D);
    private static final double G2 = (3D - Math.sqrt(3D)) / 6D;
    private final int[] p;
    public final double xo;
    public final double yo;
    public final double zo;

    public SimplexNoise(Random random)
    {
        p = new int[512];
        xo = random.nextDouble() * 256D;
        yo = random.nextDouble() * 256D;
        zo = random.nextDouble() * 256D;

        for(int i = 0; i < 256; i++)
        {
            p[i] = i;
        }

        for(int i = 0; i < 256; i++)
        {
            int k = random.nextInt(256 - i) + i;
            int l = p[i];
            p[i] = p[k];
            p[k] = l;
            p[i + 256] = p[i];
        }
    }

    private static int wrap(double d)
    {
        return d <= 0.0D ? (int)d - 1 : (int)d;
    }

    private static double dot(int[] vec, double x, double y)
    {
        return (double)vec[0] * x + (double)vec[1] * y;
    }

    // Values generated are guaranteed to be between -1.0 and 1.0, multiplied by valueScale.
    public void add(double[] out, double xOffset, double zOffset, int xSize, int zSize, double xScale, double zScale, double valueScale)
    {
        int index = 0;
        for(int dx = 0; dx < xSize; dx++)
        {
            double x = (xOffset + (double)dx) * xScale + xo;
            for(int dz = 0; dz < zSize; dz++)
            {
                double z = (zOffset + (double)dz) * zScale + yo;

                // Skew the input space to determine which simplex cell we're in
                double s = (x + z) * F2;
                int i = wrap(x + s);
                int j = wrap(z + s);

                // Unskew the cell origin back to (x, z) space
                double t = (double)(i + j) * G2;
                double X0 = (double)i - t;
                double Z0 = (double)j - t;
                double x0 = x - X0;
                double z0 = z - Z0;

                // For the 2D case, the simplex shape is an equilateral triangle.
                // Determine which simplex we are in.
                int i1, j1; // Offsets for second (middle) corner of simplex in (i, j) coords
                if(x0 > z0) // Lower triangle, XZ order: (0, 0) -> (1, 0) -> (1, 1)
                {
                    i1 = 1;
                    j1 = 0;
                }
                else // Upper triangle, ZX order: (0, 0) -> (0, 1) -> (1, 1)
                {
                    i1 = 0;
                    j1 = 1;
                }

                // A step of (1, 0) in (i, j) means a step of (1 - c, -c) in (x, z), and
                // a step of (0, 1) in (i, j) means a step of (-c, 1 - c) in (x, z), where
                // c = (3 - sqrt(3)) / 6

                double x1 = (x0 - (double)i1) + G2; // Offsets for middle corner in (x, z) unskewed coords
                double z1 = (z0 - (double)j1) + G2;
                double x2 = (x0 - 1.0D) + 2D * G2;  // Offsets for last corner in (x, z) unskewed coords
                double z2 = (z0 - 1.0D) + 2D * G2;

                // Work out the hashed gradient indices of the three simplex corners
                int ib = i & 0xff;
                int jb = j & 0xff;
                int gi0 = p[ib + p[jb]] % 12;
                int gi1 = p[ib + i1 + p[jb + j1]] % 12;
                int gi2 = p[ib + 1 + p[jb + 1]] % 12;

                // Calculate the contribution from the first corner
                double t0 = 0.5D - x0 * x0 - z0 * z0;
                double n0;
                if(t0 < 0.0D)
                {
                    n0 = 0.0D;
                }
                else
                {
                    t0 *= t0;
                    n0 = t0 * t0 * dot(GRAD[gi0], x0, z0);
                }

                // Calculate the contribution from the second corner
                double t1 = 0.5D - x1 * x1 - z1 * z1;
                double n1;
                if(t1 < 0.0D)
                {
                    n1 = 0.0D;
                }
                else
                {
                    t1 *= t1;
                    n1 = t1 * t1 * dot(GRAD[gi1], x1, z1);
                }

                // Calculate the contribution from the third corner
                double t2 = 0.5D - x2 * x2 - z2 * z2;
                double n2;
                if(t2 < 0.0D)
                {
                    n2 = 0.0D;
                }
                else
                {
                    t2 *= t2;
                    n2 = t2 * t2 * dot(GRAD[gi2], x2, z2);
                }

                double value = 70 * (n0 + n1 + n2);
                out[index++] += value * valueScale;
            }
        }
    }
}
