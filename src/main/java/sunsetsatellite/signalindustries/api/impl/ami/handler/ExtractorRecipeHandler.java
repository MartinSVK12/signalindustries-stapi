package sunsetsatellite.signalindustries.api.impl.ami.handler;

import org.jetbrains.annotations.NotNull;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryCrusher;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryExtractor;

public class ExtractorRecipeHandler extends MachineFluidRecipeHandler<RecipeEntryExtractor> {
    public ExtractorRecipeHandler() {
        super("extractor");
    }

    @Override
    public @NotNull Class<RecipeEntryExtractor> getRecipeClass() {
        return RecipeEntryExtractor.class;
    }
}
