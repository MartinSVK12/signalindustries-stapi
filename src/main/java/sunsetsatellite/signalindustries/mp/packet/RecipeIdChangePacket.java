package sunsetsatellite.signalindustries.mp.packet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import net.modificationstation.stationapi.api.util.SideUtil;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.catalyst.core.util.Connection;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.io.FluidIO;
import sunsetsatellite.catalyst.core.util.io.ItemIO;
import sunsetsatellite.catalyst.core.util.vector.Vec3i;
import sunsetsatellite.signalindustries.block.entity.base.TieredMachineSimpleBlockEntity;
import sunsetsatellite.signalindustries.util.IOPreview;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RecipeIdChangePacket extends Packet implements ManagedPacket<RecipeIdChangePacket>  {

    public static final PacketType<RecipeIdChangePacket> TYPE = PacketType.builder(false, true, RecipeIdChangePacket::new).build();

    public Vec3i pos;
    public int recipeId;
    public Class<? extends BlockEntity> beClass;

    public RecipeIdChangePacket(){}

    public RecipeIdChangePacket(int recipeId, Vec3i position, Class<? extends BlockEntity> clazz) {
        this.pos = position;
        this.recipeId = recipeId;
        this.beClass = clazz;
    }


    @Override
    public void read(DataInputStream stream) {
        try {
            int x = stream.readInt();
            int y = stream.readInt();
            int z = stream.readInt();
            pos = new Vec3i(x, y, z);
            recipeId = stream.readInt();
            beClass = ((Class<? extends BlockEntity>) BlockEntity.idToClass.get(stream.readUTF()));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(pos.x);
            stream.writeInt(pos.y);
            stream.writeInt(pos.z);
            stream.writeInt(recipeId);
            stream.writeUTF(BlockEntity.classToId.get(beClass).toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        SideUtil.run(() -> {},() -> handleServer(networkHandler));
    }

    @Environment(EnvType.SERVER)
    public void handleServer(NetworkHandler networkHandler) {
        PlayerEntity player = PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        BlockEntity e = player.world.getBlockEntity(pos.x, pos.y, pos.z);
        if(e.getClass() == beClass && e instanceof TieredMachineSimpleBlockEntity machine){
            machine.recipeId = this.recipeId;
        }

    }

    @Override
    public int size() {
        return 4 + 3 * 4 + BlockEntity.classToId.get(beClass).toString().length();
    }

    @Override
    public @NotNull PacketType<RecipeIdChangePacket> getType() {
        return TYPE;
    }
}
