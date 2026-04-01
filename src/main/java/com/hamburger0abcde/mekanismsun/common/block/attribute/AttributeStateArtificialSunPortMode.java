package com.hamburger0abcde.mekanismsun.common.block.attribute;

import com.hamburger0abcde.mekanismsun.common.MekanismSunLang;
import io.netty.buffer.ByteBuf;
import mekanism.api.IIncrementalEnum;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.text.EnumColor;
import mekanism.api.text.IHasTextComponent;
import mekanism.api.text.ILangEntry;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.attribute.AttributeState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.IntFunction;

public class AttributeStateArtificialSunPortMode implements AttributeState {
    public static final EnumProperty<ArtificialSunPortMode> modeProperty =
            EnumProperty.create("mode", ArtificialSunPortMode.class);

    @Override
    public BlockState copyStateData(BlockState oldState, BlockState newState) {
        if (Attribute.has(newState, AttributeStateArtificialSunPortMode.class)) {
            newState = newState.setValue(modeProperty, oldState.getValue(modeProperty));
        }
        return newState;
    }

    @Override
    public BlockState getDefaultState(@NotNull BlockState state) {
        return state.setValue(modeProperty, ArtificialSunPortMode.INPUT);
    }

    @Override
    public void fillBlockStateContainer(Block block, List<Property<?>> properties) {
        properties.add(modeProperty);
    }

    @NothingNullByDefault
    public enum ArtificialSunPortMode implements StringRepresentable,
            IHasTextComponent.IHasEnumNameTextComponent, IIncrementalEnum<ArtificialSunPortMode> {
        INPUT("input", MekanismSunLang.ARTIFICIAL_SUN_PORT_MODE_INPUT, EnumColor.BRIGHT_GREEN),
        OUTPUT("output", MekanismSunLang.ARTIFICIAL_SUN_PORT_MODE_OUTPUT, EnumColor.RED)
        ;

        public static final IntFunction<ArtificialSunPortMode> BY_ID =
                ByIdMap.continuous(ArtificialSunPortMode::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
        public static final StreamCodec<ByteBuf, ArtificialSunPortMode> STREAM_CODEC =
                ByteBufCodecs.idMapper(BY_ID, ArtificialSunPortMode::ordinal);

        private final String name;
        private final ILangEntry langEntry;
        private final EnumColor color;

        ArtificialSunPortMode(String name, ILangEntry langEntry, EnumColor color) {
            this.name = name;
            this.langEntry = langEntry;
            this.color = color;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public Component getTextComponent() {
            return langEntry.translateColored(color);
        }

        @Override
        public ArtificialSunPortMode byIndex(int index) {
            return BY_ID.apply(index);
        }
    }
}
