package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunPort;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityAlloyer;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityElectricNeutronActivator;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityTransmutator;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;

public class MSTileEntityTypes {
    public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(MekanismSun.MODID);

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
}
