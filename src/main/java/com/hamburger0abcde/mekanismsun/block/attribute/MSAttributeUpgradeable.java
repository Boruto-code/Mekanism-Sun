package com.hamburger0abcde.mekanismsun.block.attribute;

import com.hamburger0abcde.mekanismsun.tiers.AdvanceTier;
import mekanism.common.block.states.BlockStateHelper;
import mekanism.common.registration.impl.BlockRegistryObject;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class MSAttributeUpgradeable implements MSAttribute {
    private final Supplier<BlockRegistryObject<?, ?>> upgradeBlock;

    public MSAttributeUpgradeable(Supplier<BlockRegistryObject<?, ?>> upgradeBlock) {
        this.upgradeBlock = upgradeBlock;
    }

    @NotNull
    public BlockState upgradeResult(@NotNull BlockState current, @NotNull AdvanceTier tier) {
        return BlockStateHelper.copyStateData(current, upgradeBlock.get());
    }
}
