package com.hamburger0abcde.mekanismsun.common.tiles.multiblock.matrix;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.common.multiblock.matrix.AdvanceMatrixMultiblockData;
import com.hamburger0abcde.mekanismsun.common.registries.MSBlocks;
import com.hamburger0abcde.mekanismsun.common.registries.MSContainerTypes;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.dynamic.SyncMapper;
import mekanism.common.lib.multiblock.MultiblockManager;
import mekanism.common.tile.prefab.TileEntityMultiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityAdvanceInductionCasing extends TileEntityMultiblock<AdvanceMatrixMultiblockData> {
    public TileEntityAdvanceInductionCasing(BlockPos pos, BlockState state) {
        this(MSBlocks.ADVANCE_INDUCTION_CASING, pos, state);
    }

    public TileEntityAdvanceInductionCasing(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
    }

    @Override
    public AdvanceMatrixMultiblockData createMultiblock() {
        return new AdvanceMatrixMultiblockData(this);
    }

    @Override
    public MultiblockManager<AdvanceMatrixMultiblockData> getManager() {
        return MekanismSun.matrixManager;
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        if (container.getType() == MSContainerTypes.ADVANCE_MATRIX_STATS.get()) {
            SyncMapper.INSTANCE.setup(container, AdvanceMatrixMultiblockData.class, this::getMultiblock,
                    AdvanceMatrixMultiblockData.STATS_TAB);
        }
    }
}
