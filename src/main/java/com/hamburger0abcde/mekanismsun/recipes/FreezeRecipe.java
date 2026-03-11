package com.hamburger0abcde.mekanismsun.recipes;

import lombok.Getter;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.vanilla_input.SingleChemicalRecipeInput;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Contract;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@NothingNullByDefault
public abstract class FreezeRecipe extends MekanismRecipe<SingleChemicalRecipeInput> implements Predicate<ChemicalStack> {
    private final ChemicalStackIngredient inputChemical;
    private final FluidStack outputFluid;

    public FreezeRecipe(ChemicalStackIngredient inputChemical, FluidStack outputFluid) {
        this.inputChemical = Objects.requireNonNull(inputChemical, "Chemical input cannot be null.");
        Objects.requireNonNull(outputFluid, "Fluid output cannot be null");
        this.outputFluid = outputFluid;
    }

    public List<FluidStack> getOutputDefinition() {
        return Collections.singletonList(outputFluid);
    }

    public ChemicalStackIngredient getInput() {
        return inputChemical;
    }

    public FluidStack getOutput(ChemicalStack chemical) {
        return outputFluid.copy();
    }

    public FluidStack getOutputRaw() {
        return outputFluid.copy();
    }

    @Override
    public boolean isIncomplete() {
        return inputChemical.hasNoMatchingInstances();
    }

    public boolean test(ChemicalStack chemicalStack) {
        return this.inputChemical.test(chemicalStack);
    }

    @Override
    public boolean matches(SingleChemicalRecipeInput input, Level level) {
        return !isIncomplete() && test(input.chemical());
    }
}
