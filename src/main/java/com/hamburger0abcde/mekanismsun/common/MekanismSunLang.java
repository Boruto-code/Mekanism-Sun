package com.hamburger0abcde.mekanismsun.common;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import mekanism.api.text.ILangEntry;
import net.minecraft.Util;

public enum MekanismSunLang implements ILangEntry {
    MEKANISM_SUN("constants", "mod_name"),

    DESCRIPTION_ARTIFICIAL_SUN_CASING("description", "artificial_sun_casing"),
    DESCRIPTION_ARTIFICIAL_SUN_PORT("description", "artificial_sun_port"),
    DESCRIPTION_VACUUM_FREEZER_CASING("description", "vacuum_freezer_casing"),
    DESCRIPTION_VACUUM_FREEZER_PORT("description", "vacuum_freezer_port"),
    DESCRIPTION_ALLOYER("description", "alloyer"),
    DESCRIPTION_TRANSMUTATOR("description", "transmutator"),
    DESCRIPTION_ELECTRIC_NEUTRON_ACTIVATOR("description", "electric_neutron_activator"),
    DESCRIPTION_ASSEMBLER("description", "assembler"),

    ARTIFICIAL_SUN("artificial_sun", "artificial_sun"),
    ARTIFICIAL_SUN_PORT_MODE("artificial_sun", "port_mode"),
    ARTIFICIAL_SUN_PORT_MODE_CHANGE("artificial_sun", "port_mode_change"),
    ARTIFICIAL_SUN_PORT_MODE_INPUT("artificial_sun", "port_mode_input"),
    ARTIFICIAL_SUN_PORT_MODE_OUTPUT("artificial_sun", "port_mode_output"),
    ARTIFICIAL_SUN_BURN_RATE_LIMIT("artificial_sun", "burn_rate_limit"),

    ADVANCE_MATRIX("advance_matrix", "advance_matrix"),

    VACUUM_FREEZER("vacuum_freezer", "vacuum_freezer"),
    VACUUM_FREEZER_PORT_MODE("vacuum_freezer", "port_mode"),
    VACUUM_FREEZER_PORT_MODE_CHANGE("vacuum_freezer", "port_mode_change"),
    VACUUM_FREEZER_PORT_MODE_INPUT("vacuum_freezer", "port_mode_input"),
    VACUUM_FREEZER_PORT_MODE_OUTPUT("vacuum_freezer", "port_mode_output"),
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
