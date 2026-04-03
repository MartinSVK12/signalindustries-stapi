package sunsetsatellite.signalindustries.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.dimension.PortalForcer;
import net.modificationstation.stationapi.api.block.CustomPortal;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;

import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class EternityPortalBlock extends TemplateBlock implements CustomPortal {
    public EternityPortalBlock(String id) {
        super(Identifier.of(NAMESPACE,id), Material.NETHER_PORTAL);
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        switchDimension(player);
        return true;
    }

    @Override
    public Identifier getDimension(PlayerEntity playerEntity) {
        if(playerEntity.world.dimension.id == 0){
            return Identifier.of(NAMESPACE,"eternity");
        } else {
            return Identifier.of(Namespace.MINECRAFT,"overworld");
        }
    }

    @Override
    public PortalForcer getTravelAgent(PlayerEntity playerEntity) {
        return new PortalForcer() {
            @Override
            public boolean createPortal(World world, Entity entity) {
                return false;
            }
        };
    }
}
