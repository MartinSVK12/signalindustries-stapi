package sunsetsatellite.signalindustries.worldgen;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import sunsetsatellite.signalindustries.data.SIBlocks;

import java.util.Random;

public class DilithiumCrystalFeature extends Feature {
    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
        int maxRadius = 2;
        int maxRadiusRepeat = 4 + random.nextInt(4);

        int radius = 0;
        int worldHeight = world.getTopY();
        int height = ((worldHeight + j) / 2) - (random.nextInt(16)-8);

        generateCircle(world,random,i,height+((maxRadius + maxRadiusRepeat)/2)+1,k);

        boolean flip = false;
        int id = SIBlocks.dilithiumCrystalBlock.id;
        int y = 0;
        for (int l = 0; l <= maxRadius*2; l++) {
            int x = 0;
            boolean first = true;
            for (int lineRadius = radius; lineRadius >= 0; lineRadius--) {
                if(!first){
                    for (int z = -lineRadius; z <= lineRadius; z++) {
                        world.setBlock(i+x,height+y,k+z, id);
                        world.setBlock(i+(x*-1),height+y,k+z, id);
                    }
                    x++;
                } else {
                    for (int z = -radius; z <= radius; z++) {
                        world.setBlock(i+x,height+y,k+z, id);
                    }
                    x++;
                    first = false;
                }
            }
            if(!flip){
                radius++;
            } else {
                radius--;
            }
            if(radius >= maxRadius){
                y++;
                for (int m = 0; m < maxRadiusRepeat; m++) {
                    x = 0;
                    for (int lineRadius = maxRadius; lineRadius >= 0; lineRadius--) {
                        if (!first) {
                            for (int z = -lineRadius; z <= lineRadius; z++) {
                                world.setBlock(i + x, height + y, k + z, id);
                                world.setBlock(i + (x * -1), height + y, k + z, id);
                            }
                            x++;
                        }
                    }
                    y++;
                }
                flip = true;
                radius--;
                y--;
            }
            y++;
        }
        return true;
    }

    private void generateCircle(World world, Random random, int i, int j, int k) {
        int blockRadius = 5;

        for(int x = -blockRadius; x <= blockRadius; ++x) {
            for(int z = -blockRadius; z <= blockRadius; ++z) {
                if (isPointInsideCircle(x, z, blockRadius)) {
                    if(random.nextFloat() < 0.33f){
                        world.setBlock(x+i, j, z+k, SIBlocks.rootedFabric.id, 0);
                    } else {
                        world.setBlock(x+i, j, z+k, SIBlocks.realityFabric.id, 0);
                    }

                }
            }
        }

        blockRadius--;

        for(int x = -blockRadius; x <= blockRadius; ++x) {
            for(int z = -blockRadius; z <= blockRadius; ++z) {
                if (isPointInsideCircle(x, z, blockRadius)) {
                    world.setBlock(x+i, j, z+k, 0, 0);
                }
            }
        }
    }

    public boolean isPointInsideCircle(int x, int z, double radius) {
        return x*x + z*z <= radius*radius;
    }
}
