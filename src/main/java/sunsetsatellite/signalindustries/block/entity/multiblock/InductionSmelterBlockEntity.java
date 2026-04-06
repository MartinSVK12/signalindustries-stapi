package sunsetsatellite.signalindustries.block.entity.multiblock;

import net.minecraft.block.Block;
import sunsetsatellite.catalyst.multiblocks.MultiblockInstance;
import sunsetsatellite.signalindustries.block.entity.base.TieredMultiblockBlockEntity;
import sunsetsatellite.signalindustries.data.SIMultiblocks;
import sunsetsatellite.signalindustries.data.SIRecipes;
import sunsetsatellite.signalindustries.util.Tier;

public class InductionSmelterBlockEntity extends TieredMultiblockBlockEntity {

    @Override
    public void init(Block block) {
        super.init(block);

        usesEnergy = true;
        usesItemInput = true;
        usesItemOutput = true;
        minimumEnergyTier = Tier.BASIC;
        minimumItemInputTier = Tier.BASIC;
        minimumItemOutputTier = Tier.BASIC;

        recipeGroup = SIRecipes.INDUCTION_SMELTER;

        baseParallel = 16;
    }

    @Override
    public void tick() {
        if(multiblock == null) {
            multiblock = new MultiblockInstance(this, SIMultiblocks.basicInductionSmelter);
        }
        super.tick();
    }

    @Override
    public String getName() {
        return "container.signalindustries.inductionSmelter.name";
    }
}
