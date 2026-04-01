package com.hamburger0abcde.mekanismsun.common;

import lombok.Getter;
import mekanism.common.base.IChemicalConstant;

@Getter
public enum MSChemicalConstants implements IChemicalConstant {
    HELIUM("helium", 0xFF84FFFD, 0, 4.22F, 124.9F),

    ELECTRUM("electrum", 0xFFD9A34D, 0, 1436.84F, 20.76F)
    ;

    private final String name;
    private final int color;
    private final int lightLevel;
    private final float temperature;
    private final float density;

    MSChemicalConstants(String name, int color, int lightLevel, float temperature, float density) {
        this.name = name;
        this.color = color;
        this.lightLevel = lightLevel;
        this.temperature = temperature;
        this.density = density;
    }
}
