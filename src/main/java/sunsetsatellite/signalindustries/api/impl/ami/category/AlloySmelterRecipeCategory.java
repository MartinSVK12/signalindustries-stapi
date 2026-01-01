package sunsetsatellite.signalindustries.api.impl.ami.category;

import net.glasslauncher.mods.alwaysmoreitems.api.gui.*;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeCategory;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.glasslauncher.mods.alwaysmoreitems.gui.DrawableHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.signalindustries.api.impl.ami.wrapper.MachineRecipeWrapper;
import sunsetsatellite.signalindustries.data.SIFluids;
import sunsetsatellite.signalindustries.util.RecipeProperties;

import javax.annotation.Nonnull;

public class AlloySmelterRecipeCategory implements RecipeCategory {

    @Nonnull
    private final AMIDrawable background;
    @Nonnull
    private final String localizedName;

    @Nonnull
    protected final AnimatedDrawable energy;
    @Nonnull
    protected final AnimatedDrawable arrow;

    @Nonnull
    protected final AMIDrawable energyBack;
    @Nonnull
    protected final AMIDrawable arrowBack;

    @Nonnull
    protected final AMIDrawable slot;
    @Nonnull
    protected final AMIDrawable slotBig;

    public AlloySmelterRecipeCategory() {
        background = DrawableHelper.createDrawable("/assets/signalindustries/stationapi/textures/gui/generic_blank.png", 55, 16, 82, 70);
        localizedName = TranslationStorage.getInstance().get("container.signalindustries.alloySmelter.name");

        StaticDrawable energyDrawable = DrawableHelper.createDrawable("/assets/signalindustries/stationapi/textures/gui/generic_blank.png", 192, 3, 14, 15);
        energy = DrawableHelper.createAnimatedDrawable(energyDrawable, 300, AnimatedDrawable.StartDirection.TOP, true);

        StaticDrawable arrowDrawable = DrawableHelper.createDrawable("/assets/signalindustries/stationapi/textures/gui/generic_blank.png", 202, 19, 24, 18);
        this.arrow = DrawableHelper.createAnimatedDrawable(arrowDrawable, 200, AnimatedDrawable.StartDirection.LEFT, false);

        energyBack = DrawableHelper.createDrawable("/assets/signalindustries/stationapi/textures/gui/generic_blank.png", 177, 3, 14, 15);
        arrowBack = DrawableHelper.createDrawable("/assets/signalindustries/stationapi/textures/gui/generic_blank.png", 177, 19, 24, 18);

        slot = DrawableHelper.createDrawable("/assets/signalindustries/stationapi/textures/gui/generic_blank.png", 177, 38, 18, 18);
        slotBig = DrawableHelper.createDrawable("/assets/signalindustries/stationapi/textures/gui/generic_blank.png", 177, 57, 26, 26);
    }

    @Override
    public @NotNull String getUid() {
        return "signalindustries:recipe/alloy_smelter";
    }

    @Override
    public @NotNull String getTitle() {
        return localizedName;
    }

    @Override
    public @NotNull AMIDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        slot.draw(minecraft, 0,0);
        slot.draw(minecraft, -28,0);
        slot.draw(minecraft, 0,36);
        slotBig.draw(minecraft, 56,14);
        energyBack.draw(minecraft, 2, 19);
        arrowBack.draw(minecraft, 24, 18);
    }

    @Override
    public void drawAnimations(Minecraft minecraft) {
        energy.draw(minecraft, 2, 19);
        arrow.draw(minecraft, 24, 18);
    }

    @Override
    public void setRecipe(@NotNull RecipeLayout recipeLayout, @NotNull RecipeWrapper recipeWrapper) {
        GuiItemStackGroup stacks = recipeLayout.getItemStacks();

        stacks.init(0, true, 0, 0);
        stacks.init(2, true, -28, 0);
        stacks.init(1, false, 60, 18);
        stacks.init(3, false, 0, 36);

        stacks.setFromRecipe(0, recipeWrapper.getInputs().get(0));
        stacks.setFromRecipe(2, recipeWrapper.getInputs().get(1));
        stacks.setFromRecipe(1, recipeWrapper.getOutputs());
        RecipeProperties data = ((MachineRecipeWrapper) recipeWrapper).recipe.getData();
        stacks.setFromRecipe(3, new ItemStack(SIFluids.ENERGY.getFlowingBlock(), (int) (data.cost * (data.ticks/200.0f))));
    }
}
