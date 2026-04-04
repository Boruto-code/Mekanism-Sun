package com.hamburger0abcde.mekanismsun.client.recipe_viewer.type;

import com.hamburger0abcde.mekanismsun.common.MekanismSunLang;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe.ArtificialSunRecipeViewerRecipe;
import com.hamburger0abcde.mekanismsun.common.recipes.BasicItemItemChemicalRecipe;
import com.hamburger0abcde.mekanismsun.common.registries.MSBlocks;
import com.hamburger0abcde.mekanismsun.common.recipes.MSRecipeType;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.client.recipe_viewer.type.FakeRVRecipeType;
import mekanism.client.recipe_viewer.type.RVRecipeTypeWrapper;

@NothingNullByDefault
public class MSRecipeViewerRecipeTypes {
    public static final RVRecipeTypeWrapper<?, BasicItemItemChemicalRecipe, ?> ALLOYING =
            new RVRecipeTypeWrapper<>(MSRecipeType.ALLOYING, BasicItemItemChemicalRecipe.class,
                    -28, -16, 144, 54, MSBlocks.ALLOYER);
    public static final RVRecipeTypeWrapper<?, ItemStackToItemStackRecipe, ?> TRANSMUTATION =
            new RVRecipeTypeWrapper<>(MSRecipeType.TRANSMUTATION, ItemStackToItemStackRecipe.class,
                    -28, -16, 144, 54, MSBlocks.TRANSMUTATOR);
    public static final RVRecipeTypeWrapper<?, BasicItemItemChemicalRecipe, ?> ASSEMBLE =
            new RVRecipeTypeWrapper<>(MSRecipeType.ASSEMBLE, BasicItemItemChemicalRecipe.class,
                    -28, -16, 144, 54, MSBlocks.ASSEMBLER);

    public static final FakeRVRecipeType<ArtificialSunRecipeViewerRecipe> ARTIFICIAL_SUN =
            new FakeRVRecipeType<>(MSBlocks.ARTIFICIAL_SUN_CASING.getId(), MSBlocks.ARTIFICIAL_SUN_CASING,
                    MekanismSunLang.ARTIFICIAL_SUN, ArtificialSunRecipeViewerRecipe.class,
                    -3, -12, 168, 74, false,
                    MSBlocks.ARTIFICIAL_SUN_CASING, MSBlocks.ARTIFICIAL_SUN_PORT);
}
