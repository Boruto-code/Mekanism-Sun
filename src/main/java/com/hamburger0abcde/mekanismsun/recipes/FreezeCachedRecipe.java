package com.hamburger0abcde.mekanismsun.recipes;

import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BooleanSupplier;

@NothingNullByDefault
public class FreezeCachedRecipe extends CachedRecipe<FreezeRecipe> {
    private final IInputHandler<@NotNull ChemicalStack> inputHandler;
    private final IOutputHandler<@NotNull FluidStack> outputHandler;

    private ChemicalStack recipeChemical = ChemicalStack.EMPTY;
    @Nullable
    private FluidStack output;

    public FreezeCachedRecipe(FreezeRecipe recipe, BooleanSupplier recheckAllErrors,
                              IInputHandler<@NotNull ChemicalStack> chemicalInputHandler,
                              IOutputHandler<@NotNull FluidStack> fluidOutputHandler) {
        super(recipe, recheckAllErrors);
        this.inputHandler = Objects.requireNonNull(chemicalInputHandler, "Input handler cannot be null.");
        this.outputHandler = Objects.requireNonNull(fluidOutputHandler, "Output handler cannot be null.");
    }

    @Override
    protected void calculateOperationsThisTick(OperationTracker tracker) {
        super.calculateOperationsThisTick(tracker);
        if (tracker.shouldContinueChecking()) {
            recipeChemical = inputHandler.getRecipeInput(recipe.getInput());
            if (recipeChemical.isEmpty()) {
                tracker.mismatchedRecipe();
            } else {
                inputHandler.calculateOperationsCanSupport(tracker, recipeChemical);
                if (tracker.shouldContinueChecking()) {
                    output = recipe.getOutput(recipeChemical);
                    outputHandler.calculateOperationsCanSupport(tracker, output);
                }
            }
        }
    }

    @Override
    public boolean isInputValid() {
        ChemicalStack chemical = inputHandler.getInput();
        return !chemical.isEmpty() && recipe.test(chemical);
    }

    @Override
    protected void finishProcessing(int operations) {
        if (output != null && !recipeChemical.isEmpty()) {
            inputHandler.use(recipeChemical, operations);
            outputHandler.handleOutput(output, operations);
        }
    }
}
