package com.hamburger0abcde.mekanismsun.common.network.to_server;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.common.registries.MSContainerTypes;
import com.hamburger0abcde.mekanismsun.common.tiles.multiblock.matrix.TileEntityAdvanceInductionCasing;
import io.netty.buffer.ByteBuf;
import mekanism.common.MekanismLang;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.attribute.AttributeGui;
import mekanism.common.network.IMekanismPacket;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;

public record PacketMSTileButtonPress(ClickedTileButton buttonClicked, BlockPos pos) implements IMekanismPacket {
    public static final CustomPacketPayload.Type<PacketMSTileButtonPress> TYPE = 
            new CustomPacketPayload.Type<>(MekanismSun.rl("tile_button"));
    public static final StreamCodec<ByteBuf, PacketMSTileButtonPress> STREAM_CODEC = 
            StreamCodec.composite(PacketMSTileButtonPress.ClickedTileButton.STREAM_CODEC, 
                    PacketMSTileButtonPress::buttonClicked, BlockPos.STREAM_CODEC, PacketMSTileButtonPress::pos, 
                    PacketMSTileButtonPress::new);

    public PacketMSTileButtonPress(ClickedTileButton buttonClicked, BlockEntity tile) {
        this(buttonClicked, tile.getBlockPos());
    }

    public PacketMSTileButtonPress(ClickedTileButton buttonClicked, BlockPos pos) {
        this.buttonClicked = buttonClicked;
        this.pos = pos;
    }

    @NotNull
    public CustomPacketPayload.@NotNull Type<PacketMSTileButtonPress> type() {
        return TYPE;
    }

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        TileEntityMekanism tile = WorldUtils.getTileEntity(TileEntityMekanism.class, player.level(), pos);
        MenuProvider provider = buttonClicked.getProvider(tile);
        if (provider != null) {
            player.openMenu(provider, buf -> {
                buf.writeBlockPos(pos);
                buttonClicked.encodeExtraData(buf, tile);
            });
        }
    }

    public PacketMSTileButtonPress.ClickedTileButton buttonClicked() {
        return this.buttonClicked;
    }

    public BlockPos pos() {
        return this.pos;
    }

    public enum ClickedTileButton {
        BACK_BUTTON(tile -> {
            //Special handling to basically reset to the tiles default gui container
            AttributeGui attributeGui = Attribute.get(tile.getBlockHolder(), AttributeGui.class);
            return attributeGui != null ? attributeGui.getProvider(tile, false) : null;
        }),
        TAB_MAIN(tile -> {
            if (tile instanceof TileEntityAdvanceInductionCasing) {
                return MSContainerTypes.ADVANCE_INDUCTION_MATRIX.getProvider(MekanismLang.MATRIX, tile);
            }
            return null;
        }),
        TAB_STATS(tile -> {
            if (tile instanceof TileEntityAdvanceInductionCasing) {
                return MSContainerTypes.ADVANCE_MATRIX_STATS.getProvider(MekanismLang.MATRIX_STATS, tile);
            }
            return null;
        });

        public static final IntFunction<ClickedTileButton> BY_ID =
                ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
        public static final StreamCodec<ByteBuf, PacketMSTileButtonPress.ClickedTileButton> STREAM_CODEC =
                ByteBufCodecs.idMapper(BY_ID, Enum::ordinal);
        private final Function<TileEntityMekanism, MenuProvider> providerFromTile;
        @Nullable
        private final BiConsumer<RegistryFriendlyByteBuf, TileEntityMekanism> extraEncodingData;

        ClickedTileButton(Function<TileEntityMekanism, @Nullable MenuProvider> providerFromTile) {
            this(providerFromTile, null);
        }

        ClickedTileButton(Function<TileEntityMekanism, MenuProvider> providerFromTile,
                          @Nullable BiConsumer<RegistryFriendlyByteBuf, TileEntityMekanism> extraEncodingData) {
            this.providerFromTile = providerFromTile;
            this.extraEncodingData = extraEncodingData;
        }

        public MenuProvider getProvider(TileEntityMekanism tile) {
            return tile == null ? null : providerFromTile.apply(tile);
        }

        private void encodeExtraData(RegistryFriendlyByteBuf buffer, TileEntityMekanism tile) {
            if (extraEncodingData != null) {
                extraEncodingData.accept(buffer, tile);
            }
        }
    }
}
