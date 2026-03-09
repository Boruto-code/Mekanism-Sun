package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import mekanism.common.registration.impl.FluidDeferredRegister;
import mekanism.common.registration.impl.FluidDeferredRegister.MekanismFluidType;
import mekanism.common.registration.impl.FluidRegistryObject;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.block.LiquidBlock;
import net.neoforged.neoforge.fluids.BaseFlowingFluid.*;

public class MSFluids {
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MekanismSun.MODID);

    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> HELIUM =
            FLUIDS.register("helium", properties -> properties.tint(0x9CF5FF));
}
