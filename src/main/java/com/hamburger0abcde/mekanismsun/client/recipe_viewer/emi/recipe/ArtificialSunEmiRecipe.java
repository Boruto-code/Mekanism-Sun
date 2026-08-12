package com.hamburger0abcde.mekanismsun.client.recipe_viewer.emi.recipe;

import com.hamburger0abcde.mekanismsun.common.MekanismSunLang;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe.ArtificialSunRecipeViewerRecipe;
import dev.emi.emi.api.widget.WidgetHolder;
import java.util.ArrayList;
import java.util.List;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.recipe_viewer.emi.MekanismEmiRecipeCategory;
import mekanism.client.recipe_viewer.emi.recipe.MekanismEmiRecipe;
import mekanism.common.MekanismLang;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ArtificialSunEmiRecipe extends MekanismEmiRecipe<ArtificialSunRecipeViewerRecipe> {

    public ArtificialSunEmiRecipe(MekanismEmiRecipeCategory category, ResourceLocation id, ArtificialSunRecipeViewerRecipe recipe) {
        super(category, id, recipe);
        addInputDefinition(recipe.input());
        addChemicalOutputDefinition(List.of(recipe.output()));
    }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        addElement(widgetHolder, new GuiInnerScreen(this, 30, 20, 108, 60, () -> {
            List<Component> list = new ArrayList<>();
            list.add(MekanismLang.STATUS.translate(MekanismLang.ACTIVE));
            list.add(MekanismSunLang.ARTIFICIAL_SUN_BURN_RATE_LIMIT.translate(11.45));
            return list;
        }));
        initTank(widgetHolder, GuiChemicalGauge.getDummy(GaugeType.STANDARD, this, 7, 20), input(0));
        initTank(widgetHolder, GuiChemicalGauge.getDummy(GaugeType.STANDARD, this, 145, 20), output(0)).recipeContext(this);
    }
}