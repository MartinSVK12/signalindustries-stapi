package sunsetsatellite.signalindustries.interfaces;

import sunsetsatellite.signalindustries.util.MultiblockPart;

public interface MultiblockPartBlock {
    MultiblockPart.Type getType();

    MultiblockPart.IO getIO();
}
