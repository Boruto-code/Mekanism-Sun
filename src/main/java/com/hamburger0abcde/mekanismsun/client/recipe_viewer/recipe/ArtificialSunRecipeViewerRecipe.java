package com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.client.recipe_viewer.emi.INamedRVRecipe;
import net.minecraft.resources.ResourceLocation;

public record ArtificialSunRecipeViewerRecipe(ResourceLocation id, ChemicalStackIngredient input, ChemicalStack output)
        implements INamedRVRecipe {
}
