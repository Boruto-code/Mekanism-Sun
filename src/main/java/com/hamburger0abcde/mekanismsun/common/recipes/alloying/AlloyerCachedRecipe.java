package com.hamburger0abcde.mekanismsun.common.recipes.alloying;

import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BooleanSupplier;

@NothingNullByDefault
public class AlloyerCachedRecipe extends CachedRecipe<AlloyingRecipe> {
    private final IInputHandler<@NotNull ItemStack> itemInputHandler;
    private final IInputHandler<@NotNull ItemStack> extraInputHandler;
    private final IInputHandler<@NotNull ChemicalStack> chemicalInputHandler;
    private final IOutputHandler<@NotNull ItemStack> outputHandler;

    private ItemStack recipeItem = ItemStack.EMPTY;
    private ItemStack recipeExtra = ItemStack.EMPTY;
    private ChemicalStack recipeChemical = ChemicalStack.EMPTY;
    @Nullable
    private ItemStack output;

    public AlloyerCachedRecipe(AlloyingRecipe recipe, BooleanSupplier recheckAllErrors,
                               IInputHandler<@NotNull ItemStack> itemInputHandler, IInputHandler<@NotNull ItemStack> extraInputHandler,
                               IInputHandler<@NotNull ChemicalStack> chemicalInputHandler, IOutputHandler<@NotNull ItemStack> outputHandler) {
        super(recipe, recheckAllErrors);
        this.itemInputHandler = Objects.requireNonNull(itemInputHandler, "Item input handler cannot be null.");
        this.extraInputHandler = Objects.requireNonNull(extraInputHandler, "Extra input handler cannot be null.");
        this.chemicalInputHandler = Objects.requireNonNull(chemicalInputHandler, "Chemical input handler cannot be null.");
        this.outputHandler = Objects.requireNonNull(outputHandler, "Output handler cannot be null.");
    }

    @Override
    protected void calculateOperationsThisTick(OperationTracker tracker) {
        super.calculateOperationsThisTick(tracker);
        if (tracker.shouldContinueChecking()) {
            recipeItem = itemInputHandler.getRecipeInput(recipe.getMainInput());
            if (recipeItem.isEmpty()) {
                tracker.mismatchedRecipe();
            } else {
                recipeExtra = extraInputHandler.getRecipeInput(recipe.getExtraInput());
                if (recipeExtra.isEmpty()) {
                    tracker.mismatchedRecipe();
                } else {
                    recipeChemical = chemicalInputHandler.getRecipeInput(recipe.getChemicalInput());
                    if (recipeChemical.isEmpty()) {
                        tracker.mismatchedRecipe();
                    } else {
                        itemInputHandler.calculateOperationsCanSupport(tracker, recipeItem);
                        if (tracker.shouldContinueChecking()) {
                            extraInputHandler.calculateOperationsCanSupport(tracker, recipeExtra);
                            if (tracker.shouldContinueChecking()) {
                                chemicalInputHandler.calculateOperationsCanSupport(tracker, recipeChemical);
                                if (tracker.shouldContinueChecking()) {
                                    output = recipe.getOutput(recipeItem, recipeExtra, recipeChemical);
                                    outputHandler.calculateOperationsCanSupport(tracker, output);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isInputValid() {
        ItemStack item = itemInputHandler.getInput();
        if (item.isEmpty()) {
            return false;
        }
        ChemicalStack chemical = chemicalInputHandler.getInput();
        if (chemical.isEmpty()) {
            return false;
        }
        ItemStack extra = extraInputHandler.getInput();
        return !extra.isEmpty() && recipe.test(item, extra, chemical);
    }

    @Override
    protected void finishProcessing(int operations) {
        if (output != null && !recipeItem.isEmpty() && !recipeExtra.isEmpty() && !recipeChemical.isEmpty()) {
            itemInputHandler.use(recipeItem, operations);
            extraInputHandler.use(recipeExtra, operations);
            chemicalInputHandler.use(recipeChemical, operations);
            outputHandler.handleOutput(output, operations);
        }
    }
}
