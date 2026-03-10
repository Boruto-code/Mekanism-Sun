package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.recipes.FreezeRecipe;
import mekanism.api.recipes.vanilla_input.SingleChemicalRecipeInput;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.registration.impl.RecipeTypeRegistryObject;

public class MSRecipeType {
    public static RecipeTypeRegistryObject<SingleChemicalRecipeInput, FreezeRecipe,
            InputRecipeCache.SingleChemical<FreezeRecipe>> FREEZE;
}
