package com.hamburger0abcde.mekanismsun.client.gui;

import com.hamburger0abcde.mekanismsun.common.network.to_server.PacketMSTileButtonPress;
import com.hamburger0abcde.mekanismsun.common.tiles.multiblock.matrix.TileEntityAdvanceInductionCasing;
import mekanism.api.text.ILangEntry;
import mekanism.client.SpecialColors;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.tab.GuiTabElementType;
import mekanism.client.gui.element.tab.TabType;
import mekanism.client.render.lib.ColorAtlas;
import mekanism.common.MekanismLang;
import mekanism.common.network.PacketUtils;
import mekanism.common.util.MekanismUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class GuiAdvanceMatrixTab extends GuiTabElementType<TileEntityAdvanceInductionCasing,
        GuiAdvanceMatrixTab.AdvanceMatrixTab> {
    public GuiAdvanceMatrixTab(IGuiWrapper gui, TileEntityAdvanceInductionCasing tile, AdvanceMatrixTab type) {
        super(gui, tile, type);
    }

    public enum AdvanceMatrixTab implements TabType<TileEntityAdvanceInductionCasing> {
        MAIN("energy.png", MekanismLang.MAIN_TAB, PacketMSTileButtonPress.ClickedTileButton.TAB_MAIN,
                SpecialColors.TAB_MULTIBLOCK_MAIN),
        STAT("stats.png", MekanismLang.MATRIX_STATS, PacketMSTileButtonPress.ClickedTileButton.TAB_STATS,
                SpecialColors.TAB_MULTIBLOCK_STATS);

        private final ColorAtlas.ColorRegistryObject colorRO;
        private final PacketMSTileButtonPress.ClickedTileButton button;
        private final ILangEntry description;
        private final String path;

        AdvanceMatrixTab(String path, ILangEntry description, PacketMSTileButtonPress.ClickedTileButton button,
                         ColorAtlas.ColorRegistryObject colorRO) {
            this.path = path;
            this.description = description;
            this.button = button;
            this.colorRO = colorRO;
        }

        @Override
        public ResourceLocation getResource() {
            return MekanismUtils.getResource(MekanismUtils.ResourceType.GUI, path);
        }

        @Override
        public void onClick(TileEntityAdvanceInductionCasing tile) {
            PacketUtils.sendToServer(new PacketMSTileButtonPress(button, tile));
        }

        @Override
        public Component getDescription() {
            return description.translate();
        }

        @Override
        public ColorAtlas.ColorRegistryObject getTabColor() {
            return colorRO;
        }
    }
}
