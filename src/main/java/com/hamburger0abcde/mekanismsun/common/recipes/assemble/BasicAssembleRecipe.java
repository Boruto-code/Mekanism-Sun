package com.hamburger0abcde.mekanismsun.common.recipes.assemble;

import com.hamburger0abcde.mekanismsun.common.recipes.BasicItemItemChemicalRecipe;
import com.hamburger0abcde.mekanismsun.common.recipes.MSRecipeType;
import com.hamburger0abcde.mekanismsun.common.recipes.alloying.BasicAlloyingRecipe;
import com.hamburger0abcde.mekanismsun.common.registries.MSBlocks;
import com.hamburger0abcde.mekanismsun.common.registries.MSRecipeSerializers;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class BasicAssembleRecipe extends BasicItemItemChemicalRecipe {
    public BasicAssembleRecipe(ItemStackIngredient mainInput, ItemStackIngredient extraInput,
                               ChemicalStackIngredient chemicalInput, ItemStack output) {
        super(mainInput, extraInput, chemicalInput, output);
    }

    @Override
    public RecipeType<BasicItemItemChemicalRecipe> getType() {
        return MSRecipeType.ASSEMBLE.get();
    }

    @Override
    public RecipeSerializer<BasicAssembleRecipe> getSerializer() {
        return MSRecipeSerializers.ASSEMBLE.get();
    }

    @Override
    public String getGroup() {
        return MSBlocks.ASSEMBLER.getName();
    }

    @Override
    public ItemStack getToastSymbol() {
        return MSBlocks.ASSEMBLER.asItem().getDefaultInstance();
    }
}
