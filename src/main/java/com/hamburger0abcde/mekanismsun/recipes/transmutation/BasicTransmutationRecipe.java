package com.hamburger0abcde.mekanismsun.recipes.transmutation;

import com.hamburger0abcde.mekanismsun.recipes.MSRecipeType;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.recipes.basic.BasicItemStackToItemStackRecipe;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;

@NothingNullByDefault
public class BasicTransmutationRecipe extends BasicItemStackToItemStackRecipe {
    public BasicTransmutationRecipe(ItemStackIngredient input, ItemStack output) {
        super(input, output, MSRecipeType.TRANSMUTATION.get());
    }
}
