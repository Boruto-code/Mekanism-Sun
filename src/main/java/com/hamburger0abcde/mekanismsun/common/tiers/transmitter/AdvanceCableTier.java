package com.hamburger0abcde.mekanismsun.common.tiers.transmitter;

import com.hamburger0abcde.mekanismsun.common.config.MSConfig;
import mekanism.common.tier.CableTier;

public class AdvanceCableTier {
    public static long getCapacityAsLong(CableTier tier) {
        if (tier == null) return 8000L;
        return switch (tier) {
            case BASIC -> MSConfig.TIER.supernovaUniversalCableCapacity.get();
            case ADVANCED -> 0L;
            case ELITE -> 0L;
            case ULTIMATE -> 0L;
        };
    }
}
