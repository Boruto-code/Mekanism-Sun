package com.hamburger0abcde.mekanismsun.common.registries;

import com.hamburger0abcde.mekanismsun.common.MekanismSunLang;
import com.hamburger0abcde.mekanismsun.common.block.attribute.MSAttributeTier;
import com.hamburger0abcde.mekanismsun.common.block.attribute.MSAttributeUpgradeable;
import com.hamburger0abcde.mekanismsun.common.tiers.storage.*;
import com.hamburger0abcde.mekanismsun.common.tiles.storage.*;
import com.hamburger0abcde.mekanismsun.common.tiles.multiblock.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.common.tiles.multiblock.artificial_sun.TileEntityArtificialSunPort;
import com.hamburger0abcde.mekanismsun.common.tiles.machine.TileEntityAlloyer;
import com.hamburger0abcde.mekanismsun.common.tiles.machine.TileEntityElectricNeutronActivator;
import com.hamburger0abcde.mekanismsun.common.tiles.machine.TileEntityTransmutator;
import com.hamburger0abcde.mekanismsun.common.tiles.transmitter.*;
import mekanism.api.Upgrade;
import mekanism.api.text.ILangEntry;
import mekanism.api.tier.ITier;
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
import mekanism.common.tier.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Supplier;

public class MSBlockTypes {
    private static <TILE extends TileEntityAdvanceChemicalTank> Machine<TILE> createChemicalTank(
            AdvanceChemicalTankTier tier, Supplier<TileEntityTypeRegistryObject<TILE>> tile,
            Supplier<BlockRegistryObject<?, ?>> upgradeBlock
    ) {
        return Machine.MachineBuilder.createMachine(tile, MekanismLang.DESCRIPTION_CHEMICAL_TANK)
                .withGui(() -> MSContainerTypes.ADVANCE_CHEMICAL_TANK)
                .withCustomShape(BlockShapes.CHEMICAL_TANK)
                .with(new MSAttributeTier<>(tier), new MSAttributeUpgradeable(upgradeBlock))
                .withSideConfig(TransmissionType.CHEMICAL, TransmissionType.ITEM)
                .without(AttributeParticleFX.class, AttributeStateActive.class, AttributeUpgradeSupport.class)
                .withComputerSupport(tier.getAdvanceTier().getLowerName() + "ChemicalTank")
                .build();
    }

    private static <TILE extends TileEntityAdvanceFluidTank> Machine<TILE> createFluidTank(
            AdvanceFluidTankTier tier, Supplier<TileEntityTypeRegistryObject<TILE>> tile,
            Supplier<BlockRegistryObject<?, ?>> upgradeBlock
    ) {
        return Machine.MachineBuilder.createMachine(tile, MekanismLang.DESCRIPTION_FLUID_TANK)
                .withGui(() -> MSContainerTypes.ADVANCE_FLUID_TANK)
                .withCustomShape(BlockShapes.FLUID_TANK)
                .with(new MSAttributeTier<>(tier), new MSAttributeUpgradeable(upgradeBlock))
                .without(AttributeParticleFX.class, AttributeStateFacing.class,
                        Attributes.AttributeRedstone.class, AttributeUpgradeSupport.class)
                .withComputerSupport(tier.getAdvanceTier().getLowerName() + "FluidTank")
                .build();
    }

    private static <TILE extends TileEntityAdvanceEnergyCube> Machine<TILE> createEnergyCube(
            AdvanceEnergyCubeTier tier, Supplier<TileEntityTypeRegistryObject<TILE>> tile, Supplier<BlockRegistryObject<?, ?>> upgradeBlock
    ) {
        return Machine.MachineBuilder.createMachine(tile, MekanismLang.DESCRIPTION_ENERGY_CUBE)
                .withGui(() -> MSContainerTypes.ADVANCE_ENERGY_CUBE)
                .withEnergyConfig(tier::getMaxEnergy)
                .with(new MSAttributeTier<>(tier), new MSAttributeUpgradeable(upgradeBlock),
                        new AttributeStateFacing(BlockStateProperties.FACING))
                .withSideConfig(TransmissionType.ENERGY, TransmissionType.ITEM)
                .without(AttributeParticleFX.class, AttributeStateActive.class, AttributeUpgradeSupport.class)
                .withComputerSupport(tier.getAdvanceTier().getLowerName() + "EnergyCube")
                .build();
    }

    private static <TILE extends TileEntityAdvanceBin> Machine<TILE> createBin(
            AdvanceBinTier tier, Supplier<TileEntityTypeRegistryObject<TILE>> tile, Supplier<BlockRegistryObject<?, ?>> upgradeBlock
    ) {
        return Machine.MachineBuilder.createMachine(tile, MekanismLang.DESCRIPTION_BIN)
                .with(new MSAttributeTier<>(tier), new MSAttributeUpgradeable(upgradeBlock))
                .without(AttributeParticleFX.class, Attributes.AttributeSecurity.class, AttributeUpgradeSupport.class,
                        Attributes.AttributeRedstone.class)
                .withComputerSupport(tier.getAdvanceTier().getLowerName() + "Bin")
                .build();
    }

    private static <TILE extends TileEntityAdvanceTransmitter> BlockTypeTile<TILE> createTransmitter(
            ITier tier, Supplier<TileEntityTypeRegistryObject<TILE>> tile, ILangEntry description
    ) {
        return BlockTypeTile.BlockTileBuilder.createBlock(tile, description)
                .with(new AttributeTier<>(tier))
                .build();
    }

