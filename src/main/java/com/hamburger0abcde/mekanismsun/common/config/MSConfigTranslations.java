package com.hamburger0abcde.mekanismsun.common.config;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.common.tiers.IAdvancedTier;
import com.hamburger0abcde.mekanismsun.common.tiers.storage.*;
import mekanism.common.config.IConfigTranslation;
import mekanism.common.config.TranslationPreset;
import net.minecraft.Util;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public enum MSConfigTranslations implements IConfigTranslation {
    SERVER_SUN("server.sun", "Artificial Sun Settings", "Edit Artificial Sun Settings"),
    SERVER_SUN_HYDROGEN_ENERGY("server.sun.hydrogen_energy", "Energy Per Hydrogen",
            "Amount of energy created from each whole mB of hydrogen."),
    SERVER_SUN_FUEL_CAPACITY("server.sun.fuel_capacity", "Fuel Capacity",
            "Amount of fuel (mB) that the artificial sun can store."),
    SERVER_SUN_ENERGY_CAPACITY("server.sun.energy_capacity", "Energy Capacity",
            "Amount of energy (Joules) the artificial sun can store."),
    SERVER_SUN_DEFAULT_BURN_RATE("server.sun.default_burn_rate", "Default Burn Rate",
            "The default burn rate of the artificial sun."),
    SERVER_SUN_MAX_BURN_RATE("server.sun.max_burn_rate", "Max Burn Rate",
            "The max burn rate of the artificial sun."),

    SUPERNOVA_UNIVERSAL_CABLE_CAPACITY("tier.cable.supernova.capacity", "Supernova",
            "Internal buffer in Joules of Supernova Universal Cable."),

    SUPERNOVA_MECHANICAL_PIPE_CAPACITY("tier.pipe.supernova.capacity", "Supernova",
            "Capacity of Supernova Mechanical Pipe in mb."),
    SUPERNOVA_MECHANICAL_PIPE_PULL_AMOUNT("tier.pipe.supernova.pull_amount", "Supernova",
            "Pump rate of Supernova Mechanical Pipe in mb."),

    SUPERNOVA_PRESSURIZED_TUBE_CAPACITY("tier.tube.supernova.capacity", "Supernova",
            "Capacity of Supernova Pressurized Tube in mb."),
    SUPERNOVA_PRESSURIZED_TUBE_PULL_AMOUNT("tier.tube.supernova.pull_amount", "Supernova",
            "Pump rate of Supernova Pressurized Tube in mb."),

    SUPERNOVA_LOGISTICAL_TRANSPORTER_SPEED("tier.transporter.supernova.speed", "Supernova",
            "Five times the travel speed in m/s of Supernova Logistical Transporter."),
    SUPERNOVA_LOGISTICAL_TRANSPORTER_PULL_AMOUNT("tier.transporter.supernova.pull_amount", "Supernova",
            "Item throughput rate of Supernova Logistical Transporter in items/half second."),

    SUPERNOVA_THERMODYNAMIC_CONDUCTOR_CONDUCTION("tier.conductor.supernova.conduction", "Supernova",
            "Conduction value of Supernova Thermodynamic Conductor."),
    SUPERNOVA_THERMODYNAMIC_CONDUCTOR_CAPACITY("tier.conductor.supernova.capacity", "Supernova",
            "Heat capacity of Supernova Thermodynamic Conductor."),
    SUPERNOVA_THERMODYNAMIC_CONDUCTOR_INSULATION("tier.conductor.supernova.insulation", "Supernova",
            "Insulation value of Supernova Thermodynamic Conductor."),
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

    public record AdvancedTierTranslations(@Nullable IConfigTranslation first, @Nullable IConfigTranslation second,
                                           @Nullable IConfigTranslation third) {

        public AdvancedTierTranslations {
            if (first == null && second == null) {
                throw new IllegalArgumentException("Tier Translations must have at least a first, second, or third tooltip");
            }
        }

        public IConfigTranslation[] toArray() {
            return Stream.of(first, second, third).filter(Objects::nonNull).toArray(IConfigTranslation[]::new);
        }

        @NotNull
        @Override
        public IConfigTranslation first() {
            if (first == null) {
                throw new IllegalStateException("This method should not be called when first is null. Define first");
            }
            return first;
        }

        @NotNull
        @Override
        public IConfigTranslation second() {
            if (second == null) {
                throw new IllegalStateException("This method should not be called when storage is null. Define second");
            }
            return second;
        }

        @NotNull
        @Override
        public IConfigTranslation third() {
            if (third == null) {
                throw new IllegalStateException("This method should not be called when third is null. Define third");
            }
            return third;
        }

        private static String getKey(String type, String tier, String path) {
            return Util.makeDescriptionId("configuration", MekanismSun.rl("tier." + type + "." + tier + "." + path));
        }

        public static AdvancedTierTranslations create(IAdvancedTier tier, String type,
                                                      @Nullable UnaryOperator<String> storageTooltip,
                                                      @Nullable UnaryOperator<String> outputTooltip) {
            return create(tier, type, storageTooltip, outputTooltip, " Output Rate");
        }

        public static AdvancedTierTranslations create(IAdvancedTier tier, String type,
                                                      @Nullable UnaryOperator<String> storageTooltip,
                                                      @Nullable UnaryOperator<String> outputTooltip,
                                                      String rateSuffix) {
            String tierName = tier.getAdvanceTier().getSimpleName();
            String key = tierName.toLowerCase(Locale.ROOT);
            return new AdvancedTierTranslations(
                    storageTooltip == null ? null : new ConfigTranslation(getKey(type, key, "storage"),
                            tierName + " Storage", storageTooltip.apply(tierName)),
                    outputTooltip == null ? null : new ConfigTranslation(getKey(type, key, "rate"),
                            tierName + rateSuffix, outputTooltip.apply(tierName)),
                    null
            );
        }

        public static AdvancedTierTranslations create(AdvanceEnergyCubeTier tier) {
            return create(tier, "energy_cube", name -> "Maximum number of Joules " + name + " energy cubes can store.",
                    name -> "Output rate in Joules of " + name + " energy cubes."
            );
        }

        public static AdvancedTierTranslations create(AdvanceFluidTankTier tier) {
            return create(tier, "fluid_tank", name -> "Storage size of " + name + " fluid tanks in mB.",
                    name -> "Output rate of " + name + " fluid tanks in mB."
            );
        }

        public static AdvancedTierTranslations create(AdvanceChemicalTankTier tier) {
            return create(tier, "chemical_tank", name -> "Storage size of " + name + " chemical tanks in mB.",
                    name -> "Output rate of " + name + " chemical tanks in mB."
            );
        }

        public static AdvancedTierTranslations create(AdvanceBinTier tier) {
            return create(tier, "bin", name -> "The number of items " + name + " bins can store.", null);
        }

        public static AdvancedTierTranslations create(AdvanceInductionCellTier tier) {
            return create(tier, "induction.cell", name -> "Maximum number of Joules " + name + " induction cells can store.",
                    null);
        }

        public static AdvancedTierTranslations create(AdvanceInductionProviderTier tier) {
            return create(tier, "induction.provider", null,
                    name -> "Maximum number of Joules " + name + " induction providers can output or accept.");
        }
    }
}
