package com.hamburger0abcde.mekanismsun.common.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.common.capabilities.AdvanceCapabilities;
import com.hamburger0abcde.mekanismsun.common.item.block.MSItemBlockBin;
import com.hamburger0abcde.mekanismsun.common.item.block.MSItemBlockChemicalTank;
import com.hamburger0abcde.mekanismsun.common.item.block.MSItemBlockEnergyCube;
import com.hamburger0abcde.mekanismsun.common.item.block.MSItemBlockFluidTank;
import com.hamburger0abcde.mekanismsun.common.tiles.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.common.tiles.artificial_sun.TileEntityArtificialSunPort;
import com.hamburger0abcde.mekanismsun.common.tiles.machine.TileEntityAlloyer;
import com.hamburger0abcde.mekanismsun.common.tiles.machine.TileEntityElectricNeutronActivator;
import com.hamburger0abcde.mekanismsun.common.tiles.machine.TileEntityTransmutator;
import com.hamburger0abcde.mekanismsun.common.tiles.storage.TileEntityAdvanceBin;
import com.hamburger0abcde.mekanismsun.common.tiles.storage.TileEntityAdvanceChemicalTank;
import com.hamburger0abcde.mekanismsun.common.tiles.storage.TileEntityAdvanceEnergyCube;
import com.hamburger0abcde.mekanismsun.common.tiles.storage.TileEntityAdvanceFluidTank;
import com.hamburger0abcde.mekanismsun.common.tiles.transmitter.*;
import mekanism.api.functions.ConstantPredicates;
import mekanism.common.Mekanism;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.integration.computer.ComputerCapabilityHelper;
import mekanism.common.integration.energy.EnergyCompatUtils;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister.BlockEntityTypeBuilder;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.CapabilityTileEntity;
import mekanism.common.tile.base.TileEntityMekanism;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

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

    private static <BE extends TileEntityAdvanceTransmitter> TileEntityTypeDeferredRegister.BlockEntityTypeBuilder<BE> transmitterBuilder(
            BlockRegistryObject<?, ?> block, BlockEntityFactory<BE> factory
    ) {
        return TILE_ENTITY_TYPES.builder(block, (pos, state) -> factory.create(block, pos, state))
                .serverTicker(TileEntityAdvanceTransmitter::tickServer)
                .withSimple(AdvanceCapabilities.ADVANCE_ALLOY_INTERACTION)
                .with(Capabilities.CONFIGURABLE, TileEntityAdvanceTransmitter.CONFIGURABLE_PROVIDER);
    }

    private static TileEntityTypeRegistryObject<TileEntityAdvanceUniversalCable> registerCable(BlockRegistryObject<?, ?> block) {
        TileEntityTypeDeferredRegister.BlockEntityTypeBuilder<TileEntityAdvanceUniversalCable> builder =
                transmitterBuilder(block, TileEntityAdvanceUniversalCable::new);
        EnergyCompatUtils.addBlockCapabilities(builder);
        if (Mekanism.hooks.computerCompatEnabled()) {
            ComputerCapabilityHelper.addComputerCapabilities(builder, ConstantPredicates.ALWAYS_TRUE);
        }
        return builder.build();
    }

    private static TileEntityTypeRegistryObject<TileEntityAdvanceMechanicalPipe> registerPipe(BlockRegistryObject<?, ?> block) {
        TileEntityTypeDeferredRegister.BlockEntityTypeBuilder<TileEntityAdvanceMechanicalPipe> builder =
                transmitterBuilder(block, TileEntityAdvanceMechanicalPipe::new)
                        .with(Capabilities.FLUID.block(), CapabilityTileEntity.FLUID_HANDLER_PROVIDER);
        if (Mekanism.hooks.computerCompatEnabled()) {
            ComputerCapabilityHelper.addComputerCapabilities(builder, ConstantPredicates.ALWAYS_TRUE);
        }
        return builder.build();
    }

    private static TileEntityTypeRegistryObject<TileEntityAdvancePressurizedTube> registerTube(BlockRegistryObject<?, ?> block) {
        TileEntityTypeDeferredRegister.BlockEntityTypeBuilder<TileEntityAdvancePressurizedTube> builder =
                transmitterBuilder(block, TileEntityAdvancePressurizedTube::new)
                        .with(Capabilities.CHEMICAL.block(), CapabilityTileEntity.CHEMICAL_HANDLER_PROVIDER);
        if (Mekanism.hooks.computerCompatEnabled()) {
            ComputerCapabilityHelper.addComputerCapabilities(builder, ConstantPredicates.ALWAYS_TRUE);
        }
        return builder.build();
    }

    private static <BE extends TileEntityAdvanceLogisticalTransporterBase> TileEntityTypeRegistryObject<BE> registerTransporter(
            BlockRegistryObject<?, ?> block, BlockEntityFactory<BE> factory
    ) {
        return transporterBuilder(block, factory).build();
    }

    private static <BE extends TileEntityAdvanceLogisticalTransporterBase> BlockEntityTypeBuilder<BE> transporterBuilder(
            BlockRegistryObject<?, ?> block, BlockEntityFactory<BE> factory
    ) {
        return transmitterBuilder(block, factory)
                .clientTicker(TileEntityAdvanceLogisticalTransporterBase::tickClient)
                .with(Capabilities.ITEM.block(), CapabilityTileEntity.ITEM_HANDLER_PROVIDER);
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

    public static final TileEntityTypeRegistryObject<TileEntityAdvanceUniversalCable> SUPERNOVA_UNIVERSAL_CABLE =
            registerCable(MSBlocks.SUPERNOVA_UNIVERSAL_CABLE);

    public static final TileEntityTypeRegistryObject<TileEntityAdvanceMechanicalPipe> SUPERNOVA_MECHANICAL_PIPE =
            registerPipe(MSBlocks.SUPERNOVA_MECHANICAL_PIPE);

    public static final TileEntityTypeRegistryObject<TileEntityAdvancePressurizedTube> SUPERNOVA_PRESSURIZED_TUBE =
            registerTube(MSBlocks.SUPERNOVA_PRESSURIZED_TUBE);

    public static final TileEntityTypeRegistryObject<TileEntityAdvanceLogisticalTransporter> SUPERNOVA_LOGISTICAL_TRANSPORTER =
            registerTransporter(MSBlocks.SUPERNOVA_LOGISTICAL_TRANSPORTER, TileEntityAdvanceLogisticalTransporter::new);

    @FunctionalInterface
    private interface BlockEntityFactory<BE extends BlockEntity> {

        BE create(Holder<Block> block, BlockPos pos, BlockState state);
    }
}
