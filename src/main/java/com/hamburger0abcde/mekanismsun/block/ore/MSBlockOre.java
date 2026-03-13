package com.hamburger0abcde.mekanismsun.block.ore;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.world.MSOreType;
import mekanism.api.text.ILangEntry;
import mekanism.common.block.interfaces.IHasDescription;
import mekanism.common.block.states.BlockStateHelper;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MSBlockOre extends Block implements IHasDescription {
    private final MSOreType ore;
    private String descriptionTranslationKey;

    public MSBlockOre(MSOreType ore) {
        this(ore, BlockStateHelper.applyLightLevelAdjustments(BlockBehaviour.Properties.of()
                .strength(3, 3).requiresCorrectToolForDrops()
                .mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)));
    }

    public MSBlockOre(MSOreType ore, BlockBehaviour.Properties properties) {
        super(properties);
        this.ore = ore;
    }

    @NotNull
    public String getDescriptionTranslationKey() {
        if (descriptionTranslationKey == null) {
            descriptionTranslationKey = Util.makeDescriptionId("description",
                    MekanismSun.rl(ore.getResource().getRegistrySuffix() + "_ore"));
        }
        return descriptionTranslationKey;
    }

    @NotNull
    @Override
    public ILangEntry getDescription() {
        return this::getDescriptionTranslationKey;
    }

    @Override
    public int getExpDrop(@NotNull BlockState state, @NotNull LevelAccessor level, @NotNull BlockPos pos,
                          @Nullable BlockEntity blockEntity, @Nullable Entity breaker, @NotNull ItemStack tool) {
        return Mth.nextInt(level.getRandom(), ore.getMinExp(), ore.getMaxExp());
    }
}
