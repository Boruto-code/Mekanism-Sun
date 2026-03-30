package com.hamburger0abcde.mekanismsun.item;

import com.hamburger0abcde.mekanismsun.capabilities.AdvanceCapabilities;
import com.hamburger0abcde.mekanismsun.tiers.AdvanceAlloyTier;
import com.hamburger0abcde.mekanismsun.utils.IAdvanceAlloyInteraction;
import mekanism.common.config.MekanismConfig;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MSItemAlloy extends Item {
    private final AdvanceAlloyTier tier;

    public MSItemAlloy(AdvanceAlloyTier tier, Properties properties) {
        super(properties);
        this.tier = tier;
    }

    @NotNull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null && MekanismConfig.general.transmitterAlloyUpgrade.get()) {
            Level world = context.getLevel();
            BlockPos pos = context.getClickedPos();
            IAdvanceAlloyInteraction alloyInteraction = WorldUtils.getCapability(world, AdvanceCapabilities.ADVANCE_ALLOY_INTERACTION,
                    pos, context.getClickedFace());
            if (alloyInteraction != null) {
                if (!world.isClientSide) {
                    alloyInteraction.onAdvanceAlloyInteraction(player, context.getItemInHand(), tier);
                }
                return InteractionResult.sidedSuccess(world.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

    public AdvanceAlloyTier getTier() {
        return tier;
    }
}
