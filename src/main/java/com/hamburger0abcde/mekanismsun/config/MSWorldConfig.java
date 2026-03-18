package com.hamburger0abcde.mekanismsun.config;

import com.google.common.collect.ImmutableList;
import com.hamburger0abcde.mekanismsun.world.MSOreType;
import lombok.Getter;
import mekanism.api.functions.FloatSupplier;
import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.IMekanismConfig;
import mekanism.common.config.MekanismConfigTranslations;
import mekanism.common.config.WorldConfig;
import mekanism.common.config.value.CachedBooleanValue;
import mekanism.common.config.value.CachedFloatValue;
import mekanism.common.config.value.CachedIntValue;
import mekanism.common.resource.ore.BaseOreConfig;
import mekanism.common.world.height.ConfigurableHeightRange;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

public class MSWorldConfig extends BaseMekanismConfig {
    @Getter
    private final ModConfigSpec configSpec;
    private final Map<MSOreType, OreConfig> ores = new EnumMap(MSOreType.class);
    MSWorldConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        for(MSOreType ore : MSOreType.values()) {
            this.ores.put(ore, new OreConfig(this, builder, ore));
        }
        this.configSpec = builder.build();
    }

    public String getFileName() {
        return "world";
    }

    public String getTranslation() {
        return "Mekanism Sun World Config";
    }

    public ModConfig.Type getConfigType() {
        return ModConfig.Type.SERVER;
    }

    public OreVeinConfig getVeinConfig(MSOreType.OreVeinType oreVeinType) {
        return ((OreConfig) this.ores.get(oreVeinType.type())).veinConfigs.get(oreVeinType.index());
    }

    public static record OreVeinConfig(BooleanSupplier shouldGenerate, CachedIntValue perChunk, IntSupplier maxVeinSize,
                                       FloatSupplier discardChanceOnAirExposure, ConfigurableHeightRange range) {}

    private static class OreConfig {
        private final CachedBooleanValue shouldGenerate;
        private final List<OreVeinConfig> veinConfigs;

        private OreConfig(IMekanismConfig config, ModConfigSpec.Builder builder, MSOreType oreType) {
            String ore = oreType.getResource().getRegistrySuffix();
            MekanismConfigTranslations.OreConfigTranslations translations = MekanismConfigTranslations.OreConfigTranslations.create(ore);
            translations.topLevel().applyToBuilder(builder).push(ore);
            this.shouldGenerate = CachedBooleanValue.wrap(config, translations.shouldGenerate()
                    .applyToBuilder(builder).define("shouldGenerate", true));
            ImmutableList.Builder<OreVeinConfig> veinBuilder = ImmutableList.builder();

            for(BaseOreConfig baseConfig : oreType.getBaseConfigs()) {
                MekanismConfigTranslations.OreVeinConfigTranslations veinTranslations = MekanismConfigTranslations.OreVeinConfigTranslations
                        .create(ore, baseConfig.name());
                veinTranslations.topLevel().applyToBuilder(builder).push(baseConfig.name());
                CachedBooleanValue shouldVeinTypeGenerate = CachedBooleanValue.wrap(config, veinTranslations.shouldGenerate().applyToBuilder(builder).define("shouldGenerate", true));
                veinBuilder.add(new OreVeinConfig(() -> this.shouldGenerate.get() && shouldVeinTypeGenerate.get(),
                        CachedIntValue.wrap(config, veinTranslations.perChunk().applyToBuilder(builder)
                                .defineInRange("perChunk", baseConfig.perChunk(), 1, 256)),
                        CachedIntValue.wrap(config, veinTranslations.maxVeinSize().applyToBuilder(builder)
                                .defineInRange("maxVeinSize", baseConfig.maxVeinSize(), 1, 64)),
                        CachedFloatValue.wrap(config, veinTranslations.discardChanceOnAirExposure().applyToBuilder(builder)
                                .defineInRange("discardChanceOnAirExposure", (double) baseConfig.discardChanceOnAirExposure(),
                                        (double) 0.0F, (double) 1.0F)),
                        ConfigurableHeightRange.create(config, builder, veinTranslations, baseConfig)));
                builder.pop();
            }

            this.veinConfigs = veinBuilder.build();
            builder.pop();
        }
    }
}
