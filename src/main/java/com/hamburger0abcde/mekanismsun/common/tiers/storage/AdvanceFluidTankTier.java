package com.hamburger0abcde.mekanismsun.common.tiers.storage;

import com.hamburger0abcde.mekanismsun.common.tiers.AdvancedTier;
import com.hamburger0abcde.mekanismsun.common.tiers.IAdvancedTier;
import lombok.Getter;
import mekanism.common.config.value.CachedIntValue;

public enum AdvanceFluidTankTier implements IAdvancedTier {
    SUPERNOVA(AdvancedTier.SUPERNOVA, 4_096_000, 2_048_000)
    ;

    @Getter
    private final int advanceStorage;
    @Getter
    private final int advanceOutput;
    private final AdvancedTier advancedTier;
    private CachedIntValue storageReference;
    private CachedIntValue outputReference;

    AdvanceFluidTankTier(AdvancedTier tier, int storage, int output) {
        advanceStorage = storage;
        advanceOutput = output;
        advancedTier = tier;
    }

    @Override
    public AdvancedTier getAdvanceTier() {
        return advancedTier;
    }

    public int getStorage() {
        return storageReference == null ? getAdvanceStorage() : storageReference.getOrDefault();
    }

    public int getOutput() {
        return outputReference == null ? getAdvanceOutput() : outputReference.getOrDefault();
    }

    /**
     * ONLY CALL THIS FROM TierConfig. It is used to give the FluidTankTier a reference to the actual config value object
     */
    public void setConfigReference(CachedIntValue storageReference, CachedIntValue outputReference) {
        this.storageReference = storageReference;
        this.outputReference = outputReference;
    }
}
