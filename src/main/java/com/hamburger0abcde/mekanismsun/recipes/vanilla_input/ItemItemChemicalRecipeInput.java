package com.hamburger0abcde.mekanismsun.recipes.vanilla_input;

import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.vanilla_input.ItemChemicalRecipeInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

@NothingNullByDefault
public record ItemItemChemicalRecipeInput(ItemStack main, ItemStack extra, ChemicalStack chemical) implements ItemChemicalRecipeInput {
    @Override
    public ItemStack getItem(int index) {
        if (index == 0) {
            return main;
        } else if (index == 1) {
            return extra;
        } else {
            throw new IllegalArgumentException("No item for index " + index);
        }
    }

    @Override
    public ChemicalStack getChemical(int index) {
        if (index != 0) {
            throw new IllegalArgumentException("No chemical for index " + index);
        }
        return chemical;
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return main.isEmpty() || extra.isEmpty() || chemical.isEmpty();
    }
}
