package com.hamburger0abcde.mekanismsun.config;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import mekanism.common.config.IConfigTranslation;
import mekanism.common.config.TranslationPreset;
import net.minecraft.Util;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum MSConfigTranslations implements IConfigTranslation {
    SERVER_SUN("server.sun", "Artificial Sun Settings", "Edit Artificial Sun Settings"),
    SERVER_SUN_HYDROGEN_ENERGY("server.sun.hydrogen_energy", "Energy Per Hydrogen",
            "Amount of energy created from each whole mB of hydrogen."),
    SERVER_SUN_FUEL_CAPACITY("server.sun.fuel_capacity", "Fuel Capacity",
            "Amount of fuel (mB) that the artificial sun can store."),
    SERVER_SUN_ENERGY_CAPACITY("server.sun.energy_capacity", "Energy Capacity",
            "Amount of energy (Joules) the artificial sun can store."),
    SERVER_SUN_DEFAULT_BURN_RATE("server.sun.default_burn_rate", "Default Burn Rate",
            "The default burn rate of the fission reactor.")
    ;

    private final String key;
    private final String title;
    private final String tooltip;
    @Nullable
    private final String button;

    MSConfigTranslations(TranslationPreset preset, String type) {
        this(preset.path(type), preset.title(type), preset.tooltip(type));
    }

    MSConfigTranslations(String path, String title, String tooltip) {
        this(path, title, tooltip, false);
    }

    MSConfigTranslations(String path, String title, String tooltip, boolean isSection) {
        this(path, title, tooltip, IConfigTranslation.getSectionTitle(title, isSection));
    }

    MSConfigTranslations(String path, String title, String tooltip, @Nullable String button) {
        this.key = Util.makeDescriptionId("configuration", MekanismSun.rl(path));
        this.title = title;
        this.tooltip = tooltip;
        this.button = button;
    }

    @NotNull
    @Override
    public String getTranslationKey() {
        return key;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String tooltip() {
        return tooltip;
    }

    @Nullable
    @Override
    public String button() {
        return button;
    }
}
