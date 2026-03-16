package com.hamburger0abcde.mekanismsun.client.recipe_viewer.type;

import com.hamburger0abcde.mekanismsun.MekanismSunLang;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe.ArtificialSunRecipeViewerRecipe;
import com.hamburger0abcde.mekanismsun.recipes.alloying.AlloyingRecipe;
import com.hamburger0abcde.mekanismsun.registries.MSBlocks;
import com.hamburger0abcde.mekanismsun.recipes.MSRecipeType;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.client.recipe_viewer.type.FakeRVRecipeType;
import mekanism.client.recipe_viewer.type.RVRecipeTypeWrapper;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import net.minecraft.world.item.crafting.RecipeInput;

@NothingNullByDefault
public class MSRecipeViewerRecipeTypes {
    public static final RVRecipeTypeWrapper<?, AlloyingRecipe, ?> ALLOYING =
            new RVRecipeTypeWrapper<>(MSRecipeType.ALLOYING, AlloyingRecipe.class,
                    -28, -16, 144, 54, MSBlocks.ALLOYER);
    public static final RVRecipeTypeWrapper<?, ItemStackToItemStackRecipe, ?> TRANSMUTATION =
            new RVRecipeTypeWrapper<>(MSRecipeType.TRANSMUTATION, ItemStackToItemStackRecipe.class,
                    -28, -16, 144, 54, MSBlocks.TRANSMUTATOR);

    public static final FakeRVRecipeType<ArtificialSunRecipeViewerRecipe> ARTIFICIAL_SUN =
            new FakeRVRecipeType<>(MSBlocks.ARTIFICIAL_SUN_CASING.getId(), MSBlocks.ARTIFICIAL_SUN_CASING,
                    MekanismSunLang.ARTIFICIAL_SUN, ArtificialSunRecipeViewerRecipe.class,
                    -3, -12, 168, 74, false,
                    MSBlocks.ARTIFICIAL_SUN_CASING, MSBlocks.ARTIFICIAL_SUN_PORT);
}
