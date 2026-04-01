package com.hamburger0abcde.mekanismsun.common.recipes.transmutation;

import com.hamburger0abcde.mekanismsun.common.recipes.MSRecipeType;
import com.hamburger0abcde.mekanismsun.common.registries.MSBlocks;
import com.hamburger0abcde.mekanismsun.common.registries.MSRecipeSerializers;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.recipes.basic.BasicItemStackToItemStackRecipe;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

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
