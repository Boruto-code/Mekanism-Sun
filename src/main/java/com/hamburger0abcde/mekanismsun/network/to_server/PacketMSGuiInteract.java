package com.hamburger0abcde.mekanismsun.network.to_server;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunCasing;
import io.netty.buffer.ByteBuf;
import mekanism.api.functions.TriConsumer;
import mekanism.common.network.IMekanismPacket;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

public record PacketMSGuiInteract(MSGuiInteraction interaction, BlockPos tilePosition, double extra) implements IMekanismPacket {
    public static final CustomPacketPayload.Type<PacketMSGuiInteract> TYPE =
            new CustomPacketPayload.Type<>(MekanismSun.rl("gui_interact"));
    public static final StreamCodec<ByteBuf, PacketMSGuiInteract> STREAM_CODEC = StreamCodec.composite(
            MSGuiInteraction.STREAM_CODEC, PacketMSGuiInteract::interaction,
            BlockPos.STREAM_CODEC, PacketMSGuiInteract::tilePosition,
            ByteBufCodecs.DOUBLE, PacketMSGuiInteract::extra,
            PacketMSGuiInteract::new
    );

    public PacketMSGuiInteract(MSGuiInteraction interaction, BlockEntity tile) {
        this(interaction, tile.getBlockPos());
    }

    public PacketMSGuiInteract(MSGuiInteraction interaction, BlockEntity tile, double extra) {
        this(interaction, tile.getBlockPos(), extra);
    }

    public PacketMSGuiInteract(MSGuiInteraction interaction, BlockPos tilePosition) {
        this(interaction, tilePosition, 0);
    }

    @NotNull
    @Override
    public CustomPacketPayload.Type<PacketMSGuiInteract> type() {
        return TYPE;
    }

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        TileEntityMekanism tile = WorldUtils.getTileEntity(TileEntityMekanism.class, player.level(), tilePosition);
        if (tile != null) {
            interaction.consume(tile, player, extra);
        }
    }

    public enum MSGuiInteraction {
        INJECTION_RATE((tile, player, extra) -> {
            if (tile instanceof TileEntityArtificialSunCasing artificialSunCasing) {
                artificialSunCasing.setRateLimitFromPacket(extra);
            }
        })
        ;

        public static final IntFunction<MSGuiInteraction> BY_ID = ByIdMap
                .continuous(MSGuiInteraction::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
        public static final StreamCodec<ByteBuf, MSGuiInteraction> STREAM_CODEC =
                ByteBufCodecs.idMapper(BY_ID, MSGuiInteraction::ordinal);

        private final TriConsumer<TileEntityMekanism, Player, Double> consumerForTile;

        MSGuiInteraction(TriConsumer<TileEntityMekanism, Player, Double> consumerForTile) {
            this.consumerForTile = consumerForTile;
        }

        public void consume(TileEntityMekanism tile, Player player, double extra) {
            consumerForTile.accept(tile, player, extra);
        }
    }
}
