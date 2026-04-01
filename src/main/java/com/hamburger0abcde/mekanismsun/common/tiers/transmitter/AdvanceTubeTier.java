package com.hamburger0abcde.mekanismsun.common.tiers.transmitter;

import com.hamburger0abcde.mekanismsun.common.config.MSConfig;
import mekanism.common.tier.TubeTier;

public class AdvanceTubeTier {
    public static long getTubePullAmount(TubeTier tier) {
        return switch (tier) {
            case BASIC -> MSConfig.TIER.supernovaPressurizedTubePullAmount.get();
            case ADVANCED -> 0L;
            case ELITE -> 0L;
            case ULTIMATE -> 0L;
        };
    }

    public static long getTubeCapacity(TubeTier tier) {
        return switch (tier) {
            case BASIC -> MSConfig.TIER.supernovaPressurizedTubeCapacity.get();
            case ADVANCED -> 0L;
            case ELITE -> 0L;
            case ULTIMATE -> 0L;
        };
    }
}
