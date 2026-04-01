package com.hamburger0abcde.mekanismsun.common.capabilities;

import com.hamburger0abcde.mekanismsun.common.utils.IAdvanceAlloyInteraction;
import mekanism.common.Mekanism;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

public class AdvanceCapabilities {
    public static final BlockCapability<IAdvanceAlloyInteraction, @Nullable Direction> ADVANCE_ALLOY_INTERACTION =
            BlockCapability.createSided(Mekanism.rl("advance_alloy_interaction"), IAdvanceAlloyInteraction.class);
}
