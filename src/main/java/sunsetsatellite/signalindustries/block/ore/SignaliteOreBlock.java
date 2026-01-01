package sunsetsatellite.signalindustries.block.ore;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import sunsetsatellite.signalindustries.block.base.SIBlock;
import sunsetsatellite.signalindustries.data.SIItems;

import java.util.List;
import java.util.Random;

public class SignaliteOreBlock extends SIBlock {
    public SignaliteOreBlock(String identifier) {
        super(identifier, Material.STONE);
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        Random random = new Random();
        return List.of(new ItemStack(SIItems.rawSignalumCrystal, random.nextInt(2) + 2));
    }
}
