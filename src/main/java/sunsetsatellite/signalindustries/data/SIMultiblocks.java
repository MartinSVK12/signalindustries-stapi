package sunsetsatellite.signalindustries.data;

import net.minecraft.nbt.NbtCompound;
import sunsetsatellite.catalyst.multiblocks.Multiblock;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.util.SIMultiblock;
import sunsetsatellite.signalindustries.util.Tier;

import static sunsetsatellite.signalindustries.SignalIndustries.LOGGER;

public class SIMultiblocks {

    public static SIMultiblock basicInductionSmelter;

    public static void init() {
        LOGGER.info("Initializing multiblocks...");
        NbtCompound data = new NbtCompound();
        //new StructureBuilder('M', SIBlocks.basicInductionSmelter, 0);
        basicInductionSmelter = new SIMultiblock(SignalIndustries.NAMESPACE.toString(), new Class[]{SIBlocks.class}, "basicInductionSmelter", "multiblock/basic_induction_smelter", false, Tier.BASIC);
        Multiblock.multiblocks.put("basicInductionSmelter", basicInductionSmelter);
    }

}
