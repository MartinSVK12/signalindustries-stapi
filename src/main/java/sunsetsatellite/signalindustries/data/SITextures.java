package sunsetsatellite.signalindustries.data;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.util.Identifier;
import sunsetsatellite.catalyst.core.util.Side;
import sunsetsatellite.catalyst.core.util.model.LayeredCubeModel;
import sunsetsatellite.signalindustries.block.base.TieredMachineBlock;
import sunsetsatellite.signalindustries.util.MachineTextures;

import java.util.Map;

import static sunsetsatellite.signalindustries.SignalIndustries.LOGGER;
import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class SITextures {

    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        SIItems.itemTextures.forEach((item, texture)->{
            item.setTexture(NAMESPACE.id(texture));
        });

        for (Map.Entry<Block, MachineTextures> entry : SIBlocks.blockTextures.entrySet()) {
            Block block = entry.getKey();
            MachineTextures value = entry.getValue();
            if (block instanceof TieredMachineBlock model) {
                for (Map.Entry<Side, String> e : value.defaultTextures.entrySet()) {
                    Side side = e.getKey();
                    String textureName = e.getValue();
                    if(textureName == null) continue;
                    model.getTextureLayers()[0].set(Identifier.of(textureName), side);
                }
                for (Map.Entry<Side, String> e : value.activeTextures.entrySet()) {
                    Side side = e.getKey();
                    String textureName = e.getValue();
                    if(textureName == null) continue;
                    model.getTextureLayers()[1].set(Identifier.of(textureName), side);
                }
                for (Map.Entry<Side, String> e : value.overbrightTextures.entrySet()) {
                    Side side = e.getKey();
                    String textureName = e.getValue();
                    if(textureName == null) continue;
                    model.getTextureLayers()[2].set(Identifier.of(textureName), side);
                }
            } else if (block instanceof LayeredCubeModel model) {
                for (Map.Entry<Side, String> e : value.defaultTextures.entrySet()) {
                    Side side = e.getKey();
                    String textureName = e.getValue();
                    if(textureName == null) continue;
                    model.getTextureLayers()[0].set(Identifier.of(textureName), side);
                }
            }
        }

        Atlases.getTerrain().idToTex.forEach((identifier, texture)->{
            LOGGER.info("{}: {}", identifier.toString(), texture.toString());
        });
    }

}
