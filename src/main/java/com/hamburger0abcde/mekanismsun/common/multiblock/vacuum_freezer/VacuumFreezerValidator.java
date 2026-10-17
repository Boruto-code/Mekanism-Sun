package com.hamburger0abcde.mekanismsun.common.multiblock.vacuum_freezer;

import com.hamburger0abcde.mekanismsun.common.registries.MSBlockTypes;
import mekanism.common.content.blocktype.BlockType;
import mekanism.common.lib.math.voxel.VoxelCuboid;
import mekanism.common.lib.multiblock.CuboidStructureValidator;
import mekanism.common.lib.multiblock.FormationProtocol.CasingType;
import mekanism.common.lib.multiblock.FormationProtocol.StructureRequirement;
import mekanism.common.lib.multiblock.StructureHelper;
import mekanism.common.registries.MekanismBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class VacuumFreezerValidator extends CuboidStructureValidator<VacuumFreezerMultiblockData> {
    private static final VoxelCuboid BOUNDS = new VoxelCuboid(7, 7, 7);

    @Override
    protected StructureRequirement getStructureRequirement(BlockPos pos) {
        VoxelCuboid.WallRelative relative = cuboid.getWallRelative(pos);

        if (!relative.isWall()) {
            return StructureRequirement.INNER;
        }

        if (relative.isOnEdge()) {
            return StructureRequirement.FRAME;
        }

        return StructureRequirement.OTHER;
    }

    @Override
    protected CasingType getCasingType(BlockState state) {
        Block block = state.getBlock();

        if (BlockType.is(block, MSBlockTypes.VACUUM_FREEZER_CASING)) {
            return CasingType.FRAME;
        }

        if (BlockType.is(block, MSBlockTypes.VACUUM_FREEZER_PORT)) {
            return CasingType.VALVE;
        }

        if (block == MekanismBlocks.STRUCTURAL_GLASS.get()) {
            return CasingType.OTHER;
        }

        return CasingType.INVALID;
    }

    @Override
    public boolean precheck() {
        cuboid = StructureHelper.fetchCuboid(structure, BOUNDS, BOUNDS, EnumSet.allOf(VoxelCuboid.CuboidSide.class), 72);

        return cuboid != null;
    }
}
