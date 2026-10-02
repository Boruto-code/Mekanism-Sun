package com.hamburger0abcde.mekanismsun.common.multiblock.fission;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.common.tags.MSTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public final class MSFissionCoolants {
    private static final FissionFluidCoolant DEFAULT = new FissionFluidCoolant(0.5D, 1000.0D);

    private static final Map<ResourceLocation, FissionFluidCoolant> COOLANTS = Map.of(
            ResourceLocation.fromNamespaceAndPath(MekanismSun.MODID, "superfluid_helium"),
            new FissionFluidCoolant(2.5D, 3030.0D)
    );

    @Nullable
    public static FissionFluidCoolant get(Fluid fluid) {
        ResourceLocation id = BuiltInRegistries.FLUID.getKey(fluid);

        FissionFluidCoolant coolant = COOLANTS.get(id);
        if (coolant != null) {
            return coolant;
        }

        if (fluid.builtInRegistryHolder().is(MSTags.Fluids.FISSION_COOLANTS)) {
            return DEFAULT;
        }

        return null;
    }

    public static boolean isCoolant(Fluid fluid) {
        return get(fluid) != null;
    }
}
