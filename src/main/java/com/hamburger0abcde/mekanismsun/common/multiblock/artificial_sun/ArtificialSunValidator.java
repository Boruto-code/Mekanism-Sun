package com.hamburger0abcde.mekanismsun.common.multiblock.artificial_sun;

import com.hamburger0abcde.mekanismsun.common.registries.MSBlockTypes;
import mekanism.common.content.blocktype.BlockType;
import mekanism.common.lib.math.voxel.VoxelCuboid;
import mekanism.common.lib.multiblock.CuboidStructureValidator;
import mekanism.common.lib.multiblock.FormationProtocol.CasingType;
import mekanism.common.lib.multiblock.FormationProtocol.StructureRequirement;
import mekanism.common.lib.multiblock.Structure;
import mekanism.common.lib.multiblock.StructureHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class ArtificialSunValidator extends CuboidStructureValidator<ArtificialSunMultiblockData> {
    private static final VoxelCuboid BOUNDS = new VoxelCuboid(7, 7, 7);
    private static final byte[][] ALLOWED_GRID = {
            {0, 0, 1, 1, 1, 0, 0},
            {0, 1, 2, 2, 2, 1, 0},
            {1, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 1},
            {0, 1, 2, 2, 2, 1, 0},
            {0, 0, 1, 1, 1, 0, 0}
    };

    @Override
    protected StructureRequirement getStructureRequirement(BlockPos pos) {
        VoxelCuboid.WallRelative relative = cuboid.getWallRelative(pos);
        if (relative.isWall()) {
            Structure.Axis axis = Structure.Axis.get(cuboid.getSide(pos));
            Structure.Axis h = axis.horizontal(), v = axis.vertical();
            pos = pos.subtract(cuboid.getMinPos());
            return StructureRequirement.REQUIREMENTS[ALLOWED_GRID[h.getCoord(pos)][v.getCoord(pos)]];
        }
        return super.getStructureRequirement(pos);
    }

    @Override
    protected CasingType getCasingType(BlockState state) {
        Block block = state.getBlock();
        if (BlockType.is(block, MSBlockTypes.ARTIFICIAL_SUN_CASING)) {
            return CasingType.FRAME;
        } else if (BlockType.is(block, MSBlockTypes.ARTIFICIAL_SUN_PORT)) {
            return CasingType.VALVE;
        }
        return CasingType.INVALID;
    }

    @Override
    public boolean precheck() {
        cuboid = StructureHelper.fetchCuboid(structure, BOUNDS, BOUNDS, EnumSet.allOf(VoxelCuboid.CuboidSide.class), 72);
        return cuboid != null;
    }
}
