package com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe.jei.machine;

import com.hamburger0abcde.mekanismsun.client.recipe_viewer.type.MSRecipeViewerRecipeTypes;
import com.hamburger0abcde.mekanismsun.recipes.FreezeRecipe;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.recipe_viewer.jei.BaseRecipeCategory;
import mekanism.client.recipe_viewer.jei.HolderRecipeCategory;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

public class FreezeRecipeCategory extends HolderRecipeCategory<FreezeRecipe> {
    private final GuiGauge<?> input;
    private final GuiGauge<?> output;

    public FreezeRecipeCategory(IGuiHelper helper) {
        super(helper, MSRecipeViewerRecipeTypes.FREEZE);
        input = addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD, this, 30, 15));
        output = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD, this, 143, 15));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, RecipeHolder<FreezeRecipe> recipeHolder, @NotNull IFocusGroup focusGroup) {
        FreezeRecipe recipe = recipeHolder.value();
        initChemical(builder, RecipeIngredientRole.INPUT, input, recipe.getInput().getRepresentations());
        initFluid(builder, RecipeIngredientRole.OUTPUT, output, recipe.getOutputDefinition());
    }
}
