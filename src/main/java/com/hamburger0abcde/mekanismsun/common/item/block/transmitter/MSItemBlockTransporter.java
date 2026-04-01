package com.hamburger0abcde.mekanismsun.common.item.block.transmitter;

import com.hamburger0abcde.mekanismsun.common.tiers.TierColor;
import com.hamburger0abcde.mekanismsun.common.tiles.transmitter.TileEntityAdvanceLogisticalTransporterBase;
import mekanism.api.text.EnumColor;
import mekanism.api.text.ILangEntry;
import mekanism.common.MekanismLang;
import mekanism.common.block.transmitter.BlockLargeTransmitter;
import mekanism.common.item.block.ItemBlockTooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MSItemBlockTransporter<TILE extends TileEntityAdvanceLogisticalTransporterBase>
        extends ItemBlockTooltip<BlockLargeTransmitter<TILE>> {

    @Nullable
    private final ILangEntry extraDetails;

    public MSItemBlockTransporter(BlockLargeTransmitter<TILE> block, Properties properties) {
        this(block, properties, null);
    }

    public MSItemBlockTransporter(BlockLargeTransmitter<TILE> block, Properties properties, @Nullable ILangEntry extraDetails) {
        super(block,true, properties);
        this.extraDetails = extraDetails;
    }

    @Override
    protected void addDetails(@NotNull ItemStack stack, @Nullable TooltipContext context, @NotNull List<Component> tooltip,
                              @NotNull TooltipFlag flag) {
        super.addDetails(stack, context, tooltip, flag);
        tooltip.add(MekanismLang.CAPABLE_OF_TRANSFERRING.translateColored(EnumColor.DARK_GRAY));
        tooltip.add(MekanismLang.ITEMS.translateColored(EnumColor.PURPLE, MekanismLang.UNIVERSAL));
        tooltip.add(MekanismLang.BLOCKS.translateColored(EnumColor.PURPLE, MekanismLang.UNIVERSAL));
        if (extraDetails != null) {
            tooltip.add(extraDetails.translateColored(EnumColor.DARK_RED));
        }
    }
}
