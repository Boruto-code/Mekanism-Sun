package com.hamburger0abcde.mekanismsun.client.gui;

import com.hamburger0abcde.mekanismsun.client.gui.bars.GuiCustomDynamicHorizontalRateBar;
import com.hamburger0abcde.mekanismsun.client.recipe_viewer.type.MSRecipeViewerRecipeTypes;
import com.hamburger0abcde.mekanismsun.common.config.MSConfig;
import com.hamburger0abcde.mekanismsun.common.multiblock.vacuum_freezer.VacuumFreezerMultiblockData;
import com.hamburger0abcde.mekanismsun.common.tiles.multiblock.vacuum_freezer.TileEntityVacuumFreezerCasing;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.bar.GuiBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.util.text.EnergyDisplay;
import mekanism.common.util.text.TextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GuiVacuumFreezer extends GuiMekanismTile<TileEntityVacuumFreezerCasing,
        MekanismTileContainer<TileEntityVacuumFreezerCasing>> {

    public GuiVacuumFreezer(MekanismTileContainer<TileEntityVacuumFreezerCasing> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        imageHeight += 16;
        inventoryLabelY = imageHeight - 92;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiEnergyTab(this, () -> {
            long using = tile.getMultiblock().progress > 0 ? tile.getMultiblock().lastReceivedEnergy : 0;
            return List.of(MekanismLang.USING.translate(EnergyDisplay.of(using)));
        }));
        addRenderableWidget(new GuiFluidGauge(() -> tile.getMultiblock().inputTank, () -> tile.getMultiblock().getFluidTanks(null),
                GaugeType.STANDARD, this, 7, 17));
        addRenderableWidget(new GuiFluidGauge(() -> tile.getMultiblock().outputTank, () -> tile.getMultiblock().getFluidTanks(null),
                GaugeType.STANDARD, this, 151, 17));
        addRenderableWidget(new GuiCustomDynamicHorizontalRateBar(this, new GuiBar.IBarInfoHandler() {
            @Override
            public Component getTooltip() {
                return MekanismLang.PROGRESS.translate(TextUtils.getPercent(Math.max(0, tile.getMultiblock().getScaledProgress())));
            }

            @Override
            public double getLevel() {
                return Math.max(0.0D, tile.getMultiblock().getScaledProgress());
            }
        }, 7, 79, 160, progress -> tile.getMultiblock().getColor()));

        addRenderableWidget(new GuiInnerScreen(this, 47, 17, 82, 60, () -> {
            List<Component> list = new ArrayList<>();
            VacuumFreezerMultiblockData multiblock = tile.getMultiblock();
            boolean active = multiblock.progress > 0;
            list.add(MekanismLang.STATUS.translate(active ? MekanismLang.ACTIVE : MekanismLang.IDLE));
            if (active) {
                list.add(MekanismLang.USING.translate(EnergyDisplay.of(tile.getMultiblock().lastReceivedEnergy)));
            }
            return list;
        }).recipeViewerCategories(MSRecipeViewerRecipeTypes.FREEZE));
    }
}
