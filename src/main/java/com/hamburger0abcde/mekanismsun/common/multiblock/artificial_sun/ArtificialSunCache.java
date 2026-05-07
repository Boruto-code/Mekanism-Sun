package com.hamburger0abcde.mekanismsun.common.multiblock.artificial_sun;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.common.config.MSConfig;
import mekanism.api.SerializationConstants;
import mekanism.common.lib.multiblock.MultiblockCache;
import mekanism.common.util.NBTUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

public class ArtificialSunCache extends MultiblockCache<ArtificialSunMultiblockData> {
    private double rateLimit = -1;
    private double partialWaste;

    private double getRateLimit() {
        if (rateLimit == -1) {
            //If it never got set it to the default
            return MSConfig.GENERAL.sunDefaultBurnRate.get();
        }
        //Otherwise, return the actual so that it can be manually set down to zero
        return rateLimit;
    }

    @Override
    public void merge(MultiblockCache<ArtificialSunMultiblockData> mergeCache, RejectContents rejectContents) {
        super.merge(mergeCache, rejectContents);
        rateLimit = Math.max(rateLimit, ((ArtificialSunCache) mergeCache).rateLimit);
        partialWaste += ((ArtificialSunCache) mergeCache).partialWaste;
    }

    @Override
    public void apply(HolderLookup.Provider provider, ArtificialSunMultiblockData data) {
        super.apply(provider, data);
        data.rateLimit = Mth.clamp(getRateLimit(), 0, MSConfig.GENERAL.sunDefaultBurnRate.get());
        data.partialWaste = partialWaste;
    }

    @Override
    public void sync(ArtificialSunMultiblockData data) {
        super.sync(data);
        rateLimit = data.rateLimit;
        partialWaste = data.partialWaste;
    }

    @Override
    public void load(HolderLookup.Provider provider, CompoundTag nbtTags) {
        super.load(provider, nbtTags);
        rateLimit = nbtTags.getDouble(SerializationConstants.INJECTION_RATE);
        partialWaste = nbtTags.getDouble(SerializationConstants.PARTIAL_WASTE);
    }

    @Override
    public void save(HolderLookup.Provider provider, CompoundTag nbtTags) {
        super.save(provider, nbtTags);
        nbtTags.putDouble(SerializationConstants.INJECTION_RATE, getRateLimit());
        nbtTags.putDouble(SerializationConstants.PARTIAL_WASTE, partialWaste);
    }
}
