package com.hamburger0abcde.mekanismsun.client.gui;

import com.hamburger0abcde.mekanismsun.tile.artificial_sun.TileEntityArtificialSunCasing;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class GuiArtificialSun extends GuiMekanismTile<TileEntityArtificialSunCasing,
        MekanismTileContainer<TileEntityArtificialSunCasing>> {

    public GuiArtificialSun(MekanismTileContainer<TileEntityArtificialSunCasing> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        imageHeight += 16;
        inventoryLabelY = imageHeight - 92;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiChemicalGauge(() -> tile.getMultiblock().fuelTank,
                () -> tile.getMultiblock().getChemicalTanks(null), GaugeType.STANDARD, this, 7, 17));
        addRenderableWidget(new GuiChemicalGauge(() -> tile.getMultiblock().wasteTank,
                () -> tile.getMultiblock().getChemicalTanks(null), GaugeType.STANDARD, this, 151, 17));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        renderInventoryText(guiGraphics);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }
}
