package com.hamburger0abcde.mekanismsun.tiers.transmitter;

import com.hamburger0abcde.mekanismsun.config.MSConfig;
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
