package sunsetsatellite.signalindustries.util.noise;

import java.util.Random;

public abstract class BaseImprovedNoise
{
    protected final int[] p;
    public final double xo;
    public final double yo;
    public final double zo;

    public BaseImprovedNoise(Random random) {
        this.p = new int[512];
        this.xo = random.nextDouble() * 256;
        this.yo = random.nextDouble() * 256;
        this.zo = random.nextDouble() * 256;

        // Assign initial values for each permutation
        for (int i = 0; i < 256; i++)
        {
            p[i] = i;
        }

        // Shuffle values
        for(int i = 0; i < 256; i++)
        {
            int newI = random.nextInt(256 - i) + i;
            int temp = this.p[i];
            this.p[i] = this.p[newI];
            this.p[newI] = temp;
            this.p[i + 256] = this.p[i];
        }
    }

    public double getValue(double x, double y)
    {
        return this.getValue(x, y, 0);
    }

    public double getValue(double x, double y, double z)
    {
        int X = (int)((long)Math.floor(x) & 255),                  // FIND UNIT CUBE THAT
            Y = (int)((long)Math.floor(y) & 255),                  // CONTAINS POINT.
            Z = (int)((long)Math.floor(z) & 255);
        x -= Math.floor(x);                                // FIND RELATIVE X,Y,Z
        y -= Math.floor(y);                                // OF POINT IN CUBE.
        z -= Math.floor(z);
        double u = fade(x),                                // COMPUTE FADE CURVES
               v = fade(y),                                // FOR EACH OF X,Y,Z.
               w = fade(z);
        int A = p[X  ]+Y, AA = p[A]+Z, AB = p[A+1]+Z,      // HASH COORDINATES OF
            B = p[X+1]+Y, BA = p[B]+Z, BB = p[B+1]+Z;      // THE 8 CUBE CORNERS,

        return lerp(w, lerp(v, lerp(u, grad(p[AA  ], x  , y  , z   ),  // AND ADD
                                                            grad(p[BA  ], x-1, y  , z   )), // BLENDED
                                             lerp(u, grad(p[AB  ], x  , y-1, z   ),  // RESULTS
                                                            grad(p[BB  ], x-1, y-1, z   ))),// FROM  8
                              lerp(v, lerp(u, grad(p[AA+1], x  , y  , z-1 ),  // CORNERS
                                                            grad(p[BA+1], x-1, y  , z-1 )), // OF CUBE
                                             lerp(u, grad(p[AB+1], x  , y-1, z-1 ),
                                                         grad(p[BB+1], x-1, y-1, z-1 ))));
    }

    protected double grad(int hash, double x, double y)
    {
        int j = hash & 0xf;
        double d2 = (double)(1 - ((j & 8) >> 3)) * x;
        double d3 = j >= 4 ? j != 12 && j != 14 ? y : x : 0.0D;
        return ((j & 1) != 0 ? -d2 : d2) + ((j & 2) != 0 ? -d3 : d3);
    }

    protected double fade(double t)
    {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    protected double lerp(double t, double a, double b)
    {
        return a + t * (b - a);
    }

    protected double grad(int hash, double x, double y, double z)
    {
        int h = hash & 15;                      // CONVERT LO 4 BITS OF HASH CODE
        double u = h<8 ? x : y,                 // INTO 12 GRADIENT DIRECTIONS.
                v = h<4 ? y : h==12||h==14 ? x : z;
        return ((h&1) == 0 ? u : -u) + ((h&2) == 0 ? v : -v);
    }

    public abstract void add(double[] arr, double x, double y, double z, int xSize, int ySize, int zSize, double xScale, double yScale, double zScale, double levelScale);
}
