package com.hamburger0abcde.mekanismsun.config;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import mekanism.common.config.IMekanismConfig;
import mekanism.common.config.MekanismConfigHelper;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.event.config.ModConfigEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MSConfig {
    private MSConfig() {}

    private static final Map<IConfigSpec, IMekanismConfig> KNOWN_CONFIGS = new HashMap<>();
    public static final MSGeneralConfig GENERAL = new MSGeneralConfig();
    public static final MSTierConfig TIER = new MSTierConfig();

    public static void registerConfigs(ModContainer container) {
        MSConfigHelper.registerConfig(KNOWN_CONFIGS, container, GENERAL);
    }

    public static void onConfigLoad(ModConfigEvent configEvent) {
        MekanismConfigHelper.onConfigLoad(configEvent, MekanismSun.MODID, KNOWN_CONFIGS);
    }

    public static Collection<IMekanismConfig> getConfigs() {
        return Collections.unmodifiableCollection(KNOWN_CONFIGS.values());
    }
}
