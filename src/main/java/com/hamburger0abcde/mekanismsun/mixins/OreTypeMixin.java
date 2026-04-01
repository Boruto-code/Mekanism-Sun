package com.hamburger0abcde.mekanismsun.mixins;

import com.hamburger0abcde.mekanismsun.common.MSResources;
import com.hamburger0abcde.mekanismsun.common.world.MSOreType;
import com.mojang.serialization.Codec;
import mekanism.common.resource.IResource;
import mekanism.common.resource.ore.BaseOreConfig;
import mekanism.common.resource.ore.OreAnchor;
import mekanism.common.resource.ore.OreType;
import mekanism.common.world.height.HeightShape;
import net.minecraft.util.StringRepresentable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(value = OreType.class, remap = false)
public class OreTypeMixin {
    @Shadow
    @Final
    @Mutable
    private static OreType[] $VALUES;

    @Mutable
    @Shadow
    public static Codec<OreType> CODEC;

    @Invoker("<init>")
    public static OreType oreType$initInvoker(String internalName, int internalId, IResource resource, BaseOreConfig... configs) {
        throw new AssertionError();
    }

    @Invoker("<init>")
    public static OreType oreType$initInvoker(String internalName, int internalId, IResource resource, int exp, BaseOreConfig... configs) {
        throw new AssertionError();
    }

    @Invoker("<init>")
    public static OreType oreType$initInvoker(String internalName, int internalId, IResource resource, int minExp, int maxExp,
                                              BaseOreConfig... configs) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>",at = @At("TAIL"))
    private static void oreTypeClinit(CallbackInfo ci) {
        MSOreType.SILVER = mekanismsun$addVariant("SILVER", MSResources.SILVER,
                new BaseOreConfig("normal", 8, 0,
                        4, HeightShape.TRAPEZOID, OreAnchor.absolute(-48), OreAnchor.absolute(32)));

        mekanismsun$reinitializeByIdMap();
    }

    @Unique
    private static OreType mekanismsun$addVariant(String internalName, IResource resource, BaseOreConfig... configs) {
        ArrayList<OreType> variants = new ArrayList<>(Arrays.asList($VALUES));
        OreType upgrade = oreType$initInvoker(internalName,
                variants.getLast().ordinal() + 1,
                resource,
                configs);
        variants.add(upgrade);
        OreTypeMixin.$VALUES = variants.toArray(new OreType[0]);
        return upgrade;
    }

    @Unique
    private static void mekanismsun$reinitializeByIdMap() {
        CODEC = StringRepresentable.fromEnum(OreType::values);
    }
}
