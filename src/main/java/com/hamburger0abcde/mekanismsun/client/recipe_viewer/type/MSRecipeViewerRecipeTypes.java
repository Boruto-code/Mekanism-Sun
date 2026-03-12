package com.hamburger0abcde.mekanismsun.client.recipe_viewer.type;

import com.hamburger0abcde.mekanismsun.MekanismSunLang;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe.ArtificialSunRecipeViewerRecipe;
import com.hamburger0abcde.mekanismsun.registries.MSBlocks;
import com.hamburger0abcde.mekanismsun.recipes.MSRecipeType;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.client.recipe_viewer.type.FakeRVRecipeType;
import mekanism.client.recipe_viewer.type.RVRecipeTypeWrapper;

@NothingNullByDefault
public class MSRecipeViewerRecipeTypes {
    public static final FakeRVRecipeType<ArtificialSunRecipeViewerRecipe> ARTIFICIAL_SUN =
            new FakeRVRecipeType<>(MSBlocks.ARTIFICIAL_SUN_CASING.getId(), MSBlocks.ARTIFICIAL_SUN_CASING,
                    MekanismSunLang.ARTIFICIAL_SUN, ArtificialSunRecipeViewerRecipe.class,
                    -3, -12, 168, 74, false,
                    MSBlocks.ARTIFICIAL_SUN_CASING, MSBlocks.ARTIFICIAL_SUN_PORT);
}
