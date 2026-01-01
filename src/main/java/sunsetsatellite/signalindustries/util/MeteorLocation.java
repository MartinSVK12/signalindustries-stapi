package sunsetsatellite.signalindustries.util;


import net.minecraft.block.Block;
import net.minecraft.nbt.NbtCompound;
import sunsetsatellite.catalyst.core.util.vector.Vec3i;
import sunsetsatellite.signalindustries.data.SIBlocks;

public class MeteorLocation {
    public final Type type;
    public final Vec3i location;

    public MeteorLocation(Type type, Vec3i location) {
        this.type = type;
        this.location = location;
    }

    public enum Type {
        IRON,
        SIGNALUM,
        DILITHIUM,
        UNKNOWN;

        public static Type getFromBlock(Block block){
            if(block == SIBlocks.signalumOre){
                return SIGNALUM;
            } else if (block == SIBlocks.dilithiumOre) {
                return DILITHIUM;
            } else if (block == SIBlocks.meteoriteIronOre) {
                return IRON;
            } else {
                return UNKNOWN;
            }
        }

    }

    public void write(NbtCompound tag) {
        tag.putInt("x", location.x);
        tag.putInt("y", location.y);
        tag.putInt("z", location.z);
        tag.putString("type", type.name());
    }

    public static MeteorLocation read(NbtCompound tag) {
        Vec3i location = new Vec3i(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
        Type type = Type.valueOf(tag.getString("type"));
        return new MeteorLocation(type, location);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeteorLocation)) return false;

        MeteorLocation that = (MeteorLocation) o;
        return type == that.type && location.equals(that.location);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }
}
