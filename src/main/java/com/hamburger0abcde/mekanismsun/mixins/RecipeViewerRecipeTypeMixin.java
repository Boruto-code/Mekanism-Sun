package com.hamburger0abcde.mekanismsun.mixins;

import com.hamburger0abcde.mekanismsun.registries.MSBlocks;
import mekanism.api.recipes.ChemicalToChemicalRecipe;
import mekanism.client.recipe_viewer.type.RVRecipeTypeWrapper;
import mekanism.client.recipe_viewer.type.RecipeViewerRecipeType;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.registries.MekanismBlocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RecipeViewerRecipeType.class, remap = false)
public class RecipeViewerRecipeTypeMixin {
    @Final
    @Shadow
    @Mutable
    public static RVRecipeTypeWrapper<?, ChemicalToChemicalRecipe, ?> ACTIVATING;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void modifyEnergyConversion(CallbackInfo callbackInfo) {
        ACTIVATING = new RVRecipeTypeWrapper<>(MekanismRecipeType.ACTIVATING, ChemicalToChemicalRecipe.class,
                -4, -13, 168, 60,
                MekanismBlocks.SOLAR_NEUTRON_ACTIVATOR, MSBlocks.ELECTRIC_NEUTRON_ACTIVATOR);
    }
}
