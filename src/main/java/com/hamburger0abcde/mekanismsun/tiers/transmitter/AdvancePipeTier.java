package com.hamburger0abcde.mekanismsun.tiers.transmitter;

import com.hamburger0abcde.mekanismsun.config.MSConfig;
import mekanism.common.tier.PipeTier;

public class AdvancePipeTier {
    public static int getPipePullAmount(PipeTier tier) {
        return switch (tier) {
            case BASIC -> (int) MSConfig.TIER.supernovaMechanicalPipePullAmount.get();
            case ADVANCED -> 0;
            case ELITE -> 0;
            case ULTIMATE -> 0;
        };
    }

    public static long getPipeCapacity(PipeTier tier) {
        return switch (tier) {
            case BASIC -> MSConfig.TIER.supernovaMechanicalPipeCapacity.get();
            case ADVANCED -> 0L;
            case ELITE -> 0L;
            case ULTIMATE -> 0L;
        };
    }
}
