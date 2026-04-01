package com.hamburger0abcde.mekanismsun.common.recipes;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.TripleInputRecipeCache;
import mekanism.common.recipe.lookup.cache.type.ChemicalInputCache;
import mekanism.common.recipe.lookup.cache.type.ItemInputCache;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.TriPredicate;

import java.util.function.Function;

public class MSInputRecipeCache {
    public static class ItemItemChemical<RECIPE extends MekanismRecipe<?>
            & TriPredicate<ItemStack, ItemStack, ChemicalStack>> extends TripleInputRecipeCache<ItemStack, ItemStackIngredient,
            ItemStack, ItemStackIngredient, ChemicalStack, ChemicalStackIngredient,
            RECIPE, ItemInputCache<RECIPE>, ItemInputCache<RECIPE>, ChemicalInputCache<RECIPE>> {

        public ItemItemChemical(MekanismRecipeType<?,RECIPE, ?> recipeType,
                                Function<RECIPE, ItemStackIngredient> inputAExtractor,
                                Function<RECIPE, ItemStackIngredient> inputBExtractor,
                                Function<RECIPE, ChemicalStackIngredient> inputCExtractor) {
            super(recipeType, inputAExtractor, new ItemInputCache<>(), inputBExtractor, new ItemInputCache<>(),
                    inputCExtractor, new ChemicalInputCache<>());
        }
    }
}
