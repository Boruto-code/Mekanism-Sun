package com.hamburger0abcde.mekanismsun.recipes;

import com.hamburger0abcde.mekanismsun.registries.MSBlocks;
import com.hamburger0abcde.mekanismsun.registries.MSRecipeSerializers;
import com.hamburger0abcde.mekanismsun.registries.MSRecipeType;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;

@NothingNullByDefault
public class BasicFreezeRecipe extends FreezeRecipe {
    public BasicFreezeRecipe(ChemicalStackIngredient input, FluidStack output) {
        super(input, output);
    }

    @Override
    public RecipeType<FreezeRecipe> getType() {
        return MSRecipeType.FREEZE.get();
    }

    @Override
    public RecipeSerializer<BasicFreezeRecipe> getSerializer() {
        return MSRecipeSerializers.FREEZE.get();
    }

    @Override
    public String getGroup() {
        return MSBlocks.FREEZER.getName();
    }

    @Override
    public ItemStack getToastSymbol() {
        return MSBlocks.FREEZER.asItem().getDefaultInstance();
    }
}
