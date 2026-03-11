package com.hamburger0abcde.mekanismsun.mixins;

import com.hamburger0abcde.mekanismsun.recipes.FreezeRecipe;
import com.hamburger0abcde.mekanismsun.recipes.MSRecipeType;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.Mekanism;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.registration.impl.RecipeTypeRegistryObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(MekanismRecipeType.class)
public class MekanismRecipeTypeMixin {
    @Shadow
    protected static <VANILLA_INPUT extends RecipeInput, RECIPE extends MekanismRecipe<VANILLA_INPUT>,
            INPUT_CACHE extends IInputRecipeCache> RecipeTypeRegistryObject<VANILLA_INPUT, RECIPE, INPUT_CACHE> register(
            ResourceLocation name, Function<MekanismRecipeType<VANILLA_INPUT, RECIPE, INPUT_CACHE>, INPUT_CACHE> inputCacheCreator) {
        return null;
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void mekanismsun$initMSRecipe(CallbackInfo ci) {
        MSRecipeType.FREEZE = register(Mekanism.rl("freeze"),
                recipeType ->
                        new InputRecipeCache.SingleChemical<>(recipeType, FreezeRecipe::getInput));
    }
}
