package com.hamburger0abcde.mekanismsun.common.item.block;

import com.hamburger0abcde.mekanismsun.common.block.attribute.MSAttribute;
import com.hamburger0abcde.mekanismsun.common.tiers.storage.AdvanceInductionCellTier;
import com.hamburger0abcde.mekanismsun.common.tiles.multiblock.TileEntityAdvanceInductionCell;
import mekanism.api.text.EnumColor;
import mekanism.common.MekanismLang;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.util.StorageUtils;
import mekanism.common.util.text.EnergyDisplay;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class MSItemBlockInductionCell extends MSItemBlockTooltip<BlockTile<TileEntityAdvanceInductionCell,
        BlockTypeTile<TileEntityAdvanceInductionCell>>> {

    public MSItemBlockInductionCell(BlockTile<TileEntityAdvanceInductionCell, BlockTypeTile<TileEntityAdvanceInductionCell>> block,
                                    Item.Properties properties) {
        super(block, properties);
    }

    @NotNull
    @Override
    public AdvanceInductionCellTier getAdvancedTier() {
        return Objects.requireNonNull(MSAttribute.getAdvancedTier(getBlock(), AdvanceInductionCellTier.class));
    }

    @Override
    protected void addStats(@NotNull ItemStack stack, @Nullable TooltipContext context, @NotNull List<Component> tooltip,
                            @NotNull TooltipFlag flag) {
        AdvanceInductionCellTier tier = getAdvancedTier();
        tooltip.add(MekanismLang.CAPACITY.translateColored(tier.getAdvanceTier().getColor(), EnumColor.GRAY,
                EnergyDisplay.of(tier.getMaxEnergy())));
        StorageUtils.addStoredEnergy(stack, tooltip, false);
    }

    @Override
    protected boolean exposesEnergyCap() {
        return false;
    }
}
