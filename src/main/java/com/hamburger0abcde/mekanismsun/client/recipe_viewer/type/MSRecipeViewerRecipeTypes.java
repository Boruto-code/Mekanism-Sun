package com.hamburger0abcde.mekanismsun.client.recipe_viewer.type;

import com.hamburger0abcde.mekanismsun.MekanismSunLang;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe.ArtificialSunRecipeViewerRecipe;
import com.hamburger0abcde.mekanismsun.recipes.FreezeRecipe;
import com.hamburger0abcde.mekanismsun.registries.MSBlocks;
import com.hamburger0abcde.mekanismsun.registries.MSRecipeType;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.client.recipe_viewer.type.FakeRVRecipeType;
import mekanism.client.recipe_viewer.type.RVRecipeTypeWrapper;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import net.minecraft.world.item.crafting.RecipeInput;

@NothingNullByDefault
public class MSRecipeViewerRecipeTypes {
    public static final RVRecipeTypeWrapper<?, FreezeRecipe, ?> FREEZE =
            new RVRecipeTypeWrapper<>(MSRecipeType.FREEZE, FreezeRecipe.class,
                    -28, -13, 144, 60, MSBlocks.FREEZER);

    public static final FakeRVRecipeType<ArtificialSunRecipeViewerRecipe> ARTIFICIAL_SUN =
            new FakeRVRecipeType<>(MSBlocks.ARTIFICIAL_SUN_CASING.getId(), MSBlocks.ARTIFICIAL_SUN_CASING,
                    MekanismSunLang.ARTIFICIAL_SUN, ArtificialSunRecipeViewerRecipe.class,
                    -3, -12, 168, 74, false,
                    MSBlocks.ARTIFICIAL_SUN_CASING, MSBlocks.ARTIFICIAL_SUN_PORT);
}
