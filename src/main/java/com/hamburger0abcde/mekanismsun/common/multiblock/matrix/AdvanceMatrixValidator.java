package com.hamburger0abcde.mekanismsun.common.multiblock.matrix;

import com.hamburger0abcde.mekanismsun.common.registries.MSBlockTypes;
import com.hamburger0abcde.mekanismsun.common.tiles.multiblock.matrix.TileEntityAdvanceInductionCell;
import com.hamburger0abcde.mekanismsun.common.tiles.multiblock.matrix.TileEntityAdvanceInductionProvider;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import mekanism.common.content.blocktype.BlockType;
import mekanism.common.lib.multiblock.CuboidStructureValidator;
import mekanism.common.lib.multiblock.FormationProtocol;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

import java.util.ArrayList;
import java.util.List;

public class AdvanceMatrixValidator extends CuboidStructureValidator<AdvanceMatrixMultiblockData> {
    private final List<TileEntityAdvanceInductionCell> cells = new ArrayList<>();
    private final List<TileEntityAdvanceInductionProvider> providers = new ArrayList<>();

    @Override
    protected FormationProtocol.CasingType getCasingType(BlockState state) {
        Block block = state.getBlock();
        if (BlockType.is(block, MSBlockTypes.ADVANCE_INDUCTION_CASING)) {
            return FormationProtocol.CasingType.FRAME;
        } else if (BlockType.is(block, MSBlockTypes.ADVANCE_INDUCTION_PORT)) {
            return FormationProtocol.CasingType.VALVE;
        }
        return FormationProtocol.CasingType.INVALID;
    }

    @Override
    public boolean validateInner(BlockState state, Long2ObjectMap<ChunkAccess> chunkMap, BlockPos pos) {
        if (super.validateInner(state, chunkMap, pos)) {
            return true;
        }
        if (BlockType.is(state.getBlock(),
                MSBlockTypes.SUPERNOVA_INDUCTION_CELL,
                MSBlockTypes.SUPERNOVA_INDUCTION_PROVIDER)) {
            //Compare blocks against the type before bothering to look up the tile
            BlockEntity tile = WorldUtils.getTileEntity(world, chunkMap, pos);
            if (tile instanceof TileEntityAdvanceInductionCell cell) {
                cells.add(cell);
                return true;
            } else if (tile instanceof TileEntityAdvanceInductionProvider provider) {
                providers.add(provider);
                return true;
            }
            //Else something went wrong
        }
        return false;
    }

    @Override
    public FormationProtocol.FormationResult postcheck(AdvanceMatrixMultiblockData structure, Long2ObjectMap<ChunkAccess> chunkMap) {
        for (TileEntityAdvanceInductionCell cell : cells) {
            structure.addCell(cell);
        }
        for (TileEntityAdvanceInductionProvider provider : providers) {
            structure.addProvider(provider);
        }
        return FormationProtocol.FormationResult.SUCCESS;
    }
}
