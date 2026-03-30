package com.hamburger0abcde.mekanismsun.config;

import com.hamburger0abcde.mekanismsun.tiers.storage.*;
import com.hamburger0abcde.mekanismsun.utils.MSEnumUtils;
import com.hamburger0abcde.mekanismsun.config.MSConfigTranslations.AdvancedTierTranslations;
import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.MekanismConfigTranslations;
import mekanism.common.config.value.CachedIntValue;
import mekanism.common.config.value.CachedLongValue;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Locale;

public class MSTierConfig extends BaseMekanismConfig {
    private final ModConfigSpec configSpec;

    public final CachedLongValue supernovaUniversalCableCapacity;

    public final CachedLongValue supernovaMechanicalPipeCapacity;
    public final CachedLongValue supernovaeMechanicalPipePullAmount;

    public final CachedLongValue supernovaPressurizedTubeCapacity;
    public final CachedLongValue supernovaPressurizedTubePullAmount;

    public final CachedLongValue supernovaLogisticalTransporterSpeed;
    public final CachedLongValue supernovaLogisticalTransporterPullAmount;

    public final CachedLongValue supernovaThermodynamicConductorConduction;
    public final CachedLongValue supernovaThermodynamicConductorCapacity;

    MSTierConfig() {

    }

    private void addStoragesCategory(ModConfigSpec.Builder builder) {
        addBinCategory(builder);
        addInductionCategory(builder);
        addEnergyCubeCategory(builder);
        addFluidTankCategory(builder);
        addGasTankCategory(builder);
    }

    private void addBinCategory(ModConfigSpec.Builder builder) {
        MekanismConfigTranslations.TIER_BIN.applyToBuilder(builder).push("bins");
        for (AdvanceBinTier tier : MSEnumUtils.BIN_TIERS) {
            AdvancedTierTranslations translations = AdvancedTierTranslations.create(tier);
            String tierName = tier.getAdvanceTier().getSimpleName().toLowerCase(Locale.ROOT);
            CachedIntValue storageReference = CachedIntValue.wrap(this, translations.first().applyToBuilder(builder)
                    .defineInRange(tierName + "Storage", tier.getAdvanceStorage(), 1, Integer.MAX_VALUE));
            tier.setConfigReference(storageReference);
        }
        builder.pop();
    }

    private void addInductionCategory(ModConfigSpec.Builder builder) {
        MekanismConfigTranslations.TIER_INDUCTION.applyToBuilder(builder).push("induction");
        for (AdvanceInductionCellTier tier : MSEnumUtils.INDUCTION_CELL_TIERS) {
            AdvancedTierTranslations translations = AdvancedTierTranslations.create(tier);
            String tierName = tier.getAdvanceTier().getSimpleName().toLowerCase(Locale.ROOT);
            CachedLongValue storageReference = CachedLongValue.wrap(this, translations.first().applyToBuilder(builder)
                    .defineInRange(tierName + "Storage", tier.getAdvanceMaxEnergy(), 1, Long.MAX_VALUE));
            tier.setConfigReference(storageReference);
        }
        for (AdvanceInductionProviderTier tier : MSEnumUtils.INDUCTION_PROVIDER_TIERS) {
            AdvancedTierTranslations translations = AdvancedTierTranslations.create(tier);
            String tierName = tier.getAdvanceTier().getSimpleName().toLowerCase(Locale.ROOT);
            CachedLongValue outputReference = CachedLongValue.wrap(this, translations.second().applyToBuilder(builder)
                    .defineInRange(tierName + "Output", tier.getAdvanceOutput(), 1, Long.MAX_VALUE));
            tier.setConfigReference(outputReference);
        }
        builder.pop();
    }

    private void addEnergyCubeCategory(ModConfigSpec.Builder builder) {
        MekanismConfigTranslations.TIER_ENERGY_CUBE.applyToBuilder(builder).push("energy_cubes");
        for (AdvanceEnergyCubeTier tier : MSEnumUtils.ENERGY_CUBE_TIERS) {
            AdvancedTierTranslations translations = AdvancedTierTranslations.create(tier);
            String tierName = tier.getAdvanceTier().getSimpleName().toLowerCase(Locale.ROOT);
            CachedLongValue storageReference = CachedLongValue.wrap(this, translations.first().applyToBuilder(builder)
                    .defineInRange(tierName + "Storage", tier.getAdvanceMaxEnergy(), 1, Long.MAX_VALUE));
            CachedLongValue outputReference = CachedLongValue.wrap(this, translations.second().applyToBuilder(builder)
                    .defineInRange(tierName + "Output", tier.getAdvanceOutput(), 1, Long.MAX_VALUE));
            tier.setConfigReference(storageReference, outputReference);
        }
        builder.pop();
    }

    private void addFluidTankCategory(ModConfigSpec.Builder builder) {
        MekanismConfigTranslations.TIER_FLUID_TANK.applyToBuilder(builder).push("fluid_tanks");
        for (AdvanceFluidTankTier tier : MSEnumUtils.FLUID_TANK_TIERS) {
            AdvancedTierTranslations translations = AdvancedTierTranslations.create(tier);
            String tierName = tier.getAdvanceTier().getSimpleName().toLowerCase(Locale.ROOT);
            CachedIntValue storageReference = CachedIntValue.wrap(this, translations.first().applyToBuilder(builder)
                    .defineInRange(tierName + "Storage", tier.getAdvanceStorage(), 1, Integer.MAX_VALUE));
            CachedIntValue outputReference = CachedIntValue.wrap(this, translations.second().applyToBuilder(builder)
                    .defineInRange(tierName + "Output", tier.getAdvanceOutput(), 1, Integer.MAX_VALUE));
            tier.setConfigReference(storageReference, outputReference);
        }
        builder.pop();
    }

    private void addGasTankCategory(ModConfigSpec.Builder builder) {
        MekanismConfigTranslations.TIER_CHEMICAL_TANK.applyToBuilder(builder).push("chemical_tanks");
        for (AdvanceChemicalTankTier tier : MSEnumUtils.CHEMICAL_TANK_TIERS) {
            AdvancedTierTranslations translations = AdvancedTierTranslations.create(tier);
            String tierName = tier.getAdvanceTier().getSimpleName().toLowerCase(Locale.ROOT);
            CachedLongValue storageReference = CachedLongValue.wrap(this, translations.first().applyToBuilder(builder)
                    .defineInRange(tierName + "Storage", tier.getAdvanceStorage(), 1, Long.MAX_VALUE));
            CachedLongValue outputReference = CachedLongValue.wrap(this, translations.second().applyToBuilder(builder)
                    .defineInRange(tierName + "Output", tier.getAdvanceOutput(), 1, Long.MAX_VALUE));
            tier.setConfigReference(storageReference, outputReference);
        }
        builder.pop();
    }

    @Override
    public String getFileName() {
        return "tiers";
    }

    @Override
    public String getTranslation() {
        return "Tier Config";
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
