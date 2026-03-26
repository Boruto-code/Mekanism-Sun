package com.hamburger0abcde.mekanismsun.block.attribute;

import com.hamburger0abcde.mekanismsun.tiers.AdvanceTier;
import com.hamburger0abcde.mekanismsun.tiers.IAdvancedTier;
import mekanism.common.block.attribute.Attribute;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

public interface MSAttribute extends Attribute {
    @Nullable
    static <TIER extends IAdvancedTier> TIER getAdvanceTier(Holder<Block> block, Class<TIER> tierClass) {
        return getAdvanceTier(block.value(), tierClass);
    }

    @Nullable
    static <TIER extends IAdvancedTier> TIER getAdvanceTier(Block block, Class<TIER> tierClass) {
        MSAttributeTier<?> attribute = Attribute.get(block, MSAttributeTier.class);
        return attribute == null ? null : tierClass.cast(attribute.tier());
    }

    @Nullable
    static AdvanceTier getAdvanceTier(Block block) {
        MSAttributeTier<?> attribute = Attribute.get(block, MSAttributeTier.class);
        return attribute == null ? null : attribute.tier().getAdvanceTier();
    }
}
