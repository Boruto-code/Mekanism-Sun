package com.hamburger0abcde.mekanismsun.common.recipes.freeze;

import com.hamburger0abcde.mekanismsun.common.recipes.MSRecipeType;
import com.hamburger0abcde.mekanismsun.common.registries.MSBlocks;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.recipes.FluidToFluidRecipe;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.MekanismRecipeTypes;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.vanilla_input.SingleFluidRecipeInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

@NothingNullByDefault
public abstract class FreezeRecipe extends MekanismRecipe<SingleFluidRecipeInput> implements Predicate<@NotNull FluidStack> {


    @Override
    public abstract boolean test(FluidStack fluidStack);

    @Override
    public boolean matches(SingleFluidRecipeInput input, Level level) {
        //Don't match incomplete recipes or ones that don't match
        return !isIncomplete() && test(input.fluid());
    }

    public abstract FluidStackIngredient getInput();

    public abstract List<FluidStack> getOutputDefinition();

    @Contract(value = "_ ->new", pure = true)
    public abstract FluidStack getOutput(FluidStack input);

    @Override
    public boolean isIncomplete() {
        return getInput().hasNoMatchingInstances();
    }

    @Override
    public void logMissingTags() {
        getInput().logMissingTags();
    }

    @Override
    public final RecipeType<FreezeRecipe> getType() {
        return MSRecipeType.FREEZE.get();
    }

    @Override
    public String getGroup() {
        return MSBlocks.TUNGSTENSTEEL_CASING.getName();
    }

    @Override
    public ItemStack getToastSymbol() {
        return MSBlocks.TUNGSTENSTEEL_CASING.asItem().getDefaultInstance();
    }
}
