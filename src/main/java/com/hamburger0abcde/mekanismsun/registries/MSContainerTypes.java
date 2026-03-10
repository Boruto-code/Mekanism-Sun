package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityFreezer;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;

public class MSContainerTypes {
    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(MekanismSun.MODID);

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityArtificialSunCasing>> ARTIFICIAL_SUN =
            CONTAINER_TYPES.custom(MSBlocks.ARTIFICIAL_SUN_CASING, TileEntityArtificialSunCasing.class)
                    .offset(0, 16).build();
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityFreezer>> FREEZER =
            CONTAINER_TYPES.register(MSBlocks.FREEZER, TileEntityFreezer.class);
}
