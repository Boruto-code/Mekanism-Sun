package com.hamburger0abcde.mekanismsun.common.tiles.transmitter;

import com.hamburger0abcde.mekanismsun.common.content.network.transmitter.AdvanceThermodynamicConductor;
import com.hamburger0abcde.mekanismsun.common.registries.MSBlocks;
import com.hamburger0abcde.mekanismsun.common.tiers.AdvancedTier;
import mekanism.api.heat.IHeatCapacitor;
import mekanism.api.heat.IMekanismHeatHandler;
import mekanism.common.block.states.BlockStateHelper;
import mekanism.common.block.states.TransmitterType;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.resolver.manager.HeatHandlerManager;
import mekanism.common.lib.transmitter.ConnectionType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class TileEntityAdvanceThermodynamicConductor extends TileEntityAdvanceTransmitter {
    private final HeatHandlerManager heatHandlerManager;

    public TileEntityAdvanceThermodynamicConductor(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
        addCapabilityResolver(heatHandlerManager = new HeatHandlerManager(direction -> {
            AdvanceThermodynamicConductor conductor = getTransmitter();
            if (direction != null && (conductor.getConnectionTypeRaw(direction) == ConnectionType.NONE) || conductor.isRedstoneActivated()) {
                //If we actually have a side, and our connection type on that side is none, or we are currently activated by redstone,
                // then return that we have no capacitors
                return Collections.emptyList();
            }
            return conductor.getHeatCapacitors(direction);
        }, new IMekanismHeatHandler() {
            @NotNull
            @Override
            public List<IHeatCapacitor> getHeatCapacitors(@Nullable Direction side) {
                return heatHandlerManager.getContainers(side);
            }

            @Override
            public void onContentsChanged() {
            }
        }));
    }

    @Override
    protected AdvanceThermodynamicConductor createTransmitter(Holder<Block> blockProvider) {
        return new AdvanceThermodynamicConductor(blockProvider, this);
    }

    @Override
    public AdvanceThermodynamicConductor getTransmitter() {
        return (AdvanceThermodynamicConductor) super.getTransmitter();
    }

    @Override
    public TransmitterType getTransmitterType() {
        return TransmitterType.THERMODYNAMIC_CONDUCTOR;
    }

    @NotNull
    @Override
    protected BlockState upgradeResult(@NotNull BlockState current, @NotNull AdvancedTier tier) {
        return BlockStateHelper.copyStateData(current, switch (tier) {
            case SUPERNOVA -> MSBlocks.SUPERNOVA_THERMODYNAMIC_CONDUCTOR;
        });
    }

    @Override
    public void sideChanged(@NotNull Direction side, @NotNull ConnectionType old, @NotNull ConnectionType type) {
        super.sideChanged(side, old, type);
        if (type == ConnectionType.NONE) {
            //We no longer have a capability, invalidate it, which will also notify the level
            invalidateCapability(Capabilities.HEAT, side);
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
            //The transmitter now is powered by redstone and previously was not
            //Note: While at first glance the below invalidation may seem over aggressive, it is not actually that aggressive as
            // if a cap has not been initialized yet on a side then invalidating it will just NO-OP
            invalidateCapabilityAll(Capabilities.HEAT);
        } else {
            //Notify any listeners to our position that we now do have a capability
            //Note: We don't invalidate our impls because we know they are already invalid, so we can short circuit setting them to null from null
            invalidateCapabilities();
        }
    }
}
