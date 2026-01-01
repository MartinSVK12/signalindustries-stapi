package sunsetsatellite.signalindustries.api.impl.ami.wrapper;

import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.glasslauncher.mods.alwaysmoreitems.gui.AMITextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Formatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachine;
import sunsetsatellite.signalindustries.recipes.entry.base.RecipeEntryMachineFluid;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.signalindustries.util.RecipeSymbol;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MachineFluidRecipeWrapper implements RecipeWrapper {

    public RecipeEntryMachineFluid recipe;

    public MachineFluidRecipeWrapper(RecipeEntryMachineFluid recipe) {
        this.recipe = recipe;
    }

    @Override
    public List<?> getInputs() {
        List<Object> inputs = new ArrayList<>();
        for (RecipeSymbol symbol : recipe.getInput()) {
            if(symbol == null) {
                inputs.add(null);
                continue;
            }
            if(symbol.hasFluid()){
                inputs.add(symbol.resolveFluids().stream().map(FS->new ItemStack(FS.fluid.getFlowingBlock(),FS.amount)).toList());
            } else {
                inputs.add(symbol.resolve());
            }
        }
        return inputs;
    }

    @Override
    public List<?> getOutputs() {
        return Collections.singletonList(new ItemStack(recipe.getOutput().fluid.getFlowingBlock(),recipe.getOutput().amount));
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        RecipeProperties data = recipe.getData();
        AMITextRenderer.INSTANCE.draw(data.ticks+"t",recipeWidth/2 - 15,recipeHeight/2 - 30, Color.DARK_GRAY.getRGB());
        if(data.id > 0){
            AMITextRenderer.INSTANCE.draw("ID: "+data.id,recipeWidth/2 - 15,recipeHeight/2 + 5, Color.DARK_GRAY.getRGB());
        }
        if(data.thisTierOnly){
            AMITextRenderer.INSTANCE.drawWithShadow("Only at: "+data.tier.getTextColor()+data.tier.getRank() + Formatting.WHITE,-25,recipeHeight,0xFFF0F0F0);
        } else {
            AMITextRenderer.INSTANCE.drawWithShadow("Minimum tier: "+data.tier.getTextColor()+data.tier.getRank() + Formatting.WHITE,-25,recipeHeight,0xFFF0F0F0);
        }

    }

    @Override
    public void drawAnimations(@NotNull Minecraft minecraft, int recipeWidth, int recipeHeight) {

    }

    @Override
    public @Nullable ArrayList<Object> getTooltip(int mouseX, int mouseY) {
        return null;
    }

    @Override
    public boolean handleClick(@NotNull Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
