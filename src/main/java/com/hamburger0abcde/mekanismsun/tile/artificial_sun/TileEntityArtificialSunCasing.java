package com.hamburger0abcde.mekanismsun.tile.artificial_sun;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.content.artificial_sun.ArtificialSunMultiblockData;
import com.hamburger0abcde.mekanismsun.registries.MSBlocks;
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

public class TileEntityArtificialSunCasing extends TileEntityMultiblock<ArtificialSunMultiblockData> {
    private boolean handleSound;
    private boolean preActive;

    public TileEntityArtificialSunCasing(BlockPos pos, BlockState state) {
        this(MSBlocks.ARTIFICIAL_SUN_CASING, pos, state);
    }

    public TileEntityArtificialSunCasing(Holder<Block> provider, BlockPos pos, BlockState state) {
        super(provider, pos, state);
    }

    @Override
    protected boolean onUpdateServer(ArtificialSunMultiblockData multiblock) {
        boolean needsPacket = super.onUpdateServer(multiblock);
        boolean active = multiblock.isFormed() && multiblock.handlesSound(this) && multiblock.progress > 0;
        if (active != preActive) {
            preActive = active;
            needsPacket = true;
        }
        return needsPacket;
    }


    @Override
    public ArtificialSunMultiblockData createMultiblock() {
        return new ArtificialSunMultiblockData(this);
    }

    @Override
    public MultiblockManager<ArtificialSunMultiblockData> getManager() {
        return MekanismSun.artificialSunManager;
    }

    @Override
    protected boolean canPlaySound() {
        ArtificialSunMultiblockData multiblock = getMultiblock();
        return multiblock.isFormed() && handleSound && multiblock.progress > 0;
    }

    @NotNull
    @Override
    public CompoundTag getReducedUpdateTag(HolderLookup.Provider provider) {
        CompoundTag updateTag = super.getReducedUpdateTag(provider);
        ArtificialSunMultiblockData multiblock = getMultiblock();
        updateTag.putBoolean(SerializationConstants.HANDLE_SOUND, multiblock.isFormed() && multiblock.handlesSound(this));
        return updateTag;
    }

    @Override
    public void handleUpdateTag(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
        super.handleUpdateTag(tag,provider);
        NBTUtils.setBooleanIfPresent(tag, SerializationConstants.HANDLE_SOUND, value -> handleSound = value);
    }
}
