package com.hamburger0abcde.mekanismsun.tiers.storage;

import com.hamburger0abcde.mekanismsun.tiers.AdvanceTier;
import com.hamburger0abcde.mekanismsun.tiers.IAdvancedTier;
import lombok.Getter;
import mekanism.common.config.value.CachedIntValue;

public enum AdvanceBinTier implements IAdvancedTier {
    SUPERNOVA(AdvanceTier.SUPERNOVA, 2_097_152)
    ;

    @Getter
    private final int advanceStorage;
    private final AdvanceTier advancedTier;
    private CachedIntValue storageReference;
    AdvanceBinTier(AdvanceTier advancedTier, int i) {
        this.advancedTier = advancedTier;
        this.advanceStorage = i;
    }

    @Override
    public AdvanceTier getAdvanceTier() {
        return advancedTier;
    }

    public int getStorage() {
        return storageReference == null ? getAdvanceStorage() : storageReference.getOrDefault();
    }

    /**
     * ONLY CALL THIS FROM TierConfig. It is used to give the BinTier a reference to the actual config value object
     */
    public void setConfigReference(CachedIntValue storageReference) {
        this.storageReference = storageReference;
    }
}
