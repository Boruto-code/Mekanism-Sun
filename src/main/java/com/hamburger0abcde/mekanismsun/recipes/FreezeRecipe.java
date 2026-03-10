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

import java.util.List;
import java.util.Objects;

@Getter
@NothingNullByDefault
public abstract class FreezeRecipe extends MekanismRecipe<SingleChemicalRecipeInput> {
    private final ChemicalStackIngredient inputChemical;
    private final long energyRequired;
    private final int duration;
    private final FluidStackIngredient outputFluid;

    public FreezeRecipe(ChemicalStackIngredient inputChemical, long energyRequired, int duration,
                        FluidStackIngredient outputFluid) {
        this.inputChemical = Objects.requireNonNull(inputChemical, "Chemical input cannot be null.");
        this.energyRequired = Objects.requireNonNull(energyRequired, "Required energy cannot be null.");
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be positive.");
        }
        this.duration = duration;
        Objects.requireNonNull(outputFluid, "Fluid output cannot be null");
        this.outputFluid = outputFluid;
    }

    public List<FluidStack> getOutputDefinition() {
        return outputFluid.getRepresentations();
    }

    @Contract(value = "_ -> new", pure = true)
    public FluidStack getOutput(ChemicalStack chemical) {
        return outputFluid.getRepresentations().getFirst().copy();
    }

    public FluidStackIngredient getOutputRaw() {
        return outputFluid;
    }

    @Override
    public boolean isIncomplete() {
        return inputChemical.hasNoMatchingInstances() || outputFluid.hasNoMatchingInstances();
    }

    public boolean test(ChemicalStack chemicalStack) {
        return this.inputChemical.test(chemicalStack);
    }

    @Override
    public boolean matches(SingleChemicalRecipeInput input, Level level) {
        return !isIncomplete() && test(input.chemical());
    }
}
