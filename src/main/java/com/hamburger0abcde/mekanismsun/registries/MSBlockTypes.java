package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSunLang;
import com.hamburger0abcde.mekanismsun.block.attribute.MSAttributeTier;
import com.hamburger0abcde.mekanismsun.tiers.storage.AdvanceChemicalTankTier;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunPort;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityAlloyer;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityElectricNeutronActivator;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityTransmutator;
import com.hamburger0abcde.mekanismsun.tiles.storage.TileEntityAdvanceChemicalTank;
import mekanism.api.Upgrade;
import mekanism.common.MekanismLang;
import mekanism.common.block.attribute.*;
import mekanism.common.config.MekanismConfig;
import mekanism.common.content.blocktype.BlockShapes;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.registries.MekanismSounds;

import java.util.function.Supplier;

public class MSBlockTypes {
    private static <TILE extends TileEntityAdvanceChemicalTank> Machine<TILE> createChemicalTank(
            AdvanceChemicalTankTier tier, Supplier<TileEntityTypeRegistryObject<TILE>> tile,
            Supplier<BlockRegistryObject<?, ?>> upgradeBlock
    ) {
        return Machine.MachineBuilder.createMachine(tile, MekanismLang.DESCRIPTION_CHEMICAL_TANK)
                .withGui(() -> MSContainerTypes.ADVANCE_CHEMICAL_TANK)
                .withCustomShape(BlockShapes.CHEMICAL_TANK)
                .with(new MSAttributeTier<>(tier), new AttributeUpgradeable(upgradeBlock))
                .withSideConfig(TransmissionType.CHEMICAL, TransmissionType.ITEM)
                .without(AttributeParticleFX.class, AttributeStateActive.class, AttributeUpgradeSupport.class)
                .withComputerSupport(tier.getAdvanceTier().getLowerName() + "ChemicalTank")
                .build();
    }

    public static final Machine<TileEntityAlloyer> ALLOYER = Machine.MachineBuilder
            .createMachine(() -> MSTileEntityTypes.ALLOYER, MekanismSunLang.DESCRIPTION_ALLOYER)
            .withGui(() -> MSContainerTypes.ALLOYER)
            .withSound(MekanismSounds.COMBINER)
            .withEnergyConfig(MekanismConfig.usage.combiner, MekanismConfig.storage.combiner)
            .withSupportedUpgrades(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.MUFFLING)
            .withComputerSupport("alloyer")
            .withSideConfig(TransmissionType.ITEM, TransmissionType.CHEMICAL, TransmissionType.ENERGY)
            .build();

    public static final Machine<TileEntityTransmutator> TRANSMUTATOR = Machine.MachineBuilder
            .createMachine(() -> MSTileEntityTypes.TRANSMUTATOR, MekanismSunLang.DESCRIPTION_TRANSMUTATOR)
            .withGui(() -> MSContainerTypes.TRANSMUTATOR)
            .withSound(MekanismSounds.ANTIPROTONIC_NUCLEOSYNTHESIZER)
            .withEnergyConfig(MekanismConfig.usage.combiner, MekanismConfig.storage.combiner)
            .withSupportedUpgrades(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.MUFFLING)
            .withComputerSupport("transmutator")
            .withSideConfig(TransmissionType.ITEM, TransmissionType.ENERGY)
            .build();

    public static final Machine<TileEntityElectricNeutronActivator> ELECTRIC_NEUTRON_ACTIVATOR = Machine.MachineBuilder
            .createMachine(() -> MSTileEntityTypes.ELECTRIC_NEUTRON_ACTIVATOR, MekanismSunLang.DESCRIPTION_ELECTRIC_NEUTRON_ACTIVATOR)
            .withGui(() -> MSContainerTypes.ELECTRIC_NEUTRON_ACTIVATOR)
            .withEnergyConfig(MekanismConfig.usage.combiner, MekanismConfig.storage.combiner)
            .withSupportedUpgrades(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.MUFFLING)
            .withComputerSupport("electric_neutron_activator")
            .withSideConfig(TransmissionType.CHEMICAL, TransmissionType.ENERGY)
            .build();

    public static final BlockTypeTile<TileEntityArtificialSunCasing> ARTIFICIAL_SUN_CASING = BlockTypeTile.BlockTileBuilder
            .createBlock(() -> MSTileEntityTypes.ARTIFICIAL_SUN_CASING, MekanismSunLang.DESCRIPTION_ARTIFICIAL_SUN_CASING)
            .withGui(() -> MSContainerTypes.ARTIFICIAL_SUN, MekanismSunLang.ARTIFICIAL_SUN)
            .withSound(MekanismSounds.SPS)
            .with(Attributes.ACTIVE, Attributes.COMPARATOR)
            .externalMultiblock()
            .build();

    public static final BlockTypeTile<TileEntityArtificialSunPort> ARTIFICIAL_SUN_PORT = BlockTypeTile.BlockTileBuilder
            .createBlock(() -> MSTileEntityTypes.ARTIFICIAL_SUN_PORT, MekanismSunLang.DESCRIPTION_ARTIFICIAL_SUN_PORT)
            .withGui(() -> MSContainerTypes.ARTIFICIAL_SUN, MekanismSunLang.ARTIFICIAL_SUN)
            .withSound(MekanismSounds.SPS)
            .with(Attributes.ACTIVE, Attributes.COMPARATOR)
            .externalMultiblock()
            .build();

    public static final Machine<TileEntityAdvanceChemicalTank> SUPERNOVA_CHEMICAL_TANK =
            createChemicalTank(AdvanceChemicalTankTier.SUPERNOVA, () -> MSTileEntityTypes.SUPERNOVA_CHEMICAL_TANK,
                    () -> MSBlocks.SUPERNOVA_TANK);
}
