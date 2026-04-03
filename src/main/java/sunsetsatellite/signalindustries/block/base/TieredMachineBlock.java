package sunsetsatellite.signalindustries.block.base;

import net.glasslauncher.mods.alwaysmoreitems.api.Rarity;
import net.glasslauncher.mods.alwaysmoreitems.api.RarityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.io.FluidIO;
import sunsetsatellite.catalyst.core.util.io.InventoryWrapper;
import sunsetsatellite.catalyst.core.util.io.ItemIO;
import sunsetsatellite.catalyst.core.util.model.RotatableBlockWithEntity;
import sunsetsatellite.catalyst.core.util.vector.Vec3i;
import sunsetsatellite.signalindustries.interfaces.HasIOPreview;
import sunsetsatellite.signalindustries.interfaces.Tiered;
import sunsetsatellite.signalindustries.util.ActiveForm;
import sunsetsatellite.signalindustries.util.IOPreview;
import sunsetsatellite.signalindustries.util.Tier;
import sunsetsatellite.signalindustries.util.TierRarity;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class TieredMachineBlock extends RotatableBlockWithEntity implements Tiered, CustomTooltipProvider, RarityProvider {

    public final Tier tier;
    private final Supplier<BlockEntity> blockEntitySupplier;
    private final Class<? extends ScreenHandler> screenHandlerClass;
    private final String guiId;

    public Atlas.Sprite inputPreviewTex;
    public Atlas.Sprite outputPreviewTex;
    public Atlas.Sprite bothPreviewTex;

    public static boolean ioConfig = false;
    public static IOPreview ioType = IOPreview.NONE;
    public static Vec3i ioPos = null;
    public static World ioWorld = null;

    public TieredMachineBlock(String identifier,
                              Material material,
                              Tier tier,
                              Supplier<BlockEntity> supplier,
                              String guiId,
                              Class<? extends ScreenHandler> screenHandlerClass) {
        super(Identifier.of(NAMESPACE, identifier), material);
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
    public boolean renderLayer(BlockView view, BlockStateView blockStateView, int x, int y, int z, int meta, int layer) {
        BlockEntity blockEntity = view.getBlockEntity(x, y, z);
        if(blockEntity instanceof HasIOPreview preview){
            if (preview.getPreview() != IOPreview.NONE) return true;
        }
        if(blockEntity instanceof ActiveForm te) {
            if(layer == 1 || layer == 2) return te.isActive();
            else return true;
        }
        if(layer == 2 && ioConfig) return true;
        return layer == 0;
    }

    @Override
    public Atlas.Sprite getOverlayTexture(BlockView view, BlockStateView blockStateView, int x, int y, int z, int meta, int side) {
        BlockEntity blockEntity = view.getBlockEntity(x, y, z);
        if(ioConfig && ioPos != null && ioWorld != null){
            blockEntity = ioWorld.getBlockEntity(ioPos.x, ioPos.y, ioPos.z);
            return preview(ioType,blockEntity,side);
        }
        if(blockEntity instanceof HasIOPreview preview){
            if (preview.getPreview() != IOPreview.NONE) {
                return preview(preview.getPreview(),blockEntity,side);
            }
        }
        return super.getOverlayTexture(view, blockStateView, x, y, z, meta, side);
    }

    public Atlas.Sprite preview(IOPreview preview, BlockEntity blockEntity, int side){
        switch (preview) {
            case ITEM -> {
                if(blockEntity instanceof ItemIO itemIO){
                    switch (itemIO.getItemIOForSide(Direction.getDirectionFromSide(side))) {
                        case INPUT -> {
                            return inputPreviewTex;
                        }
                        case OUTPUT -> {
                            return outputPreviewTex;
                        }
                        case BOTH -> {
                            return bothPreviewTex;
                        }
                        case NONE -> {
                            return null;
                        }
                    }
                }
            }
            case FLUID -> {
                if(blockEntity instanceof FluidIO fluidIO){
                    switch (fluidIO.getFluidIOForSide(Direction.getDirectionFromSide(side))) {
                        case INPUT -> {
                            return inputPreviewTex;
                        }
                        case OUTPUT -> {
                            return outputPreviewTex;
                        }
                        case BOTH -> {
                            return bothPreviewTex;
                        }
                        case NONE -> {
                            return null;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Rarity getRarity(ItemStack itemStack) {
        return TierRarity.values()[getTier().ordinal()].getRarity();
    }
}