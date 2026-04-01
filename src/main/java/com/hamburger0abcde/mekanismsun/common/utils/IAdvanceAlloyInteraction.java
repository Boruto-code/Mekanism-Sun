package com.hamburger0abcde.mekanismsun.common.utils;

import com.hamburger0abcde.mekanismsun.common.tiers.IAdvancedTier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IAdvanceAlloyInteraction {
    <TIER extends IAdvancedTier> void onAdvanceAlloyInteraction(Player player, ItemStack stack, @NotNull TIER tier);
}
