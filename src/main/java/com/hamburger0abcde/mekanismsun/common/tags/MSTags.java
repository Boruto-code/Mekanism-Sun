package com.hamburger0abcde.mekanismsun.common.tags;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class MSTags {
    public static class Fluids {
        public static final TagKey<Fluid> FISSION_COOLANTS = TagKey.create(
                Registries.FLUID,
                ResourceLocation.fromNamespaceAndPath(
                        MekanismSun.MODID,
                        "fission_coolants"
                )
        );
    }
}
