package com.hamburger0abcde.mekanismsun.common.recipes.freeze;

import com.hamburger0abcde.mekanismsun.common.recipes.MSRecipeType;
import com.hamburger0abcde.mekanismsun.common.recipes.transmutation.BasicTransmutationRecipe;
import com.hamburger0abcde.mekanismsun.common.registries.MSBlocks;
import com.hamburger0abcde.mekanismsun.common.registries.MSRecipeSerializers;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Contract;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BasicFreezeRecipe extends FreezeRecipe {
    protected final FluidStackIngredient input;
    protected final FluidStack output;

    /**
     * @param input  Input.
     * @param output Output.
     */
    public BasicFreezeRecipe(FluidStackIngredient input, FluidStack output) {
        this.input = Objects.requireNonNull(input, "Input cannot be null.");
        Objects.requireNonNull(output, "Output cannot be null.");
        if (output.isEmpty()) {
            throw new IllegalArgumentException("Output cannot be empty.");
        }
        this.output = output.copy();
    }

    @Override
    public boolean test(FluidStack fluidStack) {
        return this.input.test(fluidStack);
    }

    @Override
    public FluidStackIngredient getInput() {
        return input;
    }

    @Override
    public List<FluidStack> getOutputDefinition() {
        return Collections.singletonList(output);
    }

    @Override
    @Contract(value = "_ ->new", pure = true)
    public FluidStack getOutput(FluidStack input) {
        return output.copy();
    }

    public FluidStack getOutputRaw() {
        return output;
    }

    @Override
    public RecipeSerializer<BasicFreezeRecipe> getSerializer() {
        return MSRecipeSerializers.FREEZE.get();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasicFreezeRecipe other = (BasicFreezeRecipe) o;
        return input.equals(other.input) && FluidStack.matches(output, other.output);
    }

    @Override
    public int hashCode() {
        int hash = input.hashCode();
        hash = 31 * hash + FluidStack.hashFluidAndComponents(output);
        hash = 31 * hash + output.getAmount();
        return hash;
    }
}
