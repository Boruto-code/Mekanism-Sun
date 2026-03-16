package com.hamburger0abcde.mekanismsun.recipes.transmutation;

import com.hamburger0abcde.mekanismsun.recipes.MSRecipeType;
import com.hamburger0abcde.mekanismsun.recipes.alloying.AlloyingRecipe;
import com.hamburger0abcde.mekanismsun.registries.MSBlocks;
import com.hamburger0abcde.mekanismsun.registries.MSRecipeSerializers;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.recipes.basic.BasicItemStackToItemStackRecipe;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

@NothingNullByDefault
public class BasicTransmutationRecipe extends BasicItemStackToItemStackRecipe {
    public BasicTransmutationRecipe(ItemStackIngredient input, ItemStack output) {
        super(input, output, MSRecipeType.TRANSMUTATION.get());
    }

    @Override
    public RecipeSerializer<BasicTransmutationRecipe> getSerializer() {
        return MSRecipeSerializers.TRANSMUTATION.get();
    }

    @Override
    public String getGroup() {
        return MSBlocks.TRANSMUTATOR.getName();
    }

    @Override
    public ItemStack getToastSymbol() {
        return MSBlocks.TRANSMUTATOR.asItem().getDefaultInstance();
    }
}
