package com.hamburger0abcde.mekanismsun.world;

import com.hamburger0abcde.mekanismsun.config.MSConfig;
import com.hamburger0abcde.mekanismsun.config.MSWorldConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mekanism.api.SerializationConstants;
import mekanism.api.functions.FloatSupplier;
import mekanism.common.config.WorldConfig;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;

import java.util.List;
import java.util.function.IntSupplier;

public record MSResizableOreFeatureConfig(List<TargetBlockState> targetStates, /*OreVeinType oreVeinType,*/ IntSupplier size,
                                          FloatSupplier discardChanceOnAirExposure) implements FeatureConfiguration {

    /*public static final Codec<MSResizableOreFeatureConfig> CODEC = RecordCodecBuilder.create(
            builder -> builder.group(
                    Codec.list(TargetBlockState.CODEC).fieldOf(SerializationConstants.TARGET)
                            .forGetter(config -> config.targetStates),
                    OreVeinType.CODEC.fieldOf(SerializationConstants.ORE_TYPE)
                            .forGetter(config -> config.oreVeinType)
            ).apply(builder, ((targetBlockStates, oreVeinType) -> {
                MSWorldConfig.OreVeinConfig veinConfig = MSConfig.WORLD.getVeinConfig(oreVeinType);
                return new MSResizableOreFeatureConfig(targetBlockStates, oreVeinType,
                        veinConfig.maxVeinSize(), veinConfig.discardChanceOnAirExposure());
            }))
    );*/
}
