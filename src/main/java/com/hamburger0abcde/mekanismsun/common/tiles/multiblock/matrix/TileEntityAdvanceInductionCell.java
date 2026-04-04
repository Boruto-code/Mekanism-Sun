package com.hamburger0abcde.mekanismsun.common.tiles.multiblock.matrix;

import com.hamburger0abcde.mekanismsun.common.block.attribute.MSAttribute;
import com.hamburger0abcde.mekanismsun.common.tiers.storage.AdvanceInductionCellTier;
import lombok.Getter;
import mekanism.api.IContentsListener;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.tile.prefab.TileEntityInternalMultiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileEntityAdvanceInductionCell extends TileEntityInternalMultiblock {
    @Getter
    private MachineEnergyContainer<TileEntityAdvanceInductionCell> energyContainer;
    public AdvanceInductionCellTier tier;

    public TileEntityAdvanceInductionCell(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
    }

    @NotNull
    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSide(this::getDirection);
        builder.addContainer(energyContainer = MachineEnergyContainer.internal(this, listener));
        return builder.build();
    }

    @Override
    protected void presetVariables() {
        super.presetVariables();
        tier = MSAttribute.getAdvancedTier(getBlockHolder(), AdvanceInductionCellTier.class);
    }
}
