package com.hamburger0abcde.mekanismsun.common.recipes;

import com.hamburger0abcde.mekanismsun.common.recipes.vanilla_input.ItemItemChemicalRecipeInput;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.registration.impl.RecipeTypeRegistryObject;
import net.minecraft.world.item.crafting.SingleRecipeInput;

public class MSRecipeType {
    public static RecipeTypeRegistryObject<ItemItemChemicalRecipeInput, BasicItemItemChemicalRecipe,
            MSInputRecipeCache.ItemItemChemical<BasicItemItemChemicalRecipe>> ALLOYING;
    public static RecipeTypeRegistryObject<SingleRecipeInput, ItemStackToItemStackRecipe,
            InputRecipeCache.SingleItem<ItemStackToItemStackRecipe>> TRANSMUTATION;
    public static RecipeTypeRegistryObject<ItemItemChemicalRecipeInput, BasicItemItemChemicalRecipe,
            MSInputRecipeCache.ItemItemChemical<BasicItemItemChemicalRecipe>> ASSEMBLE;
}
