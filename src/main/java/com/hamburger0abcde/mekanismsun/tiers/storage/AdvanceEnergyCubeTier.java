package com.hamburger0abcde.mekanismsun.tiers.storage;

import com.hamburger0abcde.mekanismsun.tiers.AdvanceTier;
import com.hamburger0abcde.mekanismsun.tiers.IAdvancedTier;
import lombok.Getter;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.common.config.value.CachedLongValue;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

@NothingNullByDefault
public enum AdvanceEnergyCubeTier implements IAdvancedTier, StringRepresentable {
    SUPERNOVA(AdvanceTier.SUPERNOVA, 1_024_000_000, 1_024_000)
    ;

    @Getter
    private final long advanceMaxEnergy;
    @Getter
    private final long advanceOutput;
    private final AdvanceTier advancedTier;
    @Nullable
    private CachedLongValue storageReference;
    @Nullable
    private CachedLongValue outputReference;

    AdvanceEnergyCubeTier(AdvanceTier tier, long max, long out) {
        advanceMaxEnergy = max;
        advanceOutput = out;
        advancedTier = tier;
    }

    @Override
    public AdvanceTier getAdvanceTier() {
        return advancedTier;
    }

    @Override
    @NotNull
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public long getMaxEnergy() {
        return storageReference == null ? getAdvanceMaxEnergy() : storageReference.getOrDefault();
    }

    public long getOutput() {
        return outputReference == null ? getAdvanceOutput() : outputReference.getOrDefault();
    }

    /**
     * ONLY CALL THIS FROM TierConfig. It is used to give the EnergyCubeTier a reference to the actual config value object
     */
    public void setConfigReference(CachedLongValue storageReference, CachedLongValue outputReference) {
        this.storageReference = storageReference;
        this.outputReference = outputReference;
    }
}
