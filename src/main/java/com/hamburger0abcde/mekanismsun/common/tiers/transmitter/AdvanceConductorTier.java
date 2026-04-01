package com.hamburger0abcde.mekanismsun.common.tiers.transmitter;

import com.hamburger0abcde.mekanismsun.common.config.MSConfig;
import mekanism.common.tier.ConductorTier;

public class AdvanceConductorTier {
    public static double getConduction(ConductorTier tier) {
        return switch (tier) {
            case BASIC -> MSConfig.TIER.supernovaThermodynamicConductorConduction.get();
            case ADVANCED -> 0.0;
            case ELITE -> 0.0;
            case ULTIMATE -> 0.0;
        };
    }

    public static double getHeatCapacity(ConductorTier tier) {
        return switch (tier) {
            case BASIC -> MSConfig.TIER.supernovaThermodynamicConductorCapacity.get();
            case ADVANCED -> 0.0;
            case ELITE -> 0.0;
            case ULTIMATE -> 0.0;
        };
    }

    public static double getConductionInsulation(ConductorTier tier) {
        return switch (tier) {
            case BASIC -> MSConfig.TIER.supernovaThermodynamicConductorInsulation.get();
            case ADVANCED -> 0.0;
            case ELITE -> 0.0;
            case ULTIMATE -> 0.0;
        };
    }
}
