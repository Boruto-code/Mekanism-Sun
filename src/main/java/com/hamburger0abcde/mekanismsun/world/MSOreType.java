package com.hamburger0abcde.mekanismsun.world;

import com.hamburger0abcde.mekanismsun.MSResources;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import mekanism.api.SerializationConstants;
import mekanism.common.resource.IResource;
import mekanism.common.resource.ore.BaseOreConfig;
import mekanism.common.resource.ore.OreAnchor;
import mekanism.common.resource.ore.OreType;
import mekanism.common.world.height.HeightShape;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public class MSOreType {
    public static OreType SILVER;
}
