package sunsetsatellite.signalindustries.data;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.util.Identifier;
import sunsetsatellite.catalyst.core.util.model.LayeredCubeModel;

import static sunsetsatellite.signalindustries.SignalIndustries.LOGGER;
import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class SITextures {

    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        SIItems.itemTextures.forEach((item, texture)->{
            item.setTexture(NAMESPACE.id(texture));
        });

        SIBlocks.blockTextures.forEach((block, texture)->{
           if(block instanceof LayeredCubeModel model){
               texture.defaultTextures.forEach((side, textureName)->{
                   model.getTextureLayers()[0].set(Identifier.of(textureName), side);
               });
               /*if(model.getTextureLayers().length > 2){
                   texture.overbrightTextures.forEach((side, textureName)->{
                       model.getTextureLayers()[2].set(Identifier.of(textureName), side);
                   });
               } else if (model.getTextureLayers().length > 1) {
                   texture.activeTextures.forEach((side, textureName)->{
                       model.getTextureLayers()[1].set(Identifier.of(textureName), side);
                   });
               }*/
           }
        });

        /*SIBlocks.energyFlowing.textureId = Atlases.getTerrain().addTexture(NAMESPACE.id("signalum_energy_transparent")).index;
        SIBlocks.energyStill.textureId = Atlases.getTerrain().addTexture(NAMESPACE.id("signalum_energy_transparent")).index;
        SIBlocks.burntSignalumFlowing.textureId = Atlases.getTerrain().addTexture(NAMESPACE.id("burnt_signalum")).index;
        SIBlocks.burntSignalumStill.textureId = Atlases.getTerrain().addTexture(NAMESPACE.id("burnt_signalum")).index;
        SIBlocks.worldResinFlowing.textureId = Atlases.getTerrain().addTexture(NAMESPACE.id("world_resin_transparent")).index;
        SIBlocks.worldResinStill.textureId = Atlases.getTerrain().addTexture(NAMESPACE.id("world_resin_transparent")).index;*/

        Atlases.getTerrain().idToTex.forEach((identifier, texture)->{
            LOGGER.info("{}: {}", identifier.toString(), texture.toString());
        });
    }

}
