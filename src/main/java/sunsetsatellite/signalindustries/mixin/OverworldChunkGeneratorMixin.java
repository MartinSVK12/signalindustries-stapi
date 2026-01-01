package sunsetsatellite.signalindustries.mixin;

import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.data.SIBlocks;
import sunsetsatellite.signalindustries.data.SIConfig;
import sunsetsatellite.signalindustries.worldgen.MeteorFeature;

import java.util.Random;

import static sunsetsatellite.signalindustries.SignalIndustries.config;

@Mixin(OverworldChunkGenerator.class)
public class OverworldChunkGeneratorMixin {
    @Shadow
    private World world;

    @Inject(method = "decorate", at = @At("TAIL"))
    public void decorate(ChunkSource chunk, int chunkX, int chunkZ, CallbackInfo ci) {
        int x = chunkX * 16;
        int z = chunkZ * 16;
        Random rand = new Random(world.getSeed());
        long l1 = (rand.nextLong() / 2L) * 2L + 1L;
        long l2 = (rand.nextLong() / 2L) * 2L + 1L;
        rand.setSeed((long) chunkX * l1 + (long) chunkZ * l2 ^ world.getSeed());

        if(rand.nextInt(config.ironMeteorChance) == 0) {
            int i = x + rand.nextInt(16);
            int k = z + rand.nextInt(16);
            int j = world.getTopY(i, k);
            new MeteorFeature(SIBlocks.meteoriteIronOre.id,0,25).generate(world, rand, i, j, k);
        }

        if(rand.nextInt(config.signaliteMeteorChance) == 0) {
            int i = x + rand.nextInt(16);
            int k = z + rand.nextInt(16);
            int j = world.getTopY(i, k);
            new MeteorFeature(SIBlocks.signalumOre.id,0,15).generate(world, rand, i, j, k);
        }

        if(rand.nextInt(config.dilithiumMeteorChance) == 0) {
            int i = x + rand.nextInt(16);
            int k = z + rand.nextInt(16);
            int j = world.getTopY(i, k);
            new MeteorFeature(SIBlocks.dilithiumOre.id,0,5).generate(world, rand, i, j, k);
        }
    }
}
