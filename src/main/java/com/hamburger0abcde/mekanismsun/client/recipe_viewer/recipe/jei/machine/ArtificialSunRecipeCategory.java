package com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe.jei.machine;

import com.hamburger0abcde.mekanismsun.MekanismSunLang;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe.ArtificialSunRecipeViewerRecipe;
import com.hamburger0abcde.mekanismsun.multiblock.artificial_sun.ArtificialSunMultiblockData;
import com.mojang.serialization.Codec;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.recipe_viewer.jei.BaseRecipeCategory;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.MekanismLang;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.ICodecHelper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArtificialSunRecipeCategory extends BaseRecipeCategory<ArtificialSunRecipeViewerRecipe> {
    private final GuiGauge<?> input;
    private final GuiGauge<?> output;

    public ArtificialSunRecipeCategory(IGuiHelper helper, IRecipeViewerRecipeType<ArtificialSunRecipeViewerRecipe> recipeType) {
        super(helper, recipeType);
        addElement(new GuiInnerScreen(this, 30, 20, 108, 60, () -> {
            List<Component> list = new ArrayList<>();
            list.add(MekanismLang.STATUS.translate(MekanismLang.ACTIVE));
            list.add(MekanismSunLang.ARTIFICIAL_SUN_BURN_RATE_LIMIT.translate(11.45));
            return list;
        }));
        input = addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD, this, 7, 20));
        output = addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD, this, 145, 20));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, ArtificialSunRecipeViewerRecipe recipe,
                          @NotNull IFocusGroup focusGroup) {
        initChemical(builder, RecipeIngredientRole.INPUT, input, recipe.input().getRepresentations());
        initChemical(builder, RecipeIngredientRole.OUTPUT, output, Collections.singletonList(recipe.output()));
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName(@NotNull ArtificialSunRecipeViewerRecipe recipe) {
        return recipe.id();
    }

    @NotNull
    @Override
    public Codec<ArtificialSunRecipeViewerRecipe> getCodec(@NotNull ICodecHelper codecHelper,
                                                           @NotNull IRecipeManager recipeManager) {
        return ArtificialSunRecipeViewerRecipe.CODEC;
    }
}
