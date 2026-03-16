package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.recipes.alloying.BasicAlloyingRecipe;
import com.hamburger0abcde.mekanismsun.recipes.transmutation.BasicTransmutationRecipe;
import com.mojang.datafixers.util.Function4;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mekanism.api.SerializationConstants;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.common.recipe.serializer.MekanismRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MSRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MekanismSun.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicAlloyingRecipe>> ALLOYING =
            RECIPE_SERIALIZERS.register("alloying", () -> alloying(BasicAlloyingRecipe::new));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicTransmutationRecipe>> TRANSMUTATION =
            RECIPE_SERIALIZERS.register("transmutation", () -> MekanismRecipeSerializer.itemToItem(BasicTransmutationRecipe::new));

    public static <RECIPE extends BasicAlloyingRecipe> MekanismRecipeSerializer<RECIPE> alloying(Function4<ItemStackIngredient,
                ItemStackIngredient, ChemicalStackIngredient, ItemStack, RECIPE> factory) {
        return new MekanismRecipeSerializer<>(RecordCodecBuilder.mapCodec(instance -> instance.group(
                ItemStackIngredient.CODEC.fieldOf(SerializationConstants.MAIN_INPUT).forGetter(BasicAlloyingRecipe::getMainInput),
                ItemStackIngredient.CODEC.fieldOf(SerializationConstants.EXTRA_INPUT).forGetter(BasicAlloyingRecipe::getExtraInput),
                ChemicalStackIngredient.CODEC.fieldOf(SerializationConstants.CHEMICAL_INPUT).forGetter(BasicAlloyingRecipe::getChemicalInput),
                ItemStack.CODEC.fieldOf(SerializationConstants.OUTPUT).forGetter(BasicAlloyingRecipe::getOutputRaw)
        ).apply(instance, factory)), StreamCodec.composite(
                ItemStackIngredient.STREAM_CODEC, BasicAlloyingRecipe::getMainInput,
                ItemStackIngredient.STREAM_CODEC, BasicAlloyingRecipe::getExtraInput,
                ChemicalStackIngredient.STREAM_CODEC, BasicAlloyingRecipe::getChemicalInput,
                ItemStack.STREAM_CODEC, BasicAlloyingRecipe::getOutputRaw,
                factory
        ));
    }
}
