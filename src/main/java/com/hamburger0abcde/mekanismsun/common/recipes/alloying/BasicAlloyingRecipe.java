package com.hamburger0abcde.mekanismsun.common.recipes.alloying;

import com.hamburger0abcde.mekanismsun.common.recipes.MSRecipeType;
import com.hamburger0abcde.mekanismsun.common.registries.MSBlocks;
import com.hamburger0abcde.mekanismsun.common.registries.MSRecipeSerializers;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

@NothingNullByDefault
public class BasicAlloyingRecipe extends AlloyingRecipe {
    public BasicAlloyingRecipe(ItemStackIngredient mainInput, ItemStackIngredient extraInput,
                               ChemicalStackIngredient chemicalInput, ItemStack output) {
        super(mainInput, extraInput, chemicalInput, output);
    }

    @Override
    public RecipeType<AlloyingRecipe> getType() {
        return MSRecipeType.ALLOYING.get();
    }

    @Override
    public RecipeSerializer<BasicAlloyingRecipe> getSerializer() {
        return MSRecipeSerializers.ALLOYING.get();
    }

    @Override
    public String getGroup() {
        return MSBlocks.ALLOYER.getName();
    }

    @Override
    public ItemStack getToastSymbol() {
        return MSBlocks.ALLOYER.asItem().getDefaultInstance();
    }
}
