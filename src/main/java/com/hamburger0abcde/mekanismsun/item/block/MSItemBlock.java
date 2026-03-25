package com.hamburger0abcde.mekanismsun.item.block;

import com.hamburger0abcde.mekanismsun.tiers.IAdvancedTier;
import mekanism.api.text.TextComponentUtil;
import mekanism.common.block.interfaces.IColoredBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class MSItemBlock<BLOCK extends Block> extends BlockItem {
    public MSItemBlock(Block block, Properties properties) {
        super(block, properties);
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public BLOCK getBlock() {
        return (BLOCK) super.getBlock();
    }

    public IAdvancedTier getAdvancedTier() {
        return null;
    }

    @NotNull
    @Override
    public Component getName(@NotNull ItemStack stack) {
        if (getBlock() instanceof IColoredBlock coloredBlock) {
            return TextComponentUtil.build(coloredBlock.getColor(), super.getName(stack));
        }
        IAdvancedTier tier = getAdvancedTier();
        if (tier == null) {
            return super.getName(stack);
        }
        return TextComponentUtil.build(tier.getAdvanceTier().getColor(), super.getName(stack));
    }
}
