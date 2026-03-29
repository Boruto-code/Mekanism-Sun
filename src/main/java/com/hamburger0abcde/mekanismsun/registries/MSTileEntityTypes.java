package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.item.block.MSItemBlockBin;
import com.hamburger0abcde.mekanismsun.item.block.MSItemBlockChemicalTank;
import com.hamburger0abcde.mekanismsun.item.block.MSItemBlockEnergyCube;
import com.hamburger0abcde.mekanismsun.item.block.MSItemBlockFluidTank;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunPort;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityAlloyer;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityElectricNeutronActivator;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityTransmutator;
import com.hamburger0abcde.mekanismsun.tiles.storage.TileEntityAdvanceBin;
import com.hamburger0abcde.mekanismsun.tiles.storage.TileEntityAdvanceChemicalTank;
import com.hamburger0abcde.mekanismsun.tiles.storage.TileEntityAdvanceEnergyCube;
import com.hamburger0abcde.mekanismsun.tiles.storage.TileEntityAdvanceFluidTank;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;

public class MSTileEntityTypes {
    public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(MekanismSun.MODID);

    private static TileEntityTypeRegistryObject<TileEntityAdvanceChemicalTank> registerChemicalTank(
            BlockRegistryObject<?, MSItemBlockChemicalTank> block) {
        return TILE_ENTITY_TYPES.mekBuilder(block, (pos, state) -> new TileEntityAdvanceChemicalTank(block, pos, state))
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIG_CARD)
                .build();
    }

    private static TileEntityTypeRegistryObject<TileEntityAdvanceFluidTank> registerFluidTank(
            BlockRegistryObject<?, MSItemBlockFluidTank> block) {
        return TILE_ENTITY_TYPES.mekBuilder(block, (pos, state) -> new TileEntityAdvanceFluidTank(block, pos, state))
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIG_CARD)
                .build();
    }

    private static TileEntityTypeRegistryObject<TileEntityAdvanceEnergyCube> registerEnergyCube(
            BlockRegistryObject<?, MSItemBlockEnergyCube> block) {
        return TILE_ENTITY_TYPES.mekBuilder(block, (pos, state) -> new TileEntityAdvanceEnergyCube(block, pos, state))
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIG_CARD)
                .build();
    }

    private static TileEntityTypeRegistryObject<TileEntityAdvanceBin> registerBin(BlockRegistryObject<?, MSItemBlockBin> block) {
        return TILE_ENTITY_TYPES.mekBuilder(block, (pos, state) -> new TileEntityAdvanceBin(block, pos, state))
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIGURABLE)
                .build();
    }

    public static final TileEntityTypeRegistryObject<TileEntityAlloyer> ALLOYER =
            TILE_ENTITY_TYPES.mekBuilder(MSBlocks.ALLOYER, TileEntityAlloyer::new)
                    .clientTicker(TileEntityMekanism::tickClient)
                    .serverTicker(TileEntityMekanism::tickServer)
                    .withSimple(Capabilities.CONFIG_CARD).build();

    public static final TileEntityTypeRegistryObject<TileEntityTransmutator> TRANSMUTATOR =
            TILE_ENTITY_TYPES.mekBuilder(MSBlocks.TRANSMUTATOR, TileEntityTransmutator::new)
                    .clientTicker(TileEntityMekanism::tickClient)
                    .serverTicker(TileEntityMekanism::tickServer)
                    .withSimple(Capabilities.CONFIG_CARD).build();

    public static final TileEntityTypeRegistryObject<TileEntityElectricNeutronActivator> ELECTRIC_NEUTRON_ACTIVATOR =
            TILE_ENTITY_TYPES.mekBuilder(MSBlocks.ELECTRIC_NEUTRON_ACTIVATOR, TileEntityElectricNeutronActivator::new)
                    .clientTicker(TileEntityMekanism::tickClient)
                    .serverTicker(TileEntityMekanism::tickServer)
                    .withSimple(Capabilities.CONFIG_CARD).build();

    public static final TileEntityTypeRegistryObject<TileEntityArtificialSunCasing> ARTIFICIAL_SUN_CASING =
            TILE_ENTITY_TYPES.mekBuilder(MSBlocks.ARTIFICIAL_SUN_CASING, TileEntityArtificialSunCasing::new)
                    .clientTicker(TileEntityMekanism::tickClient)
                    .serverTicker(TileEntityMekanism::tickServer)
                    .withSimple(Capabilities.CONFIGURABLE).build();

    public static final TileEntityTypeRegistryObject<TileEntityArtificialSunPort> ARTIFICIAL_SUN_PORT =
            TILE_ENTITY_TYPES.mekBuilder(MSBlocks.ARTIFICIAL_SUN_PORT, TileEntityArtificialSunPort::new)
                    .clientTicker(TileEntityMekanism::tickClient)
                    .serverTicker(TileEntityMekanism::tickServer)
                    .withSimple(Capabilities.CONFIGURABLE).build();

    public static final TileEntityTypeRegistryObject<TileEntityAdvanceChemicalTank> SUPERNOVA_CHEMICAL_TANK =
            registerChemicalTank(MSBlocks.SUPERNOVA_CHEMICAL_TANK);
    public static final TileEntityTypeRegistryObject<TileEntityAdvanceFluidTank> SUPERNOVA_FLUID_TANK =
            registerFluidTank(MSBlocks.SUPERNOVA_FLUID_TANK);
    public static final TileEntityTypeRegistryObject<TileEntityAdvanceEnergyCube> SUPERNOVA_ENERGY_CUBE =
            registerEnergyCube(MSBlocks.SUPERNOVA_ENERGY_CUBE);
    public static final TileEntityTypeRegistryObject<TileEntityAdvanceBin> SUPERNOVA_BIN =
            registerBin(MSBlocks.SUPERNOVA_BIN);
}
