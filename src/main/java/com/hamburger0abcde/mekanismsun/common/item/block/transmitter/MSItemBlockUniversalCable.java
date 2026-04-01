package com.hamburger0abcde.mekanismsun.common.item.block.transmitter;

import com.hamburger0abcde.mekanismsun.common.tiers.TierColor;
import com.hamburger0abcde.mekanismsun.common.tiers.transmitter.AdvanceCableTier;
import com.hamburger0abcde.mekanismsun.common.tiles.transmitter.TileEntityAdvanceUniversalCable;
import mekanism.api.text.EnumColor;
import mekanism.common.MekanismLang;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.transmitter.BlockSmallTransmitter;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.tier.CableTier;
import mekanism.common.util.text.EnergyDisplay;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class MSItemBlockUniversalCable extends ItemBlockTooltip<BlockSmallTransmitter<TileEntityAdvanceUniversalCable>> {
    private final int color;

    public MSItemBlockUniversalCable(BlockSmallTransmitter<TileEntityAdvanceUniversalCable> block, Properties properties) {
        super(block, true, properties);
        this.color = TierColor.cableTierToColor(getTier());
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
    public CableTier getTier() {
        return Objects.requireNonNull(Attribute.getTier(getBlock(), CableTier.class));
    }

    @Override
    protected void addDetails(@NotNull ItemStack stack, @Nullable TooltipContext context, @NotNull List<Component> tooltip,
                              @NotNull TooltipFlag flag) {
        super.addDetails(stack, context, tooltip, flag);
        tooltip.add(MekanismLang.CAPABLE_OF_TRANSFERRING.translateColored(EnumColor.DARK_GRAY));
        tooltip.add(MekanismLang.GENERIC_TRANSFER.translateColored(EnumColor.PURPLE, MekanismLang.ENERGY_FORGE_SHORT,
                MekanismLang.FORGE));
        tooltip.add(MekanismLang.GENERIC_TRANSFER.translateColored(EnumColor.PURPLE, MekanismLang.ENERGY_JOULES_PLURAL,
                MekanismLang.MEKANISM));
    }

    @Override
    protected void addStats(@NotNull ItemStack stack, @Nullable TooltipContext context, @NotNull List<Component> tooltip,
                            @NotNull TooltipFlag flag) {
        super.addStats(stack, context, tooltip, flag);
        CableTier tier = getTier();
        tooltip.add(MekanismLang.CAPACITY_PER_TICK.translateColored(EnumColor.INDIGO, EnumColor.GRAY,
                EnergyDisplay.of(AdvanceCableTier.getCapacityAsLong(tier))));
    }
}
