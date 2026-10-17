package com.hamburger0abcde.mekanismsun.common.multiblock.vacuum_freezer;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.common.config.MSConfig;
import com.hamburger0abcde.mekanismsun.common.recipes.MSRecipeType;
import com.hamburger0abcde.mekanismsun.common.recipes.freeze.FreezeRecipe;
import com.hamburger0abcde.mekanismsun.common.tiles.multiblock.vacuum_freezer.TileEntityVacuumFreezerCasing;
import com.hamburger0abcde.mekanismsun.common.tiles.multiblock.vacuum_freezer.TileEntityVacuumFreezerPort;
import lombok.Getter;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.SerializationConstants;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.math.MathUtils;
import mekanism.common.capabilities.fluid.VariableCapacityFluidTank;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper.ComputerFluidTankWrapper;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.container.sync.dynamic.ContainerSync;
import mekanism.common.lib.Color;
import mekanism.common.lib.multiblock.IValveHandler;
import mekanism.common.lib.multiblock.MultiblockData;
import mekanism.common.util.FluidUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.NBTUtils;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class VacuumFreezerMultiblockData extends MultiblockData {
    public static final long MAX_INPUT_ENERGY = 8_000_000_000L;

    public static final long ENERGY_PER_PARALLEL = 2_500_000L;

    @Getter
    @ContainerSync
    public long receivedEnergy = 0;

    @Getter
    @ContainerSync
    public long lastReceivedEnergy = 0;

    private final List<CapabilityOutputTarget<IFluidHandler>> fluidOutputTargets = new ArrayList<>();

    @ContainerSync
    @WrappingComputerMethod(
            wrapper = ComputerFluidTankWrapper.class,
            methodNames = {"getInput", "getInputCapacity", "getInputNeeded", "getInputFilledPercentage"},
            docPlaceholder = "input tank"
    )
    public IExtendedFluidTank inputTank;

    @ContainerSync
    @WrappingComputerMethod(
            wrapper = ComputerFluidTankWrapper.class,
            methodNames = {"getOutput", "getOutputCapacity", "getOutputNeeded", "getOutputFilledPercentage"},
            docPlaceholder = "output tank"
    )
    public IExtendedFluidTank outputTank;

    @ContainerSync
    public int progress;

    @ContainerSync
    public int defaultRecipeProgress = -1;

    public int lastProgress;

    public RecipeHolder<FreezeRecipe> currentRecipe;

    @Getter
    @ContainerSync
    public long parallel;

    public long lastParallel;

    public float prevFluidScale;

    public VacuumFreezerMultiblockData(TileEntityVacuumFreezerCasing tile) {
        super(tile);
        fluidTanks.add(inputTank = VariableCapacityFluidTank.input(this, MSConfig.GENERAL.freezerInputCapacity::get,
                this::hasRecipeWith, createSaveAndComparator()));
        fluidTanks.add(outputTank = VariableCapacityFluidTank.output(MSConfig.GENERAL.freezerOutputCapacity.get(), this));
    }

    private boolean hasRecipeWith(FluidStack fluid) {
        return getLevel().getRecipeManager().getAllRecipesFor(MSRecipeType.FREEZE.getRecipeType())
                .stream().anyMatch(r -> r.value().getInput().testType(fluid));
    }

    @Override
    public boolean tick(Level world) {
        boolean needsPacket = super.tick(world);

        if (world.isClientSide)
            return false;

        boolean couldOperate = isFormed() && !inputTank.isEmpty() && receivedEnergy > 0;

        if (currentRecipe == null) {
            world.getRecipeManager()
                    .getAllRecipesFor(MSRecipeType.FREEZE.getRecipeType())
                    .stream()
                    .filter(r -> r.value().test(inputTank.getFluid()))
                    .findFirst()
                    .ifPresent(r -> {
                        currentRecipe = r;
                        progress = (MSConfig.GENERAL.freezerDefaultDuration.getOrDefault());
                        defaultRecipeProgress = progress;
                        markDirty();
                    });

        } else {
            if (!currentRecipe.value().test(inputTank.getFluid())) {
                currentRecipe = null;
                progress = 0;
                defaultRecipeProgress = -1;
                parallel = 0;
                markDirty();
                return true;
            }
        }

        if (couldOperate && currentRecipe != null) {
            parallel = Math.min(receivedEnergy / ENERGY_PER_PARALLEL, getMaxParallel());

            if (parallel > 0) {
                if (canProcess()) {
                    progress--;

                    if (progress <= 0) {
                        process();
                        currentRecipe = null;
                        progress = 0;
                        defaultRecipeProgress = -1;
                    }

                    needsPacket = true;
                }
            }
        }

        if (!fluidOutputTargets.isEmpty() && !outputTank.isEmpty()) {
            FluidUtils.emit(getActiveOutputs(fluidOutputTargets), outputTank);
            //emitOutput();
        }

        if (lastProgress != progress) {
            lastProgress = progress;
            markDirty();
            needsPacket = true;
        }

        if (lastParallel != parallel) {
            lastParallel = parallel;
            needsPacket = true;
        }

        if (lastReceivedEnergy != receivedEnergy) {
            needsPacket = true;
        }

        float fluidScale = MekanismUtils.getScale(prevFluidScale, inputTank);

        if (MekanismUtils.scaleChanged(fluidScale, prevFluidScale)) {
            prevFluidScale = fluidScale;
            needsPacket = true;
        }

        lastReceivedEnergy = receivedEnergy;
        receivedEnergy = 0;

        return needsPacket;
    }

    private boolean canProcess() {
        if (currentRecipe == null)
            return false;

        long inputAmount = currentRecipe.value().getInput().getNeededAmount(inputTank.getFluid());
        long requiredInput = inputAmount * parallel;

        if (inputTank.getFluidAmount() < requiredInput) {
            parallel = inputTank.getFluidAmount() / inputAmount;
        }

        FluidStack output = currentRecipe.value().getOutput(inputTank.getFluid());
        long outputAmount = (long) output.getAmount() * parallel;

        if (!outputTank.isEmpty() && !FluidStack.isSameFluidSameComponents(outputTank.getFluid(), output)) {
            return false;
        }
        if (outputTank.getFluidAmount() + outputAmount > outputTank.getCapacity()) {
            parallel = outputAmount / output.getAmount();
        }

        return true;
    }

    private void process() {
        if (currentRecipe == null || parallel <= 0) {
            return;
        }

        long inputAmount = currentRecipe.value().getInput().getNeededAmount(inputTank.getFluid());
        long totalInput = inputAmount * parallel;

        FluidStack output = currentRecipe.value().getOutput(inputTank.getFluid());
        output.setAmount(MathUtils.clampToInt((long) output.getAmount() * parallel));
        inputTank.extract(MathUtils.clampToInt(totalInput), Action.EXECUTE, AutomationType.INTERNAL);
        outputTank.insert(output, Action.EXECUTE, AutomationType.INTERNAL);

        markDirty();
    }

    public long getMaxParallel() {
        return MAX_INPUT_ENERGY / ENERGY_PER_PARALLEL;
    }

    public void receiveEnergy(long energy) {
        receivedEnergy = MathUtils.addClamped(receivedEnergy, energy);
    }

    /*private void emitOutput() {
        if (outputTank.isEmpty())
            return;

        FluidStack stack = outputTank.getFluid();
        int remaining = stack.getAmount();

        for (BlockCapabilityCache<IFluidHandler, @Nullable Direction> target : fluidOutputTargets) {
            if (remaining <= 0)
                break;

            IFluidHandler handler = target.getCapability();

            if (handler == null)
                continue;

            FluidStack output = stack.copy();
            output.setAmount(remaining);

            int accepted = handler.fill(output, IFluidHandler.FluidAction.EXECUTE);

            if (accepted > 0) {
                outputTank.extract(accepted, Action.EXECUTE, AutomationType.INTERNAL);
                remaining -= accepted;
            }
        }
    }*/

    @Override
    protected void updateEjectors(Level world) {
        fluidOutputTargets.clear();

        for (IValveHandler.ValveData valve : valves) {
            TileEntityVacuumFreezerPort tile = WorldUtils.getTileEntity(TileEntityVacuumFreezerPort.class, world, valve.location);

            if (tile != null) {
                tile.addFluidTargetCapability(fluidOutputTargets, valve.side);
            }
        }
    }


    @Override
    public void writeUpdateTag(CompoundTag tag, HolderLookup.Provider provider) {
        super.writeUpdateTag(tag, provider);
        tag.putLong("lastReceivedEnergy", lastReceivedEnergy);
        tag.putLong("parallel", parallel);
        tag.putInt(SerializationConstants.PROGRESS, progress);
        tag.putInt("defaultRecipeProgress", defaultRecipeProgress);
        tag.putFloat(SerializationConstants.SCALE, prevFluidScale);
        tag.put(SerializationConstants.FLUID, inputTank.getFluid().saveOptional(provider));
    }

    @Override
    public void readUpdateTag(CompoundTag tag, HolderLookup.Provider provider) {
        super.readUpdateTag(tag, provider);
        NBTUtils.setLongIfPresent(tag, "lastReceivedEnergy", value -> lastReceivedEnergy = value);
        NBTUtils.setLongIfPresent(tag, "parallel", value -> parallel = value);
        NBTUtils.setFloatIfPresent(tag, SerializationConstants.SCALE, scale -> prevFluidScale = scale);
        NBTUtils.setIntIfPresent(tag, SerializationConstants.PROGRESS, pg -> progress = pg);
        NBTUtils.setIntIfPresent(tag, "defaultRecipeProgress", pg -> defaultRecipeProgress = pg);
        NBTUtils.setFluidStackIfPresent(provider, tag, SerializationConstants.FLUID, inputTank::setStackUnchecked);
    }

    @Override
    protected int getMultiblockRedstoneLevel() {
        return MekanismUtils.redstoneLevelFromContents(inputTank.getFluidAmount(), inputTank.getCapacity());
    }

    public boolean canOperate() {
        return !inputTank.isEmpty() && outputTank.getNeeded() > 0;
    }

    public boolean handlesSound(TileEntityVacuumFreezerCasing tile) {
        return tile.getBlockPos().equals(getMinPos().offset(3, 0, 0)) ||
                tile.getBlockPos().equals(getMaxPos().offset(-3, 0, 0));
    }

    public double getScaledProgress() {
        return defaultRecipeProgress == -1 ? 0 : (double) (defaultRecipeProgress - progress) / defaultRecipeProgress;
    }

    public Color getColor() {
        return inputTank.isEmpty() ? Color.WHITE : Color.rgb(0x5D7391);
    }
}