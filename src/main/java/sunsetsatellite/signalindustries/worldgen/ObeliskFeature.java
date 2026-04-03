package sunsetsatellite.signalindustries.worldgen;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.data.SIBlocks;

import java.util.Random;

public class ObeliskFeature extends Feature {
    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
        int l = 8;
        if (j >= 1 && j + l + 1 <= world.getHeight()) {
            for (int x = -4; x <= 4; x++) {
                for (int z = -4; z <= 4; z++) {
                    if(world.getTopY(i+x,k+z) < j-1){
                        return false;
                    }
                }
            }
            if (j < world.getHeight() - l - 1) {
                if((world.getBlockId(i,j-1,k) == SIBlocks.realityFabric.id /*&& world.getBlockId(i,j-1,k) != SIBlocks.eternalTreeLog.id*/) || (world.dimension.id == 0 && world.getBlockId(i,j-1,k) != 0 )){
                    for (int x = -2; x <= 2; x++) {
                        for (int z = -2; z <= 2; z++) {
                            if((x == -2 || x == 2) && (z == -2 || z == 2)){
                                world.setBlock(i+x,j,k+z, SIBlocks.rootedFabric.id);
                            } else {
                                world.setBlock(i+x,j,k+z, SIBlocks.realityFabric.id);
                            }
                        }
                    }
                    for (int x = -4; x <= 4; x++) {
                        for (int z = -4; z <= 4; z++) {
                            if(random.nextFloat() < 0.2f){
                                world.setBlock(i+x,world.getTopY(i+x,k+z)-1,k+z, SIBlocks.rootedFabric.id);
                            }
                        }
                    }
                    world.setBlock(i,j,k, SIBlocks.rootedFabric.id);

                    world.setBlock(i+2,j+1,k, SIBlocks.rootedFabric.id);
                    world.setBlock(i-2,j+1,k, SIBlocks.rootedFabric.id);
                    world.setBlock(i,j+1,k+2, SIBlocks.rootedFabric.id);
                    world.setBlock(i,j+1,k-2, SIBlocks.rootedFabric.id);

                    world.setBlock(i+2,j+2,k, SIBlocks.rootedFabric.id);
                    world.setBlock(i-2,j+2,k, SIBlocks.rootedFabric.id);
                    world.setBlock(i,j+2,k+2, SIBlocks.rootedFabric.id);
                    world.setBlock(i,j+2,k-2, SIBlocks.rootedFabric.id);

                    world.setBlock(i,j+l+3,k, SIBlocks.realityFabric.id);
                    world.setBlock(i,j+l+2,k, SIBlocks.realityFabric.id);
                    world.setBlock(i,j+l+1,k, SIBlocks.rootedFabric.id);
                    world.setBlock(i,j+l,k, SIBlocks.dimensionalShardOre.id);
                    world.setBlock(i,j+l-1,k, SIBlocks.rootedFabric.id);
                    world.setBlock(i,j+l-2,k, SIBlocks.realityFabric.id);
                    world.setBlock(i,j+l-3,k, SIBlocks.realityFabric.id);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
