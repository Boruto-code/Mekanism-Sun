package com.hamburger0abcde.mekanismsun;

import lombok.Getter;
import mekanism.common.base.IChemicalConstant;

@Getter
public enum MSChemicalConstants implements IChemicalConstant {
    HELIUM("helium", 0xFF9CF5FF, 0, 4.22F, 124.9F)
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
