package com.hamburger0abcde.mekanismsun.common.tiers.storage;

import com.hamburger0abcde.mekanismsun.common.tiers.AdvancedTier;
import com.hamburger0abcde.mekanismsun.common.tiers.IAdvancedTier;
import lombok.Getter;
import mekanism.common.config.value.CachedIntValue;

public enum AdvanceBinTier implements IAdvancedTier {
    SUPERNOVA(AdvancedTier.SUPERNOVA, 2_097_152)
    ;

    @Getter
    private final int advanceStorage;
    private final AdvancedTier advancedTier;
    private CachedIntValue storageReference;
    AdvanceBinTier(AdvancedTier advancedTier, int i) {
        this.advancedTier = advancedTier;
        this.advanceStorage = i;
    }

    @Override
    public AdvancedTier getAdvanceTier() {
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
