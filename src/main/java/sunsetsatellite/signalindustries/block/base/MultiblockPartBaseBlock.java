package sunsetsatellite.signalindustries.block.base;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.screen.ScreenHandler;
import sunsetsatellite.signalindustries.interfaces.MultiblockPartBlock;
import sunsetsatellite.signalindustries.util.MultiblockPart;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.function.Supplier;

public class MultiblockPartBaseBlock extends TieredMachineBlock implements MultiblockPartBlock {

    public final MultiblockPart.Type type;
    public final MultiblockPart.IO io;

    public MultiblockPartBaseBlock(String identifier, Material material, Tier tier, Supplier<BlockEntity> supplier, String guiId, Class<? extends ScreenHandler> screenHandlerClass, MultiblockPart.Type type, MultiblockPart.IO io) {
        super(identifier, material, tier, supplier, guiId, screenHandlerClass);
        this.type = type;
        this.io = io;
    }

    @Override
    public MultiblockPart.Type getType() {
        return type;
    }

    @Override
    public MultiblockPart.IO getIO() {
        return io;
    }
}
