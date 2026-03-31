package com.hamburger0abcde.mekanismsun.item.block.transmitter;

import com.hamburger0abcde.mekanismsun.tiers.transmitter.AdvanceTubeTier;
import com.hamburger0abcde.mekanismsun.tiles.transmitter.TileEntityAdvancePressurizedTube;
import mekanism.api.text.EnumColor;
import mekanism.common.MekanismLang;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.transmitter.BlockSmallTransmitter;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.tier.TubeTier;
import mekanism.common.util.text.TextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class MSItemBlockPressurizedTube extends ItemBlockTooltip<BlockSmallTransmitter<TileEntityAdvancePressurizedTube>> {
    private static final int COLOR = 0xD6A352;

    public MSItemBlockPressurizedTube(BlockSmallTransmitter<TileEntityAdvancePressurizedTube> block, Properties properties) {
        super(block, true, properties);
    }

    @NotNull
    @Override
    public Component getName(@NotNull ItemStack stack) {
        Component name = super.getName(stack);
        Style coloredStyle = name.getStyle().withColor(TextColor.fromRgb(COLOR));
        return name.copy().setStyle(coloredStyle);
    }

    @NotNull
    @Override
    public TubeTier getTier() {
        return Objects.requireNonNull(Attribute.getTier(getBlock(), TubeTier.class));
    }

    @Override
    protected void addDetails(@NotNull ItemStack stack, @Nullable TooltipContext context, @NotNull List<Component> tooltip,
                              @NotNull TooltipFlag flag) {
        super.addDetails(stack, context, tooltip, flag);
        tooltip.add(MekanismLang.CAPABLE_OF_TRANSFERRING.translateColored(EnumColor.DARK_GRAY));
        tooltip.add(MekanismLang.CHEMICAL.translateColored(EnumColor.PURPLE, MekanismLang.MEKANISM));
    }

    @Override
    protected void addStats(@NotNull ItemStack stack, @Nullable TooltipContext context, @NotNull List<Component> tooltip,
                            @NotNull TooltipFlag flag) {
        super.addStats(stack, context, tooltip, flag);
        TubeTier tier = getTier();
        tooltip.add(MekanismLang.CAPACITY_MB_PER_TICK.translateColored(EnumColor.INDIGO, EnumColor.GRAY,
                TextUtils.format(AdvanceTubeTier.getTubeCapacity(tier))));
        tooltip.add(MekanismLang.PUMP_RATE_MB.translateColored(EnumColor.INDIGO, EnumColor.GRAY,
                TextUtils.format(AdvanceTubeTier.getTubePullAmount(tier))));
    }
}
