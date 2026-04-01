package com.hamburger0abcde.mekanismsun.client.gui;

import com.hamburger0abcde.mekanismsun.common.MekanismSunLang;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.type.MSRecipeViewerRecipeTypes;
import com.hamburger0abcde.mekanismsun.common.config.MSConfig;
import com.hamburger0abcde.mekanismsun.common.multiblock.artificial_sun.ArtificialSunMultiblockData;
import com.hamburger0abcde.mekanismsun.common.network.to_server.PacketMSGuiInteract;
import com.hamburger0abcde.mekanismsun.common.tiles.artificial_sun.TileEntityArtificialSunCasing;
import mekanism.api.math.MathUtils;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiEnergyGauge;
import mekanism.client.gui.element.text.GuiTextField;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.network.PacketUtils;
import mekanism.common.util.UnitDisplayUtils;
import mekanism.common.util.text.InputValidator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GuiArtificialSun extends GuiMekanismTile<TileEntityArtificialSunCasing,
        MekanismTileContainer<TileEntityArtificialSunCasing>> {

    private GuiTextField rateLimitField;

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
                () -> tile.getMultiblock().getChemicalTanks(null), GaugeType.STANDARD, this, 7, 20));
        addRenderableWidget(new GuiChemicalGauge(() -> tile.getMultiblock().wasteTank,
                () -> tile.getMultiblock().getChemicalTanks(null), GaugeType.STANDARD, this, 125, 20));
        addRenderableWidget(new GuiEnergyGauge(tile.getMultiblock().energyContainer, GaugeType.STANDARD, this, 150, 20));

        addRenderableWidget(new GuiInnerScreen(this, 30, 20, 80, 42, () -> {
            List<Component> list = new ArrayList<>();
            ArtificialSunMultiblockData multiblock = tile.getMultiblock();
            boolean active = multiblock.lastBurnRate > 0;
            list.add(MekanismLang.STATUS.translate(active ? MekanismLang.ACTIVE : MekanismLang.IDLE));
            list.add(MekanismSunLang.ARTIFICIAL_SUN_BURN_RATE_LIMIT.translate(multiblock.rateLimit));
            return list;
        }).recipeViewerCategories(MSRecipeViewerRecipeTypes.ARTIFICIAL_SUN));
        rateLimitField = addRenderableWidget(new GuiTextField(this, 40, 68, 54, 12));
        rateLimitField.setEnterHandler(this::setRateLimit);
        rateLimitField.setInputValidator(InputValidator.DECIMAL);
        long adjustedMaxBurn = Math.max(0, MathUtils.clampToLong(MSConfig.GENERAL.sunMaxBurnRate.get()) - 1);
        rateLimitField.setMaxLength(Long.toString(adjustedMaxBurn).length() + 3);
        rateLimitField.addCheckmarkButton(this::setRateLimit);
    }

    private void setRateLimit() {
        if (!rateLimitField.getText().isEmpty()) {
            try {
                double limit = Double.parseDouble(rateLimitField.getText());
                if (limit >= 0 && limit <= MSConfig.GENERAL.sunMaxBurnRate.get()) {
                    limit = UnitDisplayUtils.roundDecimals(limit);
                    PacketUtils.sendToServer(new PacketMSGuiInteract(PacketMSGuiInteract.MSGuiInteraction.INJECTION_RATE, tile, limit));
                    rateLimitField.setText("");
                }
            } catch (NumberFormatException ignored) {

            }
        }
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        renderInventoryText(guiGraphics);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }
}
