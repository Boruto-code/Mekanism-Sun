package com.hamburger0abcde.mekanismsun.common.tiers;

import mekanism.common.tier.*;

public class TierColor {
    public static int cableTierToColor(CableTier tier) {
        return switch (tier) {
            case BASIC -> 0xD6A352;
            case ADVANCED -> 0;
            case ELITE -> 0;
            case ULTIMATE -> 0;
        };
    }

    public static int pipeTierToColor(PipeTier tier) {
        return switch (tier) {
            case BASIC -> 0xD6A352;
            case ADVANCED -> 0;
            case ELITE -> 0;
            case ULTIMATE -> 0;
        };
    }

    public static int tubeTierToColor(TubeTier tier) {
        return switch (tier) {
            case BASIC -> 0xD6A352;
            case ADVANCED -> 0;
            case ELITE -> 0;
            case ULTIMATE -> 0;
        };
    }

    public static int transporterTierToColor(TransporterTier tier) {
        return switch (tier) {
            case BASIC -> 0xD6A352;
            case ADVANCED -> 0;
            case ELITE -> 0;
            case ULTIMATE -> 0;
        };
    }

    public static int conductorTierToColor(ConductorTier tier) {
        return switch (tier) {
            case BASIC -> 0xD6A352;
            case ADVANCED -> 0;
            case ELITE -> 0;
            case ULTIMATE -> 0;
        };
    }
}
