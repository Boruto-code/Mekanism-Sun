package com.hamburger0abcde.mekanismsun.common.item.block;

import com.hamburger0abcde.mekanismsun.common.block.attribute.MSAttribute;
import com.hamburger0abcde.mekanismsun.common.tiers.storage.AdvanceInductionProviderTier;
import com.hamburger0abcde.mekanismsun.common.tiles.multiblock.matrix.TileEntityAdvanceInductionProvider;
import mekanism.api.text.EnumColor;
import mekanism.common.MekanismLang;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.util.text.EnergyDisplay;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class MSItemBlockInductionProvider extends MSItemBlockTooltip<BlockTile<TileEntityAdvanceInductionProvider,
        BlockTypeTile<TileEntityAdvanceInductionProvider>>> {

    public MSItemBlockInductionProvider(BlockTile<TileEntityAdvanceInductionProvider,
            BlockTypeTile<TileEntityAdvanceInductionProvider>> block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    @NotNull
    public AdvanceInductionProviderTier getAdvancedTier() {
        return Objects.requireNonNull(MSAttribute.getAdvancedTier(getBlock(), AdvanceInductionProviderTier.class));
    }

    @Override
    protected void addStats(@NotNull ItemStack stack, @Nullable TooltipContext context, @NotNull List<Component> tooltip,
                            @NotNull TooltipFlag flag) {
        AdvanceInductionProviderTier tier = getAdvancedTier();
        tooltip.add(MekanismLang.INDUCTION_PORT_OUTPUT_RATE.translateColored(tier.getAdvanceTier().getColor(), EnumColor.GRAY,
                EnergyDisplay.of(tier.getOutput())));
    }
}
