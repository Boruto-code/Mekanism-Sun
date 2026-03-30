package com.hamburger0abcde.mekanismsun.tiles.transmitter;

import mekanism.common.capabilities.energy.DynamicStrictEnergyHandler;
import mekanism.common.capabilities.resolver.manager.EnergyHandlerManager;
import mekanism.common.integration.computer.IComputerTile;
import mekanism.common.lib.transmitter.ConnectionType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collections;

public class TileEntityAdvanceUniversalCable extends TileEntityAdvanceTransmitter implements IComputerTile {
    private final EnergyHandlerManager energyHandlerManager;
    
    public TileEntityAdvanceUniversalCable(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
        addCapabilityResolver(energyHandlerManager = new EnergyHandlerManager(direction -> {
            AdvanceUniversalCable cable = getTransmitter();
            if (direction != null && (cable.getConnectionTypeRaw(direction) == ConnectionType.NONE) || cable.isRedstoneActivated()) {
                return Collections.emptyList();
            }
            return cable.getEnergyContainers(direction);
        }, new DynamicStrictEnergyHandler(this::getEnergyContainers, getAdvancectPredicate(), getInsertPredicate(), null)));
    }

    @Override
    protected AdvanceUniversalCable createTransmitter(Holder<Block> blockProvider) {
        return new AdvanceUniversalCable(blockProvider, this);
    }

    @Override
    public AdvanceUniversalCable getTransmitter() {
        return (AdvanceUniversalCable) super.getTransmitter();
    }

    @Override
    protected void onUpdateServer() {
        getTransmitter().pullFromAcceptors();
        super.onUpdateServer();
    }

    @Override
    public TransmitterType getTransmitterType() {
        return TransmitterType.UNIVERSAL_CABLE;
    }

    @NotNull
    @Override
    protected BlockState upgradeResult(@NotNull BlockState current, @NotNull AdvancedTier tier) {
        return BlockStateHelper.copyStateData(current, switch (tier) {
            case ABSOLUTE -> AdvanceBlocks.ABSOLUTE_UNIVERSAL_CABLE;
            case SUPREME -> AdvanceBlocks.SUPREME_UNIVERSAL_CABLE;
            case COSMIC -> AdvanceBlocks.COSMIC_UNIVERSAL_CABLE;
            case INFINITE -> AdvanceBlocks.INFINITE_UNIVERSAL_CABLE;
        });
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag(HolderLookup.@NotNull Provider provider) {
        //Note: We add the stored information to the initial update tag and not to the one we sync on side changes which uses getReducedUpdateTag
        CompoundTag updateTag = super.getUpdateTag(provider);
        if (getTransmitter().hasTransmitterNetwork()) {
            EnergyNetwork network = getTransmitter().getTransmitterNetwork();
            updateTag.putLong(SerializationConstants.ENERGY, network.energyContainer.getEnergy());
            updateTag.putFloat(SerializationConstants.SCALE, network.currentScale);
        }
        return updateTag;
    }

    private List<IEnergyContainer> getEnergyContainers(@Nullable Direction side) {
        return energyHandlerManager.getContainers(side);
    }

    @Override
    public void sideChanged(@NotNull Direction side, @NotNull ConnectionType old, @NotNull ConnectionType type) {
        super.sideChanged(side, old, type);
        if (type == ConnectionType.NONE) {
            //We no longer have a capability, invalidate it, which will also notify the level
            invalidateCapabilities(EnergyCompatUtils.getLoadedEnergyCapabilities(), side);
        } else if (old == ConnectionType.NONE) {
            //Notify any listeners to our position that we now do have a capability
            //Note: We don't invalidate our impls because we know they are already invalid, so we can short circuit setting them to null from null
            invalidateCapabilities();
        }
    }

    @Override
    public void redstoneChanged(boolean powered) {
        super.redstoneChanged(powered);
        if (powered) {
            invalidateCapabilitiesAll(EnergyCompatUtils.getLoadedEnergyCapabilities());
        } else {
            invalidateCapabilities();
        }
    }

    //Methods relating to IComputerTile
    @Override
    public String getComputerName() {
        return getTransmitter().getTier().getBaseTier().getLowerName() + "UniversalCable";
    }

    @ComputerMethod
    long getBuffer() {
        return getTransmitter().getBufferWithFallback();
    }

    @ComputerMethod
    long getCapacity() {
        AdvanceUniversalCable cable = getTransmitter();
        return cable.hasTransmitterNetwork() ? cable.getTransmitterNetwork().getCapacity() : cable.getCapacity();
    }

    @ComputerMethod
    long getNeeded() {
        return getCapacity() - getBuffer();
    }

    @ComputerMethod
    double getFilledPercentage() {
        return MathUtils.divideToLevel(getBuffer(), getCapacity());
    }
}
