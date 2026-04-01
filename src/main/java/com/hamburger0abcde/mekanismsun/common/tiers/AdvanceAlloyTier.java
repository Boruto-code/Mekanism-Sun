package com.hamburger0abcde.mekanismsun.common.tiers;

import lombok.Getter;

public enum AdvanceAlloyTier implements IAdvancedTier {
    SUPERNOVA("supernova", AdvancedTier.SUPERNOVA)
    ;

    @Getter
    public final String name;
    public final AdvancedTier advancedTier;

    AdvanceAlloyTier(String name, AdvancedTier tier) {
        this.advancedTier = tier;
        this.name = name;
    }

    @Override
    public AdvancedTier getAdvanceTier() {
        return advancedTier;
    }
}
