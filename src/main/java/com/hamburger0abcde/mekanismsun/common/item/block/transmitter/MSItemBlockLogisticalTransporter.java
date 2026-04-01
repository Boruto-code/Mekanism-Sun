package com.hamburger0abcde.mekanismsun.common.item.block.transmitter;

import com.hamburger0abcde.mekanismsun.common.tiers.TierColor;
import com.hamburger0abcde.mekanismsun.common.tiers.transmitter.AdvanceTransporterTier;
import com.hamburger0abcde.mekanismsun.common.tiles.transmitter.TileEntityAdvanceLogisticalTransporter;
import mekanism.api.text.EnumColor;
import mekanism.common.MekanismLang;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.transmitter.BlockLargeTransmitter;
import mekanism.common.tier.TransporterTier;
import mekanism.common.util.MekanismUtils;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class MSItemBlockLogisticalTransporter extends MSItemBlockTransporter<TileEntityAdvanceLogisticalTransporter> {
    private final int color;

    public MSItemBlockLogisticalTransporter(BlockLargeTransmitter<TileEntityAdvanceLogisticalTransporter> block,
                                            Properties properties) {
        super(block, properties);
        this.color = TierColor.transporterTierToColor(getTier());
    }

    @NotNull
    @Override
    public Component getName(@NotNull ItemStack stack) {
        Component name = super.getName(stack);
        Style coloredStyle = name.getStyle().withColor(TextColor.fromRgb(color));
        return name.copy().setStyle(coloredStyle);
    }

    @NotNull
    @Override
    public TransporterTier getTier() {
        return Objects.requireNonNull(Attribute.getTier(getBlock(), TransporterTier.class));
    }

    @Override
    protected void addStats(@NotNull ItemStack stack, @Nullable TooltipContext context, @NotNull List<Component> tooltip,
                            @NotNull TooltipFlag flag) {
        super.addStats(stack, context, tooltip, flag);
        TransporterTier tier = getTier();
        float tickRate = Math.max(context.tickRate(), TickRateManager.MIN_TICKRATE);
        float speed = AdvanceTransporterTier.getSpeed(tier) / (5 * SharedConstants.TICKS_PER_SECOND / tickRate);
        float pull = AdvanceTransporterTier.getPullAmount(tier) * tickRate / MekanismUtils.TICKS_PER_HALF_SECOND;
        tooltip.add(MekanismLang.SPEED.translateColored(EnumColor.INDIGO, EnumColor.GRAY, speed));
        tooltip.add(MekanismLang.PUMP_RATE.translateColored(EnumColor.INDIGO, EnumColor.GRAY, pull));
    }
}
