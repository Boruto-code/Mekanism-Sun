package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityAlloyer;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityElectricNeutronActivator;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityTransmutator;
import com.hamburger0abcde.mekanismsun.tiles.storage.TileEntityAdvanceChemicalTank;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;

public class MSContainerTypes {
    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(MekanismSun.MODID);

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAlloyer>> ALLOYER =
            CONTAINER_TYPES.register(MSBlocks.ALLOYER, TileEntityAlloyer.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityTransmutator>> TRANSMUTATOR =
            CONTAINER_TYPES.register(MSBlocks.TRANSMUTATOR, TileEntityTransmutator.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityElectricNeutronActivator>> ELECTRIC_NEUTRON_ACTIVATOR =
            CONTAINER_TYPES.register(MSBlocks.ELECTRIC_NEUTRON_ACTIVATOR, TileEntityElectricNeutronActivator.class);

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityArtificialSunCasing>> ARTIFICIAL_SUN =
            CONTAINER_TYPES.custom(MSBlocks.ARTIFICIAL_SUN_CASING, TileEntityArtificialSunCasing.class)
                    .offset(0, 16).build();

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAdvanceChemicalTank>> ADVANCE_CHEMICAL_TANK =
            CONTAINER_TYPES.custom("advance_chemical_tank", TileEntityAdvanceChemicalTank.class).armorSideBar().build();
}
