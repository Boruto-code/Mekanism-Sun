package com.hamburger0abcde.mekanismsun.client.gui;

import com.hamburger0abcde.mekanismsun.tile.artificial_sun.TileEntityArtificialSunCasing;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiArtificialSun extends GuiMekanismTile<TileEntityArtificialSunCasing,
        MekanismTileContainer<TileEntityArtificialSunCasing>> {

    public GuiArtificialSun(MekanismTileContainer<TileEntityArtificialSunCasing> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        imageHeight += 16;
        inventoryLabelY = imageHeight - 92;
    }
}
