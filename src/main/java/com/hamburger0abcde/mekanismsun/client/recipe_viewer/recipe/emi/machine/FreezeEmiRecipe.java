package com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe.emi.machine;

import com.hamburger0abcde.mekanismsun.client.gui.bars.GuiCustomDynamicHorizontalRateBar;
import com.hamburger0abcde.mekanismsun.common.recipes.freeze.FreezeRecipe;
import dev.emi.emi.api.widget.WidgetHolder;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.recipe_viewer.RecipeViewerUtils;
import mekanism.client.recipe_viewer.emi.MekanismEmiRecipeCategory;
import mekanism.client.recipe_viewer.emi.recipe.MekanismEmiHolderRecipe;
import mekanism.common.MekanismLang;
import mekanism.common.lib.Color;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.util.text.EnergyDisplay;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

public class FreezeEmiRecipe extends MekanismEmiHolderRecipe<FreezeRecipe> {
    public FreezeEmiRecipe(MekanismEmiRecipeCategory category, RecipeHolder<FreezeRecipe> recipeHolder) {
        super(category, recipeHolder);
        addInputDefinition(recipe.getInput());
        addFluidOutputDefinition(recipe.getOutputDefinition());
    }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        addElement(widgetHolder, GuiFluidGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 7, 17));
        addElement(widgetHolder, GuiFluidGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 151, 17));
        addElement(widgetHolder, new GuiCustomDynamicHorizontalRateBar(this,
                RecipeViewerUtils.barProgressHandler(SharedConstants.TICKS_PER_SECOND), 6, 79, 160, c -> Color.WHITE));
        addElement(widgetHolder, new GuiInnerScreen(this, 47, 17, 82, 60, () -> {
            List<Component> list = new ArrayList<>();
            list.add(MekanismLang.STATUS.translate(MekanismLang.ACTIVE));
            list.add(MekanismLang.USING.translate(EnergyDisplay.of(8_000_000_000L)));
            return list;
        }));
    }
}
