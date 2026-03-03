package com.hamburger0abcde.mekanismsun;

import mekanism.api.text.ILangEntry;
import net.minecraft.Util;

public enum MekanismSunLang implements ILangEntry {
    MEKANISM_SUN("constants", "mod_name"),

    DESCRIPTION_ARTIFICIAL_SUN_CASING("description", "artificial_sun_casing"),
    DESCRIPTION_ARTIFICIAL_SUN_PORT("description", "artificial_sun_port"),

    ARTIFICIAL_SUN("artificial_sun", "artificial_sun"),
    ARTIFICIAL_SUN_PORT_MODE("artificial_sun", "port_mode"),
    ARTIFICIAL_SUN_PORT_MODE_CHANGE("artificial_sun", "port_mode_change"),
    ARTIFICIAL_SUN_PORT_MODE_INPUT("artificial_sun", "port_mode_input"),
    ARTIFICIAL_SUN_PORT_MODE_OUTPUT("artificial_sun", "port_mode_output"),
    ;

    private final String key;

    private MekanismSunLang(String type, String path) {
        this(Util.makeDescriptionId(type, MekanismSun.rl(path)));
    }

    private MekanismSunLang(String key) {
        this.key = key;
    }

    public String getTranslationKey() {
        return this.key;
    }
}
