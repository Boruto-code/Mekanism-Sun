package com.hamburger0abcde.mekanismsun.common.tiles.multiblock.vacuum_freezer;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.common.multiblock.vacuum_freezer.VacuumFreezerMultiblockData;
import com.hamburger0abcde.mekanismsun.common.registries.MSBlocks;
import mekanism.api.SerializationConstants;
import mekanism.common.lib.multiblock.MultiblockManager;
import mekanism.common.tile.prefab.TileEntityMultiblock;
import mekanism.common.util.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileEntityVacuumFreezerCasing extends TileEntityMultiblock<VacuumFreezerMultiblockData> {
    private boolean handleSound;
    private boolean prevActive;

    public TileEntityVacuumFreezerCasing(BlockPos pos, BlockState state) {
        this(MSBlocks.TUNGSTENSTEEL_CASING, pos, state);
    }

    public TileEntityVacuumFreezerCasing(Holder<Block> provider, BlockPos pos, BlockState state) {
        super(provider, pos, state);
    }

    @Override
    protected boolean onUpdateServer(VacuumFreezerMultiblockData multiblock) {
        boolean needsPacket = super.onUpdateServer(multiblock);
        boolean active = multiblock.isFormed() && multiblock.handlesSound(this) && multiblock.progress > 0;
        if (active != prevActive) {
            prevActive = active;
            needsPacket = true;
        }
        return needsPacket;
    }

    @Override
    public VacuumFreezerMultiblockData createMultiblock() {
        return new VacuumFreezerMultiblockData(this);
    }

    @Override
    public MultiblockManager<VacuumFreezerMultiblockData> getManager() {
        return MekanismSun.vacuumFreezerManager;
    }

    @Override
    protected boolean canPlaySound() {
        VacuumFreezerMultiblockData multiblock = getMultiblock();
        return multiblock.isFormed() && handleSound && multiblock.progress > 0;
    }

    @NotNull
    @Override
    public CompoundTag getReducedUpdateTag(HolderLookup.Provider provider) {
        CompoundTag updateTag = super.getReducedUpdateTag(provider);
        VacuumFreezerMultiblockData multiblock = getMultiblock();
        updateTag.putBoolean(SerializationConstants.HANDLE_SOUND, multiblock.isFormed() && multiblock.handlesSound(this));
        return updateTag;
    }

    @Override
    public void handleUpdateTag(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
        super.handleUpdateTag(tag, provider);
        NBTUtils.setBooleanIfPresent(tag, SerializationConstants.HANDLE_SOUND, value -> handleSound = value);
    }
}
