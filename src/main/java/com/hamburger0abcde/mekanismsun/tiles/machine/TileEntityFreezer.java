package com.hamburger0abcde.mekanismsun.tiles.machine;

import com.hamburger0abcde.mekanismsun.recipes.FreezeRecipe;
import com.hamburger0abcde.mekanismsun.registries.MSBlocks;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper.*;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.FluidInventorySlot;
import mekanism.common.inventory.slot.chemical.ChemicalInventorySlot;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.tile.prefab.TileEntityProgressMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TileEntityFreezer extends TileEntityProgressMachine<FreezeRecipe>
        implements InputRecipeCache.SingleChemical<FreezeRecipe> {

    public static final RecipeError NOT_ENOUGH_CHEMICAL_INPUT_ERROR = RecipeError.create();
    public static final RecipeError NOT_ENOUGH_FLUID_OUTPUT_ERROR = RecipeError.create();
    private static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            NOT_ENOUGH_CHEMICAL_INPUT_ERROR,
            NOT_ENOUGH_FLUID_OUTPUT_ERROR,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
    );

    @WrappingComputerMethod(wrapper = ComputerChemicalTankWrapper.class, methodNames = {"getInput", "getInputCapability",
            "getInputNeeded", "getInputFilledPercentage"}, docPlaceholder = "input")
    public BasicChemicalTank inputChemicalTank;
    @WrappingComputerMethod(wrapper = ComputerChemicalTankWrapper.class, methodNames = {"getOutput", "getOutputCapability",
            "getOutputNeeded", "getOutputFilledPercentage"}, docPlaceholder = "output")
    public BasicFluidTank outputFluidTank;

    private final IOutputHandler<@NotNull FluidStack> outputHandler;
    private final IInputHandler<@NotNull ChemicalStack> inputHandler;

    private MachineEnergyContainer<TileEntityFreezer> energyContainer;
    @WrappingComputerMethod(wrapper = ComputerIInventorySlotWrapper.class, methodNames = "getChemicalItemInput",
            docPlaceholder = "chemical item input slot")
    ChemicalInventorySlot chemicalInputSlot;
    @WrappingComputerMethod(wrapper = ComputerIInventorySlotWrapper.class, methodNames = "getFluidItemOutput",
            docPlaceholder = "fluid item output slot")
    FluidInventorySlot fluidOutputSlot;
    @WrappingComputerMethod(wrapper = ComputerIInventorySlotWrapper.class, methodNames = "getEnergyItem",
            docPlaceholder = "energy slot")
    EnergyInventorySlot energySlot;

    public TileEntityFreezer(BlockPos pos, BlockState state) {
        super();
    }
}
