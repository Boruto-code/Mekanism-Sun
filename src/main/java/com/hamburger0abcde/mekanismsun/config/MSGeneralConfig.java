package com.hamburger0abcde.mekanismsun.config;

import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedDoubleValue;
import mekanism.common.config.value.CachedLongValue;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.fluids.FluidType;

public class MSGeneralConfig extends BaseMekanismConfig {
    private static final String ARTIFICIAL_SUN_CATEGORY = "artificial_sun";
    private static final String ITEMS_CATEGORY = "items";

    private final ModConfigSpec configSpec;

    public final CachedLongValue energyPerHydrogen;
    public final CachedLongValue sunFuelCapacity;
    public final CachedLongValue sunEnergyCapacity;
    public final CachedDoubleValue sunDefaultBurnRate;

    MSGeneralConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        MSConfigTranslations.SERVER_SUN.applyToBuilder(builder).push(ARTIFICIAL_SUN_CATEGORY);
        energyPerHydrogen = CachedLongValue.definePositive(this, builder, MSConfigTranslations.SERVER_SUN_HYDROGEN_ENERGY,
                "hydrogenEnergy", 512_000_000L);
        sunFuelCapacity = CachedLongValue.wrap(this, MSConfigTranslations.SERVER_SUN_FUEL_CAPACITY.applyToBuilder(builder)
                .defineInRange("fuelCapacity", FluidType.BUCKET_VOLUME, 1L, 1_000L * FluidType.BUCKET_VOLUME));
        sunEnergyCapacity = CachedLongValue.wrap(this, MSConfigTranslations.SERVER_SUN_ENERGY_CAPACITY.applyToBuilder(builder)
                .defineInRange("energyCapacity", 1_024_000_000L, 1L, Long.MAX_VALUE));
        sunDefaultBurnRate = CachedDoubleValue.wrap(this, MSConfigTranslations.SERVER_SUN_DEFAULT_BURN_RATE.applyToBuilder(builder)
                .defineInRange("defaultBurnRate", 1D, 0.1D, 100D));
        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "general";
    }

    @Override
    public String getTranslation() {
        return "";
    }

    @Override
    public ModConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public ModConfig.Type getConfigType() {
        return ModConfig.Type.SERVER;
    }
}
