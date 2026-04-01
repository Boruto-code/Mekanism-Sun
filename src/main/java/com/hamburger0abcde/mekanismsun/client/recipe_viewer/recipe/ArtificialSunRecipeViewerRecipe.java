package com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.common.registries.MSChemicals;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mekanism.api.SerializationConstants;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.client.recipe_viewer.RecipeViewerUtils;
import mekanism.client.recipe_viewer.emi.INamedRVRecipe;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.List;

public record ArtificialSunRecipeViewerRecipe(ResourceLocation id, ChemicalStackIngredient input, ChemicalStack output)
        implements INamedRVRecipe {

    public static final Codec<ArtificialSunRecipeViewerRecipe> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf(SerializationConstants.ID).forGetter(ArtificialSunRecipeViewerRecipe::id),
                    ChemicalStackIngredient.CODEC.fieldOf(SerializationConstants.INPUT).forGetter(ArtificialSunRecipeViewerRecipe::input),
                    ChemicalStack.CODEC.fieldOf(SerializationConstants.OUTPUT).forGetter(ArtificialSunRecipeViewerRecipe::output)
            ).apply(instance, ArtificialSunRecipeViewerRecipe::new)
    );

    public static List<ArtificialSunRecipeViewerRecipe> getArtificialSunRecipes() {
        return Collections.singletonList(new ArtificialSunRecipeViewerRecipe(
                RecipeViewerUtils.synthetic(MekanismSun.rl("helium"), "artificial_sun"),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.HYDROGEN, 1),
                MSChemicals.HELIUM.asStack(1)
        ));
    }
}
