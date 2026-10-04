package com.hamburger0abcde.mekanismsun.client.recipe_viewer.emi.recipe;

import com.hamburger0abcde.mekanismsun.common.recipes.BasicItemItemChemicalRecipe;
import dev.emi.emi.api.widget.WidgetHolder;
import mekanism.client.gui.element.GuiUpArrow;
import mekanism.client.gui.element.bar.GuiEmptyBar;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.recipe_viewer.RecipeViewerUtils;
import mekanism.client.recipe_viewer.emi.MekanismEmiRecipeCategory;
import mekanism.client.recipe_viewer.emi.recipe.MekanismEmiHolderRecipe;
import mekanism.common.inventory.container.slot.SlotOverlay;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ItemItemChemicalToItemStackEmiRecipe extends MekanismEmiHolderRecipe<BasicItemItemChemicalRecipe> {

    private static final int PROCESS_TIME = 100;

    public ItemItemChemicalToItemStackEmiRecipe(MekanismEmiRecipeCategory category,
                                                RecipeHolder<BasicItemItemChemicalRecipe> recipeHolder) {
        super(category, recipeHolder);
        addInputDefinition(recipe.getChemicalInput());
        addInputDefinition(recipe.getMainInput());
        addInputDefinition(recipe.getExtraInput());
        addItemOutputDefinition(recipe.getOutputDefinition());
    }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        addElement(widgetHolder, new GuiUpArrow(this, 68, 38));
        initTank(widgetHolder, new GuiEmptyBar(this, 68, 36, 6, 12), input(0));
        addSlot(widgetHolder, SlotType.INPUT, 64, 17, input(1));
        addSlot(widgetHolder, SlotType.EXTRA, 64, 53, input(2));
        addSlot(widgetHolder, SlotType.OUTPUT, 116, 35, output(0)).recipeContext(this);
        addSlot(widgetHolder, SlotType.POWER, 141, 35).with(SlotOverlay.POWER);
        addElement(widgetHolder, new GuiVerticalPowerBar(this, RecipeViewerUtils.FULL_BAR, 164, 15));
        addSimpleProgress(widgetHolder, ProgressType.BAR, 86, 38, PROCESS_TIME);
    }
}