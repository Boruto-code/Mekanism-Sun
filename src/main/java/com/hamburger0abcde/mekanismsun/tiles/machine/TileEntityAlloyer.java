package com.hamburger0abcde.mekanismsun.tiles.machine;

import com.hamburger0abcde.mekanismsun.recipes.alloying.AlloyerCachedRecipe;
import com.hamburger0abcde.mekanismsun.recipes.alloying.AlloyingRecipe;
import com.hamburger0abcde.mekanismsun.recipes.MSInputRecipeCache;
import com.hamburger0abcde.mekanismsun.recipes.MSRecipeType;
import com.hamburger0abcde.mekanismsun.recipes.vanilla_input.ItemItemChemicalRecipeInput;
import com.hamburger0abcde.mekanismsun.registries.MSBlocks;
import lombok.Getter;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper.*;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.integration.computer.computercraft.ComputerConstants;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.lookup.ITripleRecipeLookupHandler;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.prefab.TileEntityProgressMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TileEntityAlloyer extends TileEntityProgressMachine<AlloyingRecipe>
        implements ITripleRecipeLookupHandler<ItemStack, ItemStack, ChemicalStack, AlloyingRecipe,
            MSInputRecipeCache.ItemItemChemical<AlloyingRecipe>> {

    public static final RecipeError NOT_ENOUGH_ITEM_INPUT_ERROR = RecipeError.create();
    public static final RecipeError NOT_ENOUGH_EXTRA_INPUT_ERROR = RecipeError.create();
    public static final RecipeError NOT_ENOUGH_GAS_INPUT_ERROR = RecipeError.create();
    public static final RecipeError NOT_ENOUGH_SPACE_ITEM_OUTPUT_ERROR = RecipeError.create();
    private static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            NOT_ENOUGH_ITEM_INPUT_ERROR,
            NOT_ENOUGH_EXTRA_INPUT_ERROR,
            NOT_ENOUGH_GAS_INPUT_ERROR,
            NOT_ENOUGH_SPACE_ITEM_OUTPUT_ERROR,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
    );

    private static final int BASE_DURATION = 100;
    public static final long MAX_GAS = 16_000;

    @WrappingComputerMethod(wrapper = ComputerChemicalTankWrapper.class, methodNames = {"getInputChemical",
            "getInputChemicalCapacity", "getInputChemicalNeeded", "getInputChemicalFilledPercentage"}, docPlaceholder = "chemical input")
    public IChemicalTank inputChemicalTank;
    private final IOutputHandler<@NotNull ItemStack> outputHandler;
    private final IInputHandler<@NotNull ItemStack> itemInputHandler;
    private final IInputHandler<@NotNull ItemStack> extraInputHandler;
    private final IInputHandler<@NotNull ChemicalStack> chemicalInputHandler;

    @Getter
    private MachineEnergyContainer<TileEntityAlloyer> energyContainer;
    @WrappingComputerMethod(wrapper = ComputerIInventorySlotWrapper.class, methodNames = "getInputItem",
            docPlaceholder = "item input slot")
    InputInventorySlot mainInputSlot;
    @WrappingComputerMethod(wrapper = ComputerIInventorySlotWrapper.class, methodNames = "getExtraInputItem",
            docPlaceholder = "extra item input slot")
    InputInventorySlot extraInputSlot;
    @WrappingComputerMethod(wrapper = ComputerIInventorySlotWrapper.class, methodNames = "getOutputItem",
            docPlaceholder = "item output slot")
    OutputInventorySlot outputSlot;
    @WrappingComputerMethod(wrapper = ComputerIInventorySlotWrapper.class, methodNames = "getEnergyItem",
            docPlaceholder = "energy slot")
    EnergyInventorySlot energySlot;

    public TileEntityAlloyer(BlockPos pos, BlockState state) {
        super(MSBlocks.ALLOYER, pos, state, TRACKED_ERROR_TYPES, BASE_DURATION);
        configComponent.setupItemIOExtraConfig(mainInputSlot, outputSlot, extraInputSlot, energySlot);
        configComponent.setupInputConfig(TransmissionType.CHEMICAL, inputChemicalTank);
        configComponent.setupInputConfig(TransmissionType.ENERGY, energyContainer);

        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(configComponent, TransmissionType.ITEM, TransmissionType.CHEMICAL)
                .setCanTankEject(tank -> tank != inputChemicalTank);

        itemInputHandler = InputHelper.getInputHandler(mainInputSlot, NOT_ENOUGH_ITEM_INPUT_ERROR);
        extraInputHandler = InputHelper.getInputHandler(extraInputSlot, NOT_ENOUGH_EXTRA_INPUT_ERROR);
        chemicalInputHandler = InputHelper.getInputHandler(inputChemicalTank, NOT_ENOUGH_GAS_INPUT_ERROR);
        outputHandler = OutputHelper.getOutputHandler(outputSlot, NOT_ENOUGH_SPACE_ITEM_OUTPUT_ERROR);
    }

    @NotNull
    @Override
    public IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener, IContentsListener recipeCacheListener,
                                                       IContentsListener unpause) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(inputChemicalTank = BasicChemicalTank.createModern(MAX_GAS,
                (chemical, type) -> type != AutomationType.EXTERNAL,
                (chemical, automationType) -> containsRecipeCAB(mainInputSlot.getStack(),
                        extraInputSlot.getStack(), chemical), this::containsRecipeC,
                ChemicalAttributeValidator.ALWAYS_ALLOW, recipeCacheListener));
        return builder.build();
    }

    @NotNull
    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener, IContentsListener recipeCacheListener,
                                                                IContentsListener unpause) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this);
        builder.addContainer(energyContainer = MachineEnergyContainer.input(this, listener));
        return builder.build();
    }

    @NotNull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener, IContentsListener recipeCacheListener,
                                                       IContentsListener unpause) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(mainInputSlot = InputInventorySlot.at(item -> containsRecipeABC(item, extraInputSlot.getStack(),
                                inputChemicalTank.getStack()), this::containsRecipeA, recipeCacheListener, 64, 17))
                .tracksWarnings(slot -> slot.warning(WarningType.NO_MATCHING_RECIPE,
                        getWarningCheck(NOT_ENOUGH_ITEM_INPUT_ERROR)));
        builder.addSlot(extraInputSlot = InputInventorySlot.at(item -> containsRecipeBAC(mainInputSlot.getStack(),
                        item, inputChemicalTank.getStack()), this::containsRecipeB, recipeCacheListener, 64, 53)
        ).tracksWarnings(slot -> slot.warning(WarningType.NO_MATCHING_RECIPE,
                getWarningCheck(RecipeError.NOT_ENOUGH_SECONDARY_INPUT)));
        builder.addSlot(outputSlot = OutputInventorySlot.at(listener, 116, 35))
                .tracksWarnings(slot -> slot.warning(WarningType.NO_SPACE_IN_OUTPUT,
                        getWarningCheck(NOT_ENOUGH_SPACE_ITEM_OUTPUT_ERROR)));
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 141, 35));
        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean sendUpdatePacket = super.onUpdateServer();
        energySlot.fillContainerOrConvert();
        recipeCacheLookupMonitor.updateAndProcess();
        return sendUpdatePacket;
    }

    @NotNull
    @Override
    public IMekanismRecipeTypeProvider<ItemItemChemicalRecipeInput, AlloyingRecipe,
                MSInputRecipeCache.ItemItemChemical<AlloyingRecipe>> getRecipeType() {
        return MSRecipeType.ALLOYING;
    }

    @Nullable
    @Override
    public AlloyingRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(itemInputHandler, extraInputHandler, chemicalInputHandler);
    }

    @NotNull
    @Override
    public CachedRecipe<AlloyingRecipe> createNewCachedRecipe(@NotNull AlloyingRecipe recipe, int cacheIndex) {
        return new AlloyerCachedRecipe(recipe, recheckAllRecipeErrors, itemInputHandler, extraInputHandler,
                chemicalInputHandler, outputHandler)
                .setErrorsChanged(this::onErrorsChanged)
                .setCanHolderFunction(this::canFunction)
                .setActive(this::setActive)
                .setEnergyRequirements(energyContainer::getEnergyPerTick, energyContainer)
                .setRequiredTicks(this::getTicksRequired)
                .setOnFinish(this::markForSave)
                .setOperatingTicksChanged(this::setOperatingTicks);
    }

    @ComputerMethod(methodDescription = ComputerConstants.DESCRIPTION_GET_ENERGY_USAGE)
    long getEnergyUsage() {
        return getActive() ? energyContainer.getEnergyPerTick() : 0;
    }


    /*@Override
    public @Nullable IRecipeViewerRecipeType<AlloyingRecipe> recipeViewerType() {
        return MSRecipeViewerRecipeTypes;
    }*/
}
