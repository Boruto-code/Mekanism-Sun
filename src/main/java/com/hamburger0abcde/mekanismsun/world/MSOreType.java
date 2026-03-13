package com.hamburger0abcde.mekanismsun.world;

import com.hamburger0abcde.mekanismsun.MSResources;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import mekanism.api.SerializationConstants;
import mekanism.common.resource.IResource;
import mekanism.common.resource.ore.BaseOreConfig;
import mekanism.common.resource.ore.OreAnchor;
import mekanism.common.world.height.HeightShape;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public enum MSOreType implements StringRepresentable {
    SILVER(MSResources.SILVER, 2, 8, new BaseOreConfig("normal", 4, 0.1F,
            4, HeightShape.TRAPEZOID, OreAnchor.absolute(-48), OreAnchor.absolute(32)))
    ;

    public static Codec<MSOreType> CODEC = StringRepresentable.fromEnum(MSOreType::values);

    private final List<BaseOreConfig> baseConfigs;
    private final IResource resource;
    private final int minExp;
    private final int maxExp;

    MSOreType(IResource resource, BaseOreConfig... configs) {
        this(resource, 0, configs);
    }

    MSOreType(IResource resource, int exp, BaseOreConfig... configs) {
        this(resource, exp, exp, configs);
    }

    MSOreType(IResource resource, int minExp, int maxExp, BaseOreConfig... configs) {
        this.resource = resource;
        this.minExp = minExp;
        this.maxExp = maxExp;
        this.baseConfigs = List.of(configs);
    }

    public static MSOreType get(IResource resource) {
        for (MSOreType ore : values()) {
            if (resource == ore.resource) {
                return ore;
            }
        }
        return null;
    }

    @NotNull
    @Override
    public String getSerializedName() {
        return resource.getRegistrySuffix();
    }

    public record OreVeinType(MSOreType type, int index) {

        public static final Codec<OreVeinType> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                MSOreType.CODEC.fieldOf(SerializationConstants.TYPE).forGetter(config -> config.type),
                Codec.INT.fieldOf(SerializationConstants.INDEX).forGetter(config -> config.index)
        ).apply(builder, OreVeinType::new));

        public OreVeinType {
            if (index < 0 || index >= type.getBaseConfigs().size()) {
                throw new IndexOutOfBoundsException("Vein Type index out of range: " + index);
            }
        }

        public String name() {
            return "ore_" + type.getResource().getRegistrySuffix() + "_" + type.getBaseConfigs().get(index).name();
        }
    }
}
