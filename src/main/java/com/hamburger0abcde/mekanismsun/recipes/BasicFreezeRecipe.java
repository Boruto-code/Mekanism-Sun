package com.hamburger0abcde.mekanismsun.recipes;

import com.hamburger0abcde.mekanismsun.registries.MSRecipeType;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;

@NothingNullByDefault
public class BasicFreezeRecipe extends FreezeRecipe {
    public BasicFreezeRecipe(ChemicalStackIngredient input, long energyRequired, int duration,
                             FluidStackIngredient output) {
        super(input, energyRequired, duration, output);
    }

    @Override
    public RecipeType<FreezeRecipe> getType() {
        return MSRecipeType.FREEZE.get();
    }
}
