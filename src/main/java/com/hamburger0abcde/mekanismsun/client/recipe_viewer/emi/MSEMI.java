package com.hamburger0abcde.mekanismsun.client.recipe_viewer.emi;

import com.hamburger0abcde.mekanismsun.client.recipe_viewer.emi.recipe.ArtificialSunEmiRecipe;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.emi.recipe.ItemItemChemicalToItemStackEmiRecipe;
import mekanism.client.recipe_viewer.emi.recipe.ItemStackToItemStackEmiRecipe;

import com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe.ArtificialSunRecipeViewerRecipe;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.type.MSRecipeViewerRecipeTypes;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import java.util.List;
import java.util.function.BiFunction;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.client.recipe_viewer.emi.INamedRVRecipe;
import mekanism.client.recipe_viewer.emi.MekanismEmiRecipeCategory;
import mekanism.client.recipe_viewer.emi.recipe.MekanismEmiRecipe;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.ItemLike;

/**
 * Native EMI entrypoint for Mekanism: Sun.
 *
 * <p>EMI normally reaches modded JEI recipes through its JEMI bridge, but Mekanism's own
 * {@code registerIngredients} is swallowed by EMI's {@code PluginCallerMixin} whenever the
 * {@code mekanism} namespace has an {@code @EmiEntrypoint} (which it does), so the JEI ingredient
 * for {@code ChemicalStack} never gets registered and the JEMI bridge drops every recipe that uses
 * chemicals. Registering our recipes natively through EMI avoids that path entirely.</p>
 */
@EmiEntrypoint
public class MSEMI implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        addCategoryAndRecipes(registry, MSRecipeViewerRecipeTypes.ARTIFICIAL_SUN,
                ArtificialSunEmiRecipe::new,
                ArtificialSunRecipeViewerRecipe.getArtificialSunRecipes());
        addCategoryAndRecipes(registry, MSRecipeViewerRecipeTypes.ALLOYING,
                ItemItemChemicalToItemStackEmiRecipe::new);
        addCategoryAndRecipes(registry, MSRecipeViewerRecipeTypes.TRANSMUTATION,
                ItemStackToItemStackEmiRecipe::new);
        addCategoryAndRecipes(registry, MSRecipeViewerRecipeTypes.ASSEMBLE,
                ItemItemChemicalToItemStackEmiRecipe::new);
    }

    /**
     * Registers a category plus all recipes of a {@link IMekanismRecipeTypeProvider}-backed recipe type, mirroring
     * Mekanism's own {@code MekanismEmi.addCategoryAndRecipes}.
     */
    public static <RECIPE extends MekanismRecipe<?>, TYPE extends IRecipeViewerRecipeType<RECIPE> & IMekanismRecipeTypeProvider<?, RECIPE, ?>>
    void addCategoryAndRecipes(EmiRegistry registry, TYPE recipeType,
          BiFunction<MekanismEmiRecipeCategory, RecipeHolder<RECIPE>, MekanismEmiRecipe<RECIPE>> recipeCreator) {
        MekanismEmiRecipeCategory category = addCategory(registry, recipeType);
        for (RecipeHolder<RECIPE> recipe : recipeType.getRecipes(registry.getRecipeManager())) {
            registry.addRecipe(recipeCreator.apply(category, recipe));
        }
    }

    /**
     * Registers a category plus recipes supplied as an explicit list (for fake recipe types that have no
     * {@link RecipeHolder}), mirroring Mekanism's own {@code MekanismEmi.addCategoryAndRecipes}.
     */
    public static <RECIPE extends INamedRVRecipe> void addCategoryAndRecipes(EmiRegistry registry,
          IRecipeViewerRecipeType<RECIPE> recipeType, BasicRecipeCreator<RECIPE> recipeCreator, List<RECIPE> recipes) {
        MekanismEmiRecipeCategory category = addCategory(registry, recipeType);
        for (RECIPE recipe : recipes) {
            registry.addRecipe(recipeCreator.create(category, recipe.id(), recipe));
        }
    }

    private static MekanismEmiRecipeCategory addCategory(EmiRegistry registry, IRecipeViewerRecipeType<?> recipeType) {
        MekanismEmiRecipeCategory category = MekanismEmiRecipeCategory.create(recipeType);
        registry.addCategory(category);
        addWorkstations(registry, category, recipeType.workstations());
        return category;
    }

    private static void addWorkstations(EmiRegistry registry, EmiRecipeCategory category, List<ItemLike> workstations) {
        for (ItemLike workstation : workstations) {
            registry.addWorkstation(category, EmiStack.of(workstation.asItem()));
        }
    }

    @FunctionalInterface
    public interface BasicRecipeCreator<RECIPE> {

        MekanismEmiRecipe<RECIPE> create(MekanismEmiRecipeCategory category, net.minecraft.resources.ResourceLocation id, RECIPE recipe);
    }
}
