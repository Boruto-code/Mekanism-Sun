package com.hamburger0abcde.mekanismsun.common.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.common.tiles.machine.TileEntityAssembler;
import com.hamburger0abcde.mekanismsun.common.tiles.multiblock.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.common.tiles.machine.TileEntityAlloyer;
import com.hamburger0abcde.mekanismsun.common.tiles.machine.TileEntityElectricNeutronActivator;
import com.hamburger0abcde.mekanismsun.common.tiles.machine.TileEntityTransmutator;
import com.hamburger0abcde.mekanismsun.common.tiles.multiblock.matrix.TileEntityAdvanceInductionCasing;
import com.hamburger0abcde.mekanismsun.common.tiles.storage.TileEntityAdvanceChemicalTank;
import com.hamburger0abcde.mekanismsun.common.tiles.storage.TileEntityAdvanceEnergyCube;
import com.hamburger0abcde.mekanismsun.common.tiles.storage.TileEntityAdvanceFluidTank;
import mekanism.common.inventory.container.tile.EmptyTileContainer;
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
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAssembler>> ASSEMBLER =
            CONTAINER_TYPES.register(MSBlocks.ASSEMBLER, TileEntityAssembler.class);

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityArtificialSunCasing>> ARTIFICIAL_SUN =
            CONTAINER_TYPES.custom(MSBlocks.ARTIFICIAL_SUN_CASING, TileEntityArtificialSunCasing.class)
                    .offset(0, 16).build();
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAdvanceInductionCasing>> ADVANCE_INDUCTION_MATRIX =
            CONTAINER_TYPES.custom("advance_induction_matrix", TileEntityAdvanceInductionCasing.class)
                    .armorSideBar(-20, 41, 0).build();
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityAdvanceInductionCasing>> ADVANCE_MATRIX_STATS =
            CONTAINER_TYPES.registerEmpty("advance_matrix_stats", TileEntityAdvanceInductionCasing.class);

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAdvanceChemicalTank>> ADVANCE_CHEMICAL_TANK =
            CONTAINER_TYPES.custom("advance_chemical_tank", TileEntityAdvanceChemicalTank.class).armorSideBar().build();
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAdvanceFluidTank>> ADVANCE_FLUID_TANK =
            CONTAINER_TYPES.custom("advance_fluid_tank", TileEntityAdvanceFluidTank.class).armorSideBar().build();
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAdvanceEnergyCube>> ADVANCE_ENERGY_CUBE =
            CONTAINER_TYPES.custom("advance_energy_cube", TileEntityAdvanceEnergyCube.class)
                    .armorSideBar(180, 41, 0).build();
}
