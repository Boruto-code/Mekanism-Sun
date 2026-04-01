package com.hamburger0abcde.mekanismsun.common.tiles.transmitter;

import com.hamburger0abcde.mekanismsun.common.content.network.transmitter.AdvanceLogisticalTransporter;
import com.hamburger0abcde.mekanismsun.common.registries.MSBlocks;
import com.hamburger0abcde.mekanismsun.common.tiers.AdvancedTier;
import mekanism.client.model.data.TransmitterModelData;
import mekanism.common.block.states.BlockStateHelper;
import mekanism.common.block.states.TransmitterType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileEntityAdvanceLogisticalTransporter extends TileEntityAdvanceLogisticalTransporterBase {
    public TileEntityAdvanceLogisticalTransporter(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
    }

    @Override
    protected AdvanceLogisticalTransporter createTransmitter(Holder<Block> blockProvider) {
        return new AdvanceLogisticalTransporter(blockProvider, this);
    }

    @Override
    public AdvanceLogisticalTransporter getTransmitter() {
        return (AdvanceLogisticalTransporter) super.getTransmitter();
    }

    @Override
    public TransmitterType getTransmitterType() {
        return TransmitterType.LOGISTICAL_TRANSPORTER;
    }

    @Override
    protected void updateModelData(TransmitterModelData modelData) {
        super.updateModelData(modelData);
        modelData.setHasColor(getTransmitter().getColor() != null);
    }

    @NotNull
    @Override
    protected BlockState upgradeResult(@NotNull BlockState current, @NotNull AdvancedTier tier) {
        return BlockStateHelper.copyStateData(current, switch (tier) {
            case SUPERNOVA -> MSBlocks.SUPERNOVA_LOGISTICAL_TRANSPORTER;
        });
    }
}
