package sunsetsatellite.signalindustries.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Formatting;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.vector.Vec3i;

public class PositionChipItem extends TemplateItem implements CustomTooltipProvider {
    public PositionChipItem(Identifier identifier) {
        super(identifier);
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        if (user.isSneaking()) {
            stack.getStationNbt().entries.remove("position");
            user.sendMessage("Position cleared!");
        }
        return super.use(stack, world, user);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        //todo: accepts position code
        NbtCompound position = new NbtCompound();
        position.putInt("x", x);
        position.putInt("y", y);
        position.putInt("z", z);
        position.putInt("dim", world.dimension.id);
        position.putInt("side", side);
        stack.getStationNbt().put("position", position);
        user.sendMessage(String.format("Position set to XYZ: %d, %d, %d!", x, y, z));
        return true;
    }

    public Vec3i getPosition(ItemStack stack){
        if (stack.getStationNbt().contains("position")) {
            NbtCompound position = stack.getStationNbt().getCompound("position");
            return new Vec3i(position.getInt("x"), position.getInt("y"), position.getInt("z"));
        }
        return null;
    }

    @Override
    public @NotNull String[] getTooltip(ItemStack stack, String originalTooltip) {
        NbtCompound position = stack.getStationNbt().getCompound("position");
        if (position.contains("x") && position.contains("y") && position.contains("z") && position.contains("dim") && position.contains("side")) {
            return new String[]{originalTooltip, String.format("XYZ: %s%d, %s%d, %s%d%s | Side: %s%s%s | Dim: %s%d%s", Formatting.RED, position.getInt("x"), Formatting.GREEN, position.getInt("y"), Formatting.BLUE, position.getInt("z"), Formatting.WHITE, Formatting.YELLOW, Direction.getDirectionFromSide(position.getInt("side")).getName(), Formatting.WHITE, Formatting.LIGHT_PURPLE, position.getInt("dim"), Formatting.WHITE)};
        }
        return new String[]{originalTooltip, Formatting.GRAY + "No position stored." + Formatting.WHITE};
    }
}