    private static BlockTypeTile<TileEntityAdvanceUniversalCable> createCable(
            CableTier tier, Supplier<TileEntityTypeRegistryObject<TileEntityAdvanceUniversalCable>> tile
    ) {
        return createTransmitter(tier, tile, MekanismLang.DESCRIPTION_CABLE);
    }

    private static BlockTypeTile<TileEntityAdvanceMechanicalPipe> createPipe(
            PipeTier tier, Supplier<TileEntityTypeRegistryObject<TileEntityAdvanceMechanicalPipe>> tile
    ) {
        return createTransmitter(tier, tile, MekanismLang.DESCRIPTION_PIPE);
    }

    private static BlockTypeTile<TileEntityAdvancePressurizedTube> createTube(
            TubeTier tier, Supplier<TileEntityTypeRegistryObject<TileEntityAdvancePressurizedTube>> tile
    ) {
        return createTransmitter(tier, tile, MekanismLang.DESCRIPTION_TUBE);
    }

    private static BlockTypeTile<TileEntityAdvanceLogisticalTransporter> createTransporter(
            TransporterTier tier, Supplier<TileEntityTypeRegistryObject<TileEntityAdvanceLogisticalTransporter>> tile
    ) {
        return createTransmitter(tier, tile, MekanismLang.DESCRIPTION_TELEPORTER);
    }

    private static BlockTypeTile<TileEntityAdvanceThermodynamicConductor> createConductor(
            ConductorTier tier, Supplier<TileEntityTypeRegistryObject<TileEntityAdvanceThermodynamicConductor>> tile
    ) {
        return createTransmitter(tier, tile, MekanismLang.DESCRIPTION_CONDUCTOR);
    }

    private static <TILE extends TileEntityAdvanceInductionCell> BlockTypeTile<TILE> createInductionCell(
            AdvanceInductionCellTier tier, Supplier<TileEntityTypeRegistryObject<TILE>> tile
    ) {
        return BlockTypeTile.BlockTileBuilder.createBlock(tile, MekanismLang.DESCRIPTION_INDUCTION_CELL)
                .withEnergyConfig(tier::getMaxEnergy)
                .with(new MSAttributeTier<>(tier))
                .internalMultiblock()
                .build();
    }

    private static <TILE extends TileEntityAdvanceInductionProvider> BlockTypeTile<TILE> createInductionProvider(
            AdvanceInductionProviderTier tier, Supplier<TileEntityTypeRegistryObject<TILE>> tile
    ) {
        return BlockTypeTile.BlockTileBuilder.createBlock(tile, MekanismLang.DESCRIPTION_INDUCTION_PROVIDER)
                .with(new MSAttributeTier<>(tier))
                .internalMultiblock()
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
                    () -> MSBlocks.SUPERNOVA_CHEMICAL_TANK);

    public static final Machine<TileEntityAdvanceFluidTank> SUPERNOVA_FLUID_TANK =
            createFluidTank(AdvanceFluidTankTier.SUPERNOVA, () -> MSTileEntityTypes.SUPERNOVA_FLUID_TANK,
                    () -> MSBlocks.SUPERNOVA_FLUID_TANK);

    public static final Machine<TileEntityAdvanceEnergyCube> SUPERNOVA_ENERGY_CUBE =
            createEnergyCube(AdvanceEnergyCubeTier.SUPERNOVA, () -> MSTileEntityTypes.SUPERNOVA_ENERGY_CUBE,
                    () -> MSBlocks.SUPERNOVA_ENERGY_CUBE);

    public static final Machine<TileEntityAdvanceBin> SUPERNOVA_BIN =
            createBin(AdvanceBinTier.SUPERNOVA, () -> MSTileEntityTypes.SUPERNOVA_BIN, () -> MSBlocks.SUPERNOVA_BIN);

    public static final BlockTypeTile<TileEntityAdvanceUniversalCable> SUPERNOVA_UNIVERSAL_CABLE =
            createCable(CableTier.BASIC, () -> MSTileEntityTypes.SUPERNOVA_UNIVERSAL_CABLE);

    public static final BlockTypeTile<TileEntityAdvanceMechanicalPipe> SUPERNOVA_MECHANICAL_PIPE =
            createPipe(PipeTier.BASIC, () -> MSTileEntityTypes.SUPERNOVA_MECHANICAL_PIPE);

    public static final BlockTypeTile<TileEntityAdvancePressurizedTube> SUPERNOVA_PRESSURIZED_TUBE =
            createTube(TubeTier.BASIC, () -> MSTileEntityTypes.SUPERNOVA_PRESSURIZED_TUBE);

    public static final BlockTypeTile<TileEntityAdvanceLogisticalTransporter> SUPERNOVA_LOGISTICAL_TRANSPORTER =
            createTransporter(TransporterTier.BASIC, () -> MSTileEntityTypes.SUPERNOVA_LOGISTICAL_TRANSPORTER);

    public static final BlockTypeTile<TileEntityAdvanceThermodynamicConductor> SUPERNOVA_THERMODYNAMIC_CONDUCTOR =
            createConductor(ConductorTier.BASIC, () -> MSTileEntityTypes.SUPERNOVA_THERMODYNAMIC_CONDUCTOR);

    public static final BlockTypeTile<TileEntityAdvanceInductionCell> SUPERNOVA_INDUCTION_CELL =
            createInductionCell(AdvanceInductionCellTier.SUPERNOVA, () -> MSTileEntityTypes.SUPERNOVA_INDUCTION_CELL);

    public static final BlockTypeTile<TileEntityAdvanceInductionProvider> SUPERNOVA_INDUCTION_PROVIDER =
            createInductionProvider(AdvanceInductionProviderTier.SUPERNOVA, () -> MSTileEntityTypes.SUPERNOVA_INDUCTION_PROVIDER);
}
