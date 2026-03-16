package com.hamburger0abcde.mekanismsun.client.recipe_viewer.jei;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe.ArtificialSunRecipeViewerRecipe;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe.jei.machine.AlloyingRecipeCategory;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe.jei.machine.ArtificialSunRecipeCategory;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.type.MSRecipeViewerRecipeTypes;
import com.hamburger0abcde.mekanismsun.recipes.MSRecipeType;
import mekanism.client.recipe_viewer.jei.CatalystRegistryHelper;
import mekanism.client.recipe_viewer.jei.RecipeRegistryHelper;
import mekanism.client.recipe_viewer.jei.machine.ItemStackToItemStackRecipeCategory;
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
        registry.addRecipeCategories(new AlloyingRecipeCategory(guiHelper, MSRecipeViewerRecipeTypes.ALLOYING));
        registry.addRecipeCategories(new ItemStackToItemStackRecipeCategory(guiHelper, MSRecipeViewerRecipeTypes.TRANSMUTATION));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        RecipeRegistryHelper.register(registry, MSRecipeViewerRecipeTypes.ARTIFICIAL_SUN,
                ArtificialSunRecipeViewerRecipe.getArtificialSunRecipes());
        RecipeRegistryHelper.register(registry, MSRecipeViewerRecipeTypes.ALLOYING, MSRecipeType.ALLOYING);
        RecipeRegistryHelper.register(registry, MSRecipeViewerRecipeTypes.TRANSMUTATION, MSRecipeType.TRANSMUTATION);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        CatalystRegistryHelper.register(registry, MSRecipeViewerRecipeTypes.ARTIFICIAL_SUN);
        CatalystRegistryHelper.register(registry, MSRecipeViewerRecipeTypes.ALLOYING);
        CatalystRegistryHelper.register(registry, MSRecipeViewerRecipeTypes.TRANSMUTATION);
    }
}
