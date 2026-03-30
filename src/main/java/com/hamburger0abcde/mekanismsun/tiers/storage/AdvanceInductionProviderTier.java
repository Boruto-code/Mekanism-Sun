package com.hamburger0abcde.mekanismsun.tiers.storage;

import com.hamburger0abcde.mekanismsun.tiers.AdvancedTier;
import com.hamburger0abcde.mekanismsun.tiers.IAdvancedTier;
import lombok.Getter;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.common.config.value.CachedLongValue;
import org.jetbrains.annotations.Nullable;

@NothingNullByDefault
public enum AdvanceInductionProviderTier implements IAdvancedTier {
    SUPERNOVA(AdvancedTier.SUPERNOVA, 1_048_576_000L)
    ;

    @Getter
    private final long advanceOutput;
    private final AdvancedTier advancedTier;
    @Nullable
    private CachedLongValue outputReference;

    AdvanceInductionProviderTier(AdvancedTier tier, long out) {
        advanceOutput = out;
        advancedTier = tier;
    }

    @Override
    public AdvancedTier getAdvanceTier() {
        return advancedTier;
    }

    public long getOutput() {
        return outputReference == null ? getAdvanceOutput() : outputReference.getOrDefault();
    }

    /**
     * ONLY CALL THIS FROM TierConfig. It is used to give the InductionProviderTier a reference to the actual config value object
     */
    public void setConfigReference(CachedLongValue outputReference) {
        this.outputReference = outputReference;
    }
}
