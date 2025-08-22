package sunsetsatellite.signalindustries.block.base;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.catalyst.core.util.io.InventoryWrapper;
import sunsetsatellite.catalyst.core.util.model.RotatableBlockWithEntity;
import sunsetsatellite.signalindustries.interfaces.Tiered;
import sunsetsatellite.signalindustries.util.ActiveForm;
import sunsetsatellite.signalindustries.util.Tier;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class TieredMachineBlock extends RotatableBlockWithEntity implements Tiered {

    public final Tier tier;
    private final Supplier<BlockEntity> blockEntitySupplier;
    private final Class<? extends ScreenHandler> screenHandlerClass;
    private final String guiId;

    public TieredMachineBlock(Identifier identifier,
                              Material material,
                              Tier tier,
                              Supplier<BlockEntity> supplier,
                              String guiId,
                              Class<? extends ScreenHandler> screenHandlerClass) {
        super(identifier, material);
        this.tier = tier;
        this.blockEntitySupplier = supplier;
        this.screenHandlerClass = screenHandlerClass;
        this.guiId = guiId;
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return blockEntitySupplier.get();
    }

    @Override
    public Tier getTier() {
        return tier;
    }

    @Override
    public @NotNull String[] getTooltip(ItemStack itemStack, String s) {
        return new String[]{s, "Tier: " + tier.getTextColor() + tier.getRank()};
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(x, y, z);
        try {
            GuiHelper.openGUI(player,NAMESPACE.id(guiId), (Inventory) blockEntity, (ScreenHandler) screenHandlerClass.getDeclaredConstructors()[0].newInstance(player.inventory, blockEntity));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void onBreak(World world, int x, int y, int z) {
        BlockEntity blockEntity = world.getBlockEntity(x, y, z);
        if(blockEntity instanceof Inventory inventory) {
            InventoryWrapper wrapper = new InventoryWrapper(inventory);
            wrapper.ejectAll(world, x, y, z);
        }
        super.onBreak(world, x, y, z);
    }

    @Override
    public boolean renderLayer(BlockView view, int x, int y, int z, int meta, int layer) {
        BlockEntity blockEntity = view.getBlockEntity(x, y, z);
        if(blockEntity instanceof ActiveForm te) {
            if(layer == 1 || layer == 2) return te.isActive();
            else return true;
        }
        return layer == 0;
    }
}