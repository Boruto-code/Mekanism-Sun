package com.hamburger0abcde.mekanismsun.common.tiles.multiblock.matrix;

import com.hamburger0abcde.mekanismsun.common.block.attribute.MSAttribute;
import com.hamburger0abcde.mekanismsun.common.tiers.storage.AdvanceInductionProviderTier;
import mekanism.common.tile.prefab.TileEntityInternalMultiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityAdvanceInductionProvider extends TileEntityInternalMultiblock {
    public AdvanceInductionProviderTier tier;

    public TileEntityAdvanceInductionProvider(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
    }

    @Override
    protected void presetVariables() {
        super.presetVariables();
        tier = MSAttribute.getAdvancedTier(getBlockHolder(), AdvanceInductionProviderTier.class);
    }
}
