package com.hamburger0abcde.mekanismsun.client.gui;

import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityFreezer;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class GuiFreezer extends GuiMekanismTile<TileEntityFreezer, MekanismTileContainer<TileEntityFreezer>> {
    public GuiFreezer(MekanismTileContainer<TileEntityFreezer> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        titleLabelY += 4;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiVerticalPowerBar(this, tile.getEnergyContainer(), 155, 20))
                .warning(WarningType.NOT_ENOUGH_ENERGY, tile.getWarningCheck(RecipeError.NOT_ENOUGH_ENERGY))
                .warning(WarningType.NOT_ENOUGH_ENERGY_REDUCED_RATE, tile.getWarningCheck(RecipeError.NOT_ENOUGH_ENERGY_REDUCED_RATE));
        addRenderableWidget(new GuiChemicalGauge(() -> tile.inputChemicalTank, () -> tile.getChemicalTanks(null),
                GaugeType.STANDARD, this, 25, 5))
                .warning(WarningType.NO_MATCHING_RECIPE, tile.getWarningCheck(TileEntityFreezer.NOT_ENOUGH_CHEMICAL_INPUT_ERROR));
        addRenderableWidget(new GuiFluidGauge(() -> tile.outputFluidTank, () -> tile.getFluidTanks(null),
                GaugeType.STANDARD, this, 133, 5))
                .warning(WarningType.NO_SPACE_IN_OUTPUT, tile.getWarningCheck(TileEntityFreezer.NOT_ENOUGH_FLUID_OUTPUT_ERROR));
        addRenderableWidget(new GuiProgress(tile::getScaledProgress, ProgressType.RIGHT, this, 77, 43))
                .warning(WarningType.INPUT_DOESNT_PRODUCE_OUTPUT, tile.getWarningCheck(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        renderInventoryText(guiGraphics);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }
}
