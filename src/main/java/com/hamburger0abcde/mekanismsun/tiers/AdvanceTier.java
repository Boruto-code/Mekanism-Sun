package com.hamburger0abcde.mekanismsun.tiers;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import mekanism.api.SupportsColorMap;
import mekanism.api.math.MathUtils;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.FastColor;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.IntFunction;

public enum AdvanceTier implements StringRepresentable, SupportsColorMap {
    SUPERNOVA("Supernova", new int[]{214, 163, 82}, MapColor.GOLD)
    ;

    public static final IntFunction<AdvanceTier> BY_ID = ByIdMap.continuous(AdvanceTier::ordinal, values(),
            ByIdMap.OutOfBoundsStrategy.WRAP);
    public static final StreamCodec<ByteBuf, AdvanceTier> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, AdvanceTier::ordinal);

    private static final AdvanceTier[] TIERS = values();

    private final String name;
    @Getter
    private final MapColor mapColor;
    private TextColor textColor;
    private int[] rgbCode;
    private int argb;

    AdvanceTier(String name, int[] rgbCode, MapColor mapColor) {
        this.name = name;
        this.mapColor = mapColor;
        setColorFromAtlas(rgbCode);
    }

    public String getSimpleName() {
        return name;
    }

    public String getLowerName() {
        return getSimpleName().toLowerCase(Locale.ROOT);
    }

    @Override
    public int getPackedColor() {
        return argb;
    }

    @Override
    public int[] getRgbCode() {
        return rgbCode;
    }

    @Override
    public void setColorFromAtlas(int[] color) {
        this.rgbCode = color;
        this.argb = FastColor.ARGB32.color(rgbCode[0], rgbCode[1], rgbCode[2]);
        this.textColor = TextColor.fromRgb(argb);
    }

    public TextColor getColor() {
        return this.textColor;
    }

    @NotNull
    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public static AdvanceTier byIndexStatic(int index) {
        return MathUtils.getByIndexMod(TIERS, index);
    }
}
