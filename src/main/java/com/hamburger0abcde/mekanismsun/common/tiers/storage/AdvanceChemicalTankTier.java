package com.hamburger0abcde.mekanismsun.common.tiers.storage;

import com.hamburger0abcde.mekanismsun.common.tiers.AdvancedTier;
import com.hamburger0abcde.mekanismsun.common.tiers.IAdvancedTier;
import lombok.Getter;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.common.config.value.CachedLongValue;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Locale;

@NothingNullByDefault
public enum AdvanceChemicalTankTier implements IAdvancedTier, StringRepresentable {
    SUPERNOVA(AdvancedTier.SUPERNOVA, 65_536_000, 32_768_000)
    ;

    @Getter
    private final long advanceStorage;
    @Getter
    private final long advanceOutput;
    private final AdvancedTier advancedTier;
    @Nullable
    private CachedLongValue storageReference;
    @Nullable
    private CachedLongValue outputReference;

    AdvanceChemicalTankTier(AdvancedTier tier, long storage, long output) {
        advanceStorage = storage;
        advanceOutput = output;
        advancedTier = tier;
    }

    @Override
    public AdvancedTier getAdvanceTier() {
        return advancedTier;
    }

    @Override
    @NotNull
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public long getStorage() {
        return storageReference == null ? getAdvanceStorage() : storageReference.getOrDefault();
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
