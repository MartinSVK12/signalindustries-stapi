package sunsetsatellite.signalindustries.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;

public class ExplosionNoDrops {
    public boolean fire = false;
    private final Random random = new Random();
    private final World world;
    public double x;
    public double y;
    public double z;
    public Entity source;
    public float power;
    public Set damagedBlocks = new HashSet();

    public ExplosionNoDrops(World world, Entity source, double x, double y, double z, float power) {
        this.world = world;
        this.source = source;
        this.power = power;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void explode() {
        float var1 = this.power;
        byte var2 = 16;

        for(int var3 = 0; var3 < var2; ++var3) {
            for(int var4 = 0; var4 < var2; ++var4) {
                for(int var5 = 0; var5 < var2; ++var5) {
                    if (var3 == 0 || var3 == var2 - 1 || var4 == 0 || var4 == var2 - 1 || var5 == 0 || var5 == var2 - 1) {
                        double var6 = (float)var3 / ((float)var2 - 1.0F) * 2.0F - 1.0F;
                        double var8 = (float)var4 / ((float)var2 - 1.0F) * 2.0F - 1.0F;
                        double var10 = (float)var5 / ((float)var2 - 1.0F) * 2.0F - 1.0F;
                        double var12 = Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
                        var6 /= var12;
                        var8 /= var12;
                        var10 /= var12;
                        float var14 = this.power * (0.7F + this.world.random.nextFloat() * 0.6F);
                        double var15 = this.x;
                        double var17 = this.y;
                        double var19 = this.z;

                        for(float var21 = 0.3F; var14 > 0.0F; var14 -= var21 * 0.75F) {
                            int var22 = MathHelper.floor(var15);
                            int var23 = MathHelper.floor(var17);
                            int var24 = MathHelper.floor(var19);
                            int var25 = this.world.getBlockId(var22, var23, var24);
                            if (var25 > 0) {
                                var14 -= (Block.BLOCKS[var25].getBlastResistance(this.source) + 0.3F) * var21;
                            }

                            if (var14 > 0.0F) {
                                this.damagedBlocks.add(new BlockPos(var22, var23, var24));
                            }

                            var15 += var6 * (double)var21;
                            var17 += var8 * (double)var21;
                            var19 += var10 * (double)var21;
                        }
                    }
                }
            }
        }

        this.power *= 2.0F;
        int var29 = MathHelper.floor(this.x - (double)this.power - (double)1.0F);
        int var30 = MathHelper.floor(this.x + (double)this.power + (double)1.0F);
        int var31 = MathHelper.floor(this.y - (double)this.power - (double)1.0F);
        int var33 = MathHelper.floor(this.y + (double)this.power + (double)1.0F);
        int var7 = MathHelper.floor(this.z - (double)this.power - (double)1.0F);
        int var35 = MathHelper.floor(this.z + (double)this.power + (double)1.0F);
        List var9 = this.world.getEntities(this.source, Box.createCached(var29, var31, var7, var30, var33, var35));
        Vec3d var37 = Vec3d.createCached(this.x, this.y, this.z);

        for(int var11 = 0; var11 < var9.size(); ++var11) {
            Entity var39 = (Entity)var9.get(var11);
            double var13 = var39.getDistance(this.x, this.y, this.z) / (double)this.power;
            if (var13 <= (double)1.0F) {
                double var43 = var39.x - this.x;
                double var46 = var39.y - this.y;
                double var49 = var39.z - this.z;
                double var51 = MathHelper.sqrt(var43 * var43 + var46 * var46 + var49 * var49);
                var43 /= var51;
                var46 /= var51;
                var49 /= var51;
                double var52 = this.world.getVisibilityRatio(var37, var39.boundingBox);
                double var53 = ((double)1.0F - var13) * var52;
                var39.damage(this.source, (int)((var53 * var53 + var53) / (double)2.0F * (double)8.0F * (double)this.power + (double)1.0F));
                var39.velocityX += var43 * var53;
                var39.velocityY += var46 * var53;
                var39.velocityZ += var49 * var53;
            }
        }

        this.power = var1;
        ArrayList var38 = new ArrayList();
        var38.addAll(this.damagedBlocks);
        if (this.fire) {
            for(int var40 = var38.size() - 1; var40 >= 0; --var40) {
                BlockPos var41 = (BlockPos)var38.get(var40);
                int var42 = var41.x;
                int var45 = var41.y;
                int var16 = var41.z;
                int var48 = this.world.getBlockId(var42, var45, var16);
                int var18 = this.world.getBlockId(var42, var45 - 1, var16);
                if (var48 == 0 && Block.BLOCKS_OPAQUE[var18] && this.random.nextInt(3) == 0) {
                    this.world.setBlock(var42, var45, var16, Block.FIRE.id);
                }
            }
        }

    }

    public void playExplosionSound(boolean addParticles) {
        this.world.playSound(this.x, this.y, this.z, "random.explode", 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
        ArrayList var2 = new ArrayList();
        var2.addAll(this.damagedBlocks);

        for(int var3 = var2.size() - 1; var3 >= 0; --var3) {
            BlockPos var4 = (BlockPos)var2.get(var3);
            int var5 = var4.x;
            int var6 = var4.y;
            int var7 = var4.z;
            int var8 = this.world.getBlockId(var5, var6, var7);
            if (addParticles) {
                double var9 = (float)var5 + this.world.random.nextFloat();
                double var11 = (float)var6 + this.world.random.nextFloat();
                double var13 = (float)var7 + this.world.random.nextFloat();
                double var15 = var9 - this.x;
                double var17 = var11 - this.y;
                double var19 = var13 - this.z;
                double var21 = MathHelper.sqrt(var15 * var15 + var17 * var17 + var19 * var19);
                var15 /= var21;
                var17 /= var21;
                var19 /= var21;
                double var23 = (double)0.5F / (var21 / (double)this.power + 0.1);
                var23 *= this.world.random.nextFloat() * this.world.random.nextFloat() + 0.3F;
                var15 *= var23;
                var17 *= var23;
                var19 *= var23;
                this.world.addParticle("explode", (var9 + this.x) / (double)2.0F, (var11 + this.y) / (double)2.0F, (var13 + this.z) / (double)2.0F, var15, var17, var19);
                this.world.addParticle("smoke", var9, var11, var13, var15, var17, var19);
            }

            if (var8 > 0) {
                //Block.BLOCKS[var8].dropStacks(this.world, var5, var6, var7, this.world.getBlockMeta(var5, var6, var7), 0.3F);
                this.world.setBlock(var5, var6, var7, 0);
                //Block.BLOCKS[var8].onDestroyedByExplosion(this.world, var5, var6, var7);
            }
        }

    }
}
