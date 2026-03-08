package com.hamburger0abcde.mekanismsun.client.recipe_viewer.jei;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe.ArtificialSunRecipeViewerRecipe;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe.jei.machine.ArtificialSunRecipeCategory;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.type.MSRecipeViewerRecipeTypes;
import mekanism.client.recipe_viewer.jei.CatalystRegistryHelper;
import mekanism.client.recipe_viewer.jei.RecipeRegistryHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class MSJEI implements IModPlugin {
    @NotNull
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(MekanismSun.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

        registry.addRecipeCategories(new ArtificialSunRecipeCategory(guiHelper, MSRecipeViewerRecipeTypes.ARTIFICIAL_SUN));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        RecipeRegistryHelper.register(registry, MSRecipeViewerRecipeTypes.ARTIFICIAL_SUN,
                ArtificialSunRecipeViewerRecipe.getArtificialSunRecipes());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        CatalystRegistryHelper.register(registry, MSRecipeViewerRecipeTypes.ARTIFICIAL_SUN);
    }
}
