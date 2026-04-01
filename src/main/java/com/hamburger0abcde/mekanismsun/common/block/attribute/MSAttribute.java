package com.hamburger0abcde.mekanismsun.common.block.attribute;

import com.hamburger0abcde.mekanismsun.common.tiers.AdvancedTier;
import com.hamburger0abcde.mekanismsun.common.tiers.IAdvancedTier;
import mekanism.common.block.attribute.Attribute;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

public interface MSAttribute extends Attribute {
    @Nullable
    static <TIER extends IAdvancedTier> TIER getAdvancedTier(Holder<Block> block, Class<TIER> tierClass) {
        return getAdvancedTier(block.value(), tierClass);
    }

    @Nullable
    static <TIER extends IAdvancedTier> TIER getAdvancedTier(Block block, Class<TIER> tierClass) {
        MSAttributeTier<?> attribute = Attribute.get(block, MSAttributeTier.class);
        return attribute == null ? null : tierClass.cast(attribute.tier());
    }

    @Nullable
    static AdvancedTier getAdvancedTier(Block block) {
        MSAttributeTier<?> attribute = Attribute.get(block, MSAttributeTier.class);
        return attribute == null ? null : attribute.tier().getAdvanceTier();
    }
}
