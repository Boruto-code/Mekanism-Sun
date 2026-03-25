package com.hamburger0abcde.mekanismsun.block.attribute;

import com.hamburger0abcde.mekanismsun.tiers.IAdvancedTier;
import mekanism.common.MekanismLang;
import mekanism.common.content.blocktype.BlockType;

import java.util.HashMap;
import java.util.Map;

public record MSAttributeTier<TIER extends IAdvancedTier>(TIER tier) implements MSAttribute {
    private static final Map<IAdvancedTier, BlockType> typeCache = new HashMap<>();

    public MSAttributeTier(TIER tier) {
        this.tier = tier;
    }

    public static <T extends IAdvancedTier> BlockType getPassthroughType(T tier) {
        return typeCache.computeIfAbsent(tier, t -> BlockType.BlockTypeBuilder
                .createBlock(MekanismLang.EMPTY).with(new MSAttribute[]{new MSAttributeTier<>(t)}).build());
    }
}
