package com.hamburger0abcde.mekanismsun.recipes;

import com.hamburger0abcde.mekanismsun.recipes.alloying.AlloyingRecipe;
import com.hamburger0abcde.mekanismsun.recipes.vanilla_input.ItemItemChemicalRecipeInput;
import mekanism.api.recipes.ChemicalToChemicalRecipe;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.vanilla_input.ChemicalRecipeInput;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.registration.impl.RecipeTypeRegistryObject;
import net.minecraft.world.item.crafting.SingleRecipeInput;

public class MSRecipeType {
    public static RecipeTypeRegistryObject<ItemItemChemicalRecipeInput, AlloyingRecipe,
            MSInputRecipeCache.ItemItemChemical<AlloyingRecipe>> ALLOYING;
    public static RecipeTypeRegistryObject<SingleRecipeInput, ItemStackToItemStackRecipe,
            InputRecipeCache.SingleItem<ItemStackToItemStackRecipe>> TRANSMUTATION;
}
