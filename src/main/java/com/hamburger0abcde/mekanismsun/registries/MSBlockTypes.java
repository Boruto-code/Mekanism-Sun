package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSunLang;
import com.hamburger0abcde.mekanismsun.block.attribute.MSAttributeTier;
import com.hamburger0abcde.mekanismsun.block.attribute.MSAttributeUpgradeable;
import com.hamburger0abcde.mekanismsun.tiers.storage.AdvanceBinTier;
import com.hamburger0abcde.mekanismsun.tiers.storage.AdvanceChemicalTankTier;
import com.hamburger0abcde.mekanismsun.tiers.storage.AdvanceEnergyCubeTier;
import com.hamburger0abcde.mekanismsun.tiers.storage.AdvanceFluidTankTier;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunPort;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityAlloyer;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityElectricNeutronActivator;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityTransmutator;
import com.hamburger0abcde.mekanismsun.tiles.storage.TileEntityAdvanceBin;
import com.hamburger0abcde.mekanismsun.tiles.storage.TileEntityAdvanceChemicalTank;
import com.hamburger0abcde.mekanismsun.tiles.storage.TileEntityAdvanceEnergyCube;
import com.hamburger0abcde.mekanismsun.tiles.storage.TileEntityAdvanceFluidTank;
import com.hamburger0abcde.mekanismsun.tiles.transmitter.TileEntityAdvanceTransmitter;
import com.hamburger0abcde.mekanismsun.tiles.transmitter.TileEntityAdvanceUniversalCable;
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
import mekanism.common.tier.CableTier;
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
}
