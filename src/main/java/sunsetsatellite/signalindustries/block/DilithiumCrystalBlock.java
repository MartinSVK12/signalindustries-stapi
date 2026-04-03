package sunsetsatellite.signalindustries.block;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import sunsetsatellite.signalindustries.block.base.SIBlock;
import sunsetsatellite.signalindustries.data.SIItems;

import java.util.List;

import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class DilithiumCrystalBlock extends SIBlock {
    public DilithiumCrystalBlock(String id) {
        super(id, Material.GLASS);
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        return List.of(new ItemStack(SIItems.dilithiumShard,1));
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public int getRenderLayer() {
        return 1;
    }
}
