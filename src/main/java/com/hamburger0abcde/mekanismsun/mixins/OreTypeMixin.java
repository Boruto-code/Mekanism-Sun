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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Adds Mekanism: Sun's silver OreType only when no other addon has already
 * supplied the logical "silver" OreType.
 *
 * Mekanism: MoreMachine 1.21.1 also injects SILVER into OreType at the tail of
 * OreType.<clinit>. This mixin intentionally uses a low priority so that the
 * normal/default-priority addon injections run first. Sun can then reuse their
 * silver OreType instead of appending a second enum value with the same
 * registry suffix.
 *
 * The check is based on IResource#getRegistrySuffix(), not an addon mod id or
 * enum constant name, so the same protection also applies to other addons that
 * provide a Mekanism OreType representing "silver".
 */
@Mixin(value = OreType.class, remap = false, priority = 100)
public class OreTypeMixin {
    @Shadow
    @Final
    @Mutable
    private static OreType[] $VALUES;

    @Mutable
    @Shadow
    public static Codec<OreType> CODEC;

    @Invoker("<init>")
    public static OreType oreType$initInvoker(String internalName, int internalId, IResource resource,
                                               BaseOreConfig... configs) {
        throw new AssertionError();
    }

    @Invoker("<init>")
    public static OreType oreType$initInvoker(String internalName, int internalId, IResource resource, int exp,
                                               BaseOreConfig... configs) {
        throw new AssertionError();
    }

    @Invoker("<init>")
    public static OreType oreType$initInvoker(String internalName, int internalId, IResource resource,
                                               int minExp, int maxExp, BaseOreConfig... configs) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void oreTypeClinit(CallbackInfo ci) {
        OreType existingSilver = mekanismsun$findExistingSilver();

        if (existingSilver != null) {
            // Another Mekanism addon already owns the logical silver OreType.
            // Reuse that exact enum instance so identity checks in Sun's block
            // registration code continue to work without creating a duplicate.
            MSOreType.SILVER = existingSilver;
            return;
        }

        MSOreType.SILVER = mekanismsun$addVariant(
                "SILVER",
                MSResources.SILVER,
                new BaseOreConfig(
                        "normal",
                        8,
                        0,
                        4,
                        HeightShape.TRAPEZOID,
                        OreAnchor.absolute(-48),
                        OreAnchor.absolute(32)
                )
        );

        // Only rebuild the codec when Sun actually appended a new OreType.
        mekanismsun$reinitializeByIdMap();
    }

    @Unique
    private static OreType mekanismsun$findExistingSilver() {
        String targetSuffix = MSResources.SILVER.getRegistrySuffix();

        for (OreType ore : $VALUES) {
            if (targetSuffix.equals(ore.getResource().getRegistrySuffix())) {
                return ore;
            }
        }

        return null;
    }

    @Unique
    private static OreType mekanismsun$addVariant(String internalName, IResource resource,
                                                   BaseOreConfig... configs) {
        ArrayList<OreType> variants = new ArrayList<>(Arrays.asList($VALUES));
        OreType upgrade = oreType$initInvoker(
                internalName,
                variants.getLast().ordinal() + 1,
                resource,
                configs
        );
        variants.add(upgrade);
        OreTypeMixin.$VALUES = variants.toArray(new OreType[0]);
        return upgrade;
    }

    @Unique
    private static void mekanismsun$reinitializeByIdMap() {
        CODEC = StringRepresentable.fromEnum(OreType::values);
    }
}
