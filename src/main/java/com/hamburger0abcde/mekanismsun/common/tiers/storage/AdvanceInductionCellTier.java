package com.hamburger0abcde.mekanismsun.common.tiers.storage;

import com.hamburger0abcde.mekanismsun.common.tiers.AdvancedTier;
import com.hamburger0abcde.mekanismsun.common.tiers.IAdvancedTier;
import lombok.Getter;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.common.config.value.CachedLongValue;
import org.jetbrains.annotations.Nullable;

@NothingNullByDefault
public enum AdvanceInductionCellTier implements IAdvancedTier {
    SUPERNOVA(AdvancedTier.SUPERNOVA, 32_768_000_000_000L)
    ;

    @Getter
    private final long advanceMaxEnergy;
    private final AdvancedTier advancedTier;
    @Nullable
    private CachedLongValue storageReference;

    AdvanceInductionCellTier(AdvancedTier tier, long max) {
        advanceMaxEnergy = max;
        advancedTier = tier;
    }

    @Override
    public AdvancedTier getAdvanceTier() {
        return advancedTier;
    }

    public long getMaxEnergy() {
        return storageReference == null ? getAdvanceMaxEnergy() : storageReference.getOrDefault();
    }

    /**
     * ONLY CALL THIS FROM TierConfig. It is used to give the InductionCellTier a reference to the actual config value object
     */
    public void setConfigReference(CachedLongValue storageReference) {
        this.storageReference = storageReference;
    }
}
