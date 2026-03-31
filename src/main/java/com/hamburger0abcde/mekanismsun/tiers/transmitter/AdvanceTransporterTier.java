package com.hamburger0abcde.mekanismsun.tiers.transmitter;

import com.hamburger0abcde.mekanismsun.config.MSConfig;
import mekanism.common.tier.TransporterTier;

public class AdvanceTransporterTier {
    public static int getSpeed(TransporterTier tier) {
        return switch (tier) {
            case BASIC -> (int) MSConfig.TIER.supernovaLogisticalTransporterSpeed.get();
            case ADVANCED -> 0;
            case ELITE -> 0;
            case ULTIMATE -> 0;
        };
    }

    public static int getPullAmount(TransporterTier tier) {
        return switch (tier) {
            case BASIC -> (int) MSConfig.TIER.supernovaLogisticalTransporterPullAmount.get();
            case ADVANCED -> 0;
            case ELITE -> 0;
            case ULTIMATE -> 0;
        };
    }
}
