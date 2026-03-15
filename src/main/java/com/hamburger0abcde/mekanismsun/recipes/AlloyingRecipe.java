package com.hamburger0abcde.mekanismsun.recipes;

import com.hamburger0abcde.mekanismsun.recipes.vanilla_input.ItemItemChemicalRecipeInput;
import lombok.Getter;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.TriPredicate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@NothingNullByDefault
public abstract class AlloyingRecipe extends MekanismRecipe<ItemItemChemicalRecipeInput>
        implements TriPredicate<@NotNull ItemStack, @NotNull ItemStack, @NotNull ChemicalStack> {

    private final ItemStackIngredient mainInput;
    private final ItemStackIngredient extraInput;
    private final ChemicalStackIngredient chemicalInput;
    private final ItemStack output;

    public AlloyingRecipe(ItemStackIngredient mainInput, ItemStackIngredient extraInput,
                          ChemicalStackIngredient chemicalInput, ItemStack output) {
        this.mainInput = Objects.requireNonNull(mainInput, "Main input cannot be null.");
        this.extraInput = Objects.requireNonNull(extraInput, "Secondary input cannot be null.");
        this.chemicalInput = Objects.requireNonNull(chemicalInput, "Chemical input cannot be null.");
        Objects.requireNonNull(output, "Output cannot be null.");
        if (output.isEmpty()) {
            throw new IllegalArgumentException("Output cannot be empty.");
        }
        this.output = output.copy();
    }

    @Override
    public boolean test(ItemStack input, ItemStack extra, ChemicalStack chemical) {
        return mainInput.test(input) && extraInput.test(extra) && chemicalInput.test(chemical);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public ItemStack getOutput(@NotNull ItemStack input, @NotNull ItemStack extra, @NotNull ChemicalStack chemical) {
        return output.copy();
    }

    @NotNull
    @Override
    public ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return output.copy();
    }

    @Override
    public boolean matches(ItemItemChemicalRecipeInput input, Level level) {
        return !isIncomplete() && test(input.main(), input.extra(), input.chemical());
    }

    public List<ItemStack> getOutputDefinition() {
        return Collections.singletonList(output);
    }

    @Override
    public boolean isIncomplete() {
        return mainInput.hasNoMatchingInstances() || extraInput.hasNoMatchingInstances() || chemicalInput.hasNoMatchingInstances();
    }
}
