package com.hamburger0abcde.mekanismsun.mixins;

import com.hamburger0abcde.mekanismsun.common.registries.MSBlocks;
import com.hamburger0abcde.mekanismsun.common.world.MSOreType;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.resource.ore.OreBlockType;
import mekanism.common.resource.ore.OreType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MekanismBlocks.class, remap = false)
public class MekanismBlocksMixin {
    @Inject(method = "registerOre", at = @At(value = "HEAD"), cancellable = true)
    private static void mixinRegisterOre(OreType ore, CallbackInfoReturnable<OreBlockType> callback) {
        if (ore == MSOreType.SILVER) {
            callback.setReturnValue(MSBlocks.ORES.get(ore));
            callback.cancel();
        }
    }
}
