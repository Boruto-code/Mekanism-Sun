package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.recipes.BasicFreezeRecipe;
import com.mojang.datafixers.util.Function4;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mekanism.api.SerializationConstants;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.common.recipe.serializer.MekanismRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BiFunction;

public class MSRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MekanismSun.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicFreezeRecipe>> FREEZE =
            RECIPE_SERIALIZERS.register("freeze", () -> freeze(BasicFreezeRecipe::new));

    public static <RECIPE extends BasicFreezeRecipe> MekanismRecipeSerializer<RECIPE> freeze(
            BiFunction<ChemicalStackIngredient, FluidStack, RECIPE> factory) {
        return new MekanismRecipeSerializer<>(RecordCodecBuilder.mapCodec(instance -> instance.group(
                ChemicalStackIngredient.CODEC.fieldOf(SerializationConstants.INPUT).forGetter(BasicFreezeRecipe::getInput),
                FluidStack.CODEC.fieldOf(SerializationConstants.OUTPUT).forGetter(BasicFreezeRecipe::getOutputRaw)
        ).apply(instance, factory)), StreamCodec.composite(
                ChemicalStackIngredient.STREAM_CODEC, BasicFreezeRecipe::getInput,
                FluidStack.STREAM_CODEC, BasicFreezeRecipe::getOutputRaw,
                factory
        ));
    }
}
