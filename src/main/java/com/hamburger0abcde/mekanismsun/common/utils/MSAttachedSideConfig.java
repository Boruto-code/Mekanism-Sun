package com.hamburger0abcde.mekanismsun.common.utils;

import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.lib.transmitter.TransmissionType;
import net.minecraft.Util;

import java.util.EnumMap;
import java.util.Map;

public class MSAttachedSideConfig {
    public static final AttachedSideConfig ALLOYER_MACHINE = Util.make(() -> {
        Map<TransmissionType, AttachedSideConfig.LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.ITEM, AttachedSideConfig.LightConfigInfo.EXTRA_MACHINE);
        configInfo.put(TransmissionType.CHEMICAL, AttachedSideConfig.LightConfigInfo.INPUT_ONLY);
        configInfo.put(TransmissionType.ENERGY, AttachedSideConfig.LightConfigInfo.INPUT_ONLY);
        return new AttachedSideConfig(configInfo);
    });
    public static final AttachedSideConfig TRANSMUTATOR_MACHINE = Util.make(() -> {
        Map<TransmissionType, AttachedSideConfig.LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.ITEM, AttachedSideConfig.LightConfigInfo.MACHINE);
        configInfo.put(TransmissionType.ENERGY, AttachedSideConfig.LightConfigInfo.INPUT_ONLY);
        return new AttachedSideConfig(configInfo);
    });
}
