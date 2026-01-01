package sunsetsatellite.signalindustries.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import sunsetsatellite.catalyst.core.util.vector.Vec3i;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.data.SIBlocks;
import sunsetsatellite.signalindustries.util.ExplosionNoDrops;
import sunsetsatellite.signalindustries.util.MeteorLocation;

import java.util.Random;

public class MeteorFeature extends Feature {

    public int oreId;
    public int oreMeta;
    public int oreChance;
    public int radius = 4;

    public MeteorFeature(int oreId, int oreMeta, int oreChance){
        this.oreId = oreId;
        this.oreMeta = oreMeta;
        this.oreChance = oreChance;
    }

    public MeteorFeature(int oreId, int oreMeta, int oreChance, int radius){
        this.oreId = oreId;
        this.oreMeta = oreMeta;
        this.oreChance = oreChance;
        this.radius = radius;
    }

    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
        ExplosionNoDrops ex = new ExplosionNoDrops(world,null,i,j,k,50f);
        ex.explode();
        ex.playExplosionSound(false);

        int oreBlocks = 0;

        for(int x = -radius; x <= radius; ++x) {
            for(int y = -radius; y <= radius; ++y) {
                for(int z = -radius; z <= radius; ++z) {
                    if (isPointInsideSphere(x, y, z, radius)) {
                        if (oreId != 0 && random.nextInt(100) < oreChance){
                            world.setBlock(x+i, (y+j)-8, z+k, oreId, oreMeta);
                            oreBlocks++;
                        } else {
                            world.setBlock(x+i, (y+j)-8, z+k, SIBlocks.meteorite.id, 0);
                        }
                    }
                }
            }
        }

        SignalIndustries.addMeteorLocation(new MeteorLocation(MeteorLocation.Type.getFromBlock(Block.BLOCKS[oreId]),new Vec3i(i,j,k)));
        SignalIndustries.LOGGER.info("Meteor at {} contains {} ore.", new Vec3i(i,j,k), oreBlocks);
        return true;
    }

    public boolean isPointInsideSphere(int x, int y, int z, double radius) {
        return x*x + y*y + z*z < radius*radius;
    }
}
