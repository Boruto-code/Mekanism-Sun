package com.hamburger0abcde.mekanismsun.common.multiblock;

import com.hamburger0abcde.mekanismsun.common.registries.MSBlocks;
import mekanism.common.command.builders.StructureBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MSBuilders {
    public static class ArtificialSunBuilder extends StructureBuilder {
        public ArtificialSunBuilder() {
            super(7, 7, 7);
        }

        @Override
        protected void build(Level world, BlockPos start, boolean empty) {
            buildPartialFrame(world, start, 1);
            buildWalls(world, start);
            buildInteriorLayers(world, start, 1, 5, Blocks.AIR.defaultBlockState());
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
            for (int x = -2; x < 2; ++x) {
                for (int y = -2; y < 2; ++y) {
                    for (int z = -2; z < 2; ++z) {
                        // Check whether one or all three vars ar 0 or -1.
                        // Something that checks whether its exactly one would be better, but that seems very hard.
                        if ((x == -1) == (y == -1) == (z == -1) == (x == 0) == (y == 0) != (z == 0)) {
                            // Check that not all three vars are 0 or -1.
                            if (!(x == -1 || x == 0) || !(y == -1 || y == 0) || !(z == -1 || z == 0)) {
                                mutablePos.setWithOffset(start, x < 0 ? sizeX + x : x, y < 0 ? sizeY + y : y, z < 0 ? sizeZ + z : z);
                                world.setBlockAndUpdate(mutablePos, getCasing());
                            }
                        }
                    }
                }
            }
        }

        @Override
        protected BlockState getCasing() {
            return MSBlocks.ARTIFICIAL_SUN_CASING.defaultState();
        }
    }
}
