package com.hamburger0abcde.mekanismsun.mixins;

import com.hamburger0abcde.mekanismsun.recipes.alloying.AlloyingRecipe;
import com.hamburger0abcde.mekanismsun.recipes.MSInputRecipeCache;
import com.hamburger0abcde.mekanismsun.recipes.MSRecipeType;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.Mekanism;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
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
        MSRecipeType.ALLOYING = register(Mekanism.rl("alloying"),
                recipeType ->
                        new MSInputRecipeCache.ItemItemChemical<>(recipeType,
                                AlloyingRecipe::getMainInput, AlloyingRecipe::getExtraInput, AlloyingRecipe::getChemicalInput));
    }
}
