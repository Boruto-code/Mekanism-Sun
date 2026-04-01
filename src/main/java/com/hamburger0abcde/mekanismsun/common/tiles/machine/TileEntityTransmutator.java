package com.hamburger0abcde.mekanismsun.common.tiles.machine;

import com.hamburger0abcde.mekanismsun.client.recipe_viewer.type.MSRecipeViewerRecipeTypes;
import com.hamburger0abcde.mekanismsun.common.recipes.MSRecipeType;
import com.hamburger0abcde.mekanismsun.common.registries.MSBlocks;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.registration.impl.RecipeTypeRegistryObject;
import mekanism.common.tile.prefab.TileEntityElectricMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileEntityTransmutator extends TileEntityElectricMachine {
    public TileEntityTransmutator(BlockPos pos, BlockState state) {
        super(MSBlocks.TRANSMUTATOR, pos, state, BASE_TICKS_REQUIRED);
    }

    @NotNull
    @Override
    public RecipeTypeRegistryObject<SingleRecipeInput, ItemStackToItemStackRecipe,
            InputRecipeCache.SingleItem<ItemStackToItemStackRecipe>> getRecipeType() {
        return MSRecipeType.TRANSMUTATION;
    }

    @Override
    public @Nullable IRecipeViewerRecipeType<ItemStackToItemStackRecipe> recipeViewerType() {
        return MSRecipeViewerRecipeTypes.TRANSMUTATION;
    }
}
