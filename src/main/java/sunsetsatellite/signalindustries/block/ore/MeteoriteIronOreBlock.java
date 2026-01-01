package sunsetsatellite.signalindustries.block.ore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import sunsetsatellite.signalindustries.block.base.SIBlock;

import java.util.List;

public class MeteoriteIronOreBlock extends SIBlock {
    public MeteoriteIronOreBlock(String identifier) {
        super(identifier, Material.STONE);
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        return List.of(new ItemStack(Block.IRON_ORE));
    }
}
