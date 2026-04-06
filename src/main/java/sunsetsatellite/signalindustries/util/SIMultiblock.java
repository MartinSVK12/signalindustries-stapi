package sunsetsatellite.signalindustries.util;

import net.minecraft.nbt.NbtCompound;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.multiblocks.Multiblock;

import java.util.List;

public class SIMultiblock extends Multiblock {

    public List<String> extraBlocks = List.of("reinforcedParallelProcessor", "awakenedParallelProcessor", "awakenedParallelProcessor8x");

    public final Tier tier;

    public SIMultiblock(String modId, Class<?>[] modClasses, String translateKey, NbtCompound data, boolean includeAir, Tier tier) {
        super(modId, modClasses, translateKey, data, includeAir);
        this.tier = tier;
    }

    public SIMultiblock(String modId, Class<?>[] modClasses, String translateKey, String filePath, boolean includeAir, Tier tier) {
        super(modId, modClasses, translateKey, filePath, includeAir);
        this.tier = tier;
    }
}
