package sunsetsatellite.signalindustries.data;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.util.Identifier;
import sunsetsatellite.catalyst.core.util.Side;
import sunsetsatellite.catalyst.core.util.model.LayeredCubeModel;
import sunsetsatellite.signalindustries.block.base.ConnectedTextureBlock;
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
                model.inputPreviewTex = Atlases.getTerrain().addTexture(Identifier.of("signalindustries:block/input_overlay"));
                model.outputPreviewTex = Atlases.getTerrain().addTexture(Identifier.of("signalindustries:block/output_overlay"));
                model.bothPreviewTex = Atlases.getTerrain().addTexture(Identifier.of("signalindustries:block/both_overlay"));
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
            if(block instanceof ConnectedTextureBlock conTexBlock){
                String key = "signalindustries:block/" + conTexBlock.textureBase;
                conTexBlock.texCoord = new Atlas.Sprite[]{
                        Atlases.getTerrain().addTexture(Identifier.of(key + "_0")),
                        Atlases.getTerrain().addTexture(Identifier.of(key + "_14")),
                        Atlases.getTerrain().addTexture(Identifier.of(key + "_13")),
                        Atlases.getTerrain().addTexture(Identifier.of(key + "_12")),
                        Atlases.getTerrain().addTexture(Identifier.of(key + "_11")),
                        Atlases.getTerrain().addTexture(Identifier.of(key + "_10")),
                        Atlases.getTerrain().addTexture(Identifier.of(key + "_9")),
                        Atlases.getTerrain().addTexture(Identifier.of(key + "_8")),
                        Atlases.getTerrain().addTexture(Identifier.of(key + "_7")),
                        Atlases.getTerrain().addTexture(Identifier.of(key + "_6")),
                        Atlases.getTerrain().addTexture(Identifier.of(key + "_5")),
                        Atlases.getTerrain().addTexture(Identifier.of(key + "_4")),
                        Atlases.getTerrain().addTexture(Identifier.of(key + "_3")),
                        Atlases.getTerrain().addTexture(Identifier.of(key + "_2")),
                        Atlases.getTerrain().addTexture(Identifier.of(key + "_1")),
                        Atlases.getTerrain().addTexture(Identifier.of(key + "_15"))
                };
            }
        }



        /*Atlases.getTerrain().idToTex.forEach((identifier, texture)->{
            LOGGER.info("{}: {}", identifier.toString(), texture.toString());
        });*/
    }

}
