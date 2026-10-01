package com.hamburger0abcde.mekanismsun.common.multiblock.fission;

public record FissionFluidCoolant(double conductivity, double thermalEnthalpy) {
    public FissionFluidCoolant {
        if (conductivity <= 0) {
            throw new IllegalArgumentException("conductivity must be > 0");
        }

        if (thermalEnthalpy <= 0) {
            throw new IllegalArgumentException("thermalEnthalpy must be > 0");
        }
    }
}
