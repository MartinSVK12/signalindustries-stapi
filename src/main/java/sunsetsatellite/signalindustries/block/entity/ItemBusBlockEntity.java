package sunsetsatellite.signalindustries.block.entity;

import net.danygames2014.nyalib.fluid.FluidStack;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import sunsetsatellite.signalindustries.block.entity.base.TieredBlockEntity;
import sunsetsatellite.signalindustries.interfaces.MultiblockPartTile;

public class ItemBusBlockEntity extends TieredBlockEntity implements MultiblockPartTile {

    public BlockEntity connectedTo;

    public ItemBusBlockEntity() {
        itemContents = new ItemStack[9];
        fluidContents = new FluidStack[0];
        fluidCapacity = new int[0];
        acceptedFluids.clear();
    }

    @Override
    public void init(Block block) {
        super.init(block);
        switch (tier){
            case BASIC -> itemContents = new ItemStack[4];
            case REINFORCED -> itemContents = new ItemStack[9];
        }
    }

    @Override
    public String getName() {
        return "container.signalindustries.itemBus.name";
    }

    @Override
    public boolean isConnected() {
        return connectedTo != null;
    }

    @Override
    public BlockEntity getConnectedTileEntity() {
        return connectedTo;
    }

    @Override
    public boolean connect(BlockEntity tileEntity) {
        connectedTo = tileEntity;
        return true;
    }
}
