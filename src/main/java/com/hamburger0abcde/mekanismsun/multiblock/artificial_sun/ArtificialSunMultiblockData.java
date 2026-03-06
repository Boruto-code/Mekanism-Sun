package com.hamburger0abcde.mekanismsun.multiblock.artificial_sun;

import com.hamburger0abcde.mekanismsun.config.MSConfig;
import com.hamburger0abcde.mekanismsun.registries.MSChemicals;
import com.hamburger0abcde.mekanismsun.tile.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.tile.artificial_sun.TileEntityArtificialSunPort;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.SerializationConstants;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalHandler;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.radiation.IRadiationManager;
import mekanism.api.recipes.ChemicalToChemicalRecipe;
import mekanism.common.capabilities.chemical.VariableCapacityChemicalTank;
import mekanism.common.capabilities.energy.VariableCapacityEnergyContainer;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper.ComputerChemicalTankWrapper;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.integration.computer.annotation.SyntheticComputerMethod;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.container.sync.dynamic.ContainerSync;
import mekanism.common.lib.Color;
import mekanism.common.lib.multiblock.IValveHandler;
import mekanism.common.lib.multiblock.MultiblockData;
import mekanism.common.lib.radiation.RadiationManager;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.util.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.LongSupplier;

public class ArtificialSunMultiblockData extends MultiblockData {
    private final List<EnergyOutputTarget> energyOutputTargets = new ArrayList<>();
    private final List<CapabilityOutputTarget<IChemicalHandler>> chemicalOutputTargets = new ArrayList<>();

    @ContainerSync
    @WrappingComputerMethod(wrapper = ComputerChemicalTankWrapper.class,
            methodNames = {"getFuel", "getFuelCapacity", "getFuelNeeded", "getFuelFilledPercentage"}, docPlaceholder = "fuel tank")
    public IChemicalTank fuelTank;

    @ContainerSync
    @WrappingComputerMethod(wrapper = ComputerChemicalTankWrapper.class,
            methodNames = {"getWaste", "getWasteCapacity", "getWasteNeeded", "getWasteFilledPercentage"}, docPlaceholder = "waste tank")
    public IChemicalTank wasteTank;

    @ContainerSync
    public int progress;
    @ContainerSync
    public int defaultRecipeProgress = -1;

    @ContainerSync
    private boolean burning = false;

    @ContainerSync
    public IEnergyContainer energyContainer;

    public RecipeHolder<ChemicalToChemicalRecipe> currentRecipe;

    private float preFuelScale;
    private float preWasteScale;

    @ContainerSync
    @SyntheticComputerMethod(getter = "getActualBurnRate",
            getterDescription = "Actual burn rate as it may be lower if say there is not enough fuel")
    public double lastBurnRate = 0;
    private boolean clientBurning;
    @ContainerSync
    @SyntheticComputerMethod(getter = "getBurnRate", getterDescription = "Configured burn rate")
    public double rateLimit = MSConfig.GENERAL.sunDefaultBurnRate.get();
    public double burnRemaining = 0, partialWaste = 0;
    @ContainerSync
    private boolean active;
    @ContainerSync
    private boolean forceDisable;

    private long fuelCapacity;

    public ArtificialSunMultiblockData(TileEntityArtificialSunCasing tile) {
        super(tile);
        LongSupplier fuelCapacitySupplier = () -> fuelCapacity;
        fuelTank = VariableCapacityChemicalTank.input(this, fuelCapacitySupplier,
                chemical -> chemical.is(MekanismChemicals.HYDROGEN),
                ChemicalAttributeValidator.ALWAYS_ALLOW, createSaveAndComparator());
        wasteTank = VariableCapacityChemicalTank.output(this, fuelCapacitySupplier,
                chemical -> chemical.is(MSChemicals.HELIUM),
                ChemicalAttributeValidator.ALWAYS_ALLOW, this);
        energyContainers.add(energyContainer = VariableCapacityEnergyContainer
                .output(MSConfig.GENERAL.sunEnergyCapacity, this));
        Collections.addAll(chemicalTanks, fuelTank, wasteTank);
    }

    @Override
    public boolean tick(Level world) {
        boolean needsPacket = super.tick(world);

        if (isActive()) {
            burnFuel(world);
        } else {
            lastBurnRate = 0;
        }

        if (isBurning() != clientBurning) {
            needsPacket = true;
            clientBurning = isBurning();
        }

        if (!energyOutputTargets.isEmpty() && !energyContainer.isEmpty()) {
            CableUtils.emit(getActiveOutputs(energyOutputTargets), energyContainer);
        }
        if (!chemicalOutputTargets.isEmpty() && !wasteTank.isEmpty()) {
            ChemicalUtil.emit(getActiveOutputs(chemicalOutputTargets), wasteTank);
        }

        float fuelScale = MekanismUtils.getScale(preFuelScale, fuelTank);
        float wasteScale = MekanismUtils.getScale(preWasteScale, wasteTank);
        if (MekanismUtils.scaleChanged(fuelScale, preFuelScale) || MekanismUtils.scaleChanged(wasteScale, preWasteScale)) {
            needsPacket = true;
            preFuelScale = fuelScale;
            preWasteScale = wasteScale;
        }
        return needsPacket;
    }

    @Override
    protected void updateEjectors(Level world) {
        energyOutputTargets.clear();
        chemicalOutputTargets.clear();
        for (IValveHandler.ValveData valve : valves) {
            TileEntityArtificialSunPort tile = WorldUtils.getTileEntity(TileEntityArtificialSunPort.class, world, valve.location);
            if (tile != null) {
                tile.addEnergyTargetCapability(energyOutputTargets, valve.side);
                tile.addChemicalTargetCapability(chemicalOutputTargets, valve.side);
            }
        }
    }

    private void burnFuel(Level world) {
        double lastPartialWaste = partialWaste;
        double lastBurnRemaining = burnRemaining;
        double storedFuel = fuelTank.getStored() + burnRemaining;
        double toBurn = Math.min(rateLimit, storedFuel);
        storedFuel -= toBurn;
        fuelTank.setStackSize((long) storedFuel, Action.EXECUTE);
        burnRemaining = storedFuel % 1;
        partialWaste += toBurn;
        long newWaste = Mth.lfloor(partialWaste);
        if (newWaste > 0) {
            partialWaste %= 1;
            long leftoverWaste = Math.max(0, newWaste - wasteTank.getNeeded());
            ChemicalStack wasteToAdd = MSChemicals.HELIUM.asStack(newWaste - leftoverWaste);
            wasteTank.insert(wasteToAdd, Action.EXECUTE, AutomationType.INTERNAL);
            if (leftoverWaste > 0 && RadiationManager.isGlobalRadiationEnabled()) {
                double wasteRadioactivity = wasteToAdd.getChemical().getRadioactivity();
                if (wasteRadioactivity > 0) {
                    IRadiationManager.INSTANCE.radiate(world, getBounds().getCenter(), leftoverWaste * wasteRadioactivity);
                }
            }
        }

        lastBurnRate = toBurn;
        if (lastPartialWaste != partialWaste || lastBurnRemaining != burnRemaining) {
            markDirty();
        }
    }

    @ComputerMethod
    public boolean isForceDisabled() {
        return forceDisable;
    }

    void setForceDisable(boolean forceDisable) {
        if (this.forceDisable != forceDisable) {
            this.forceDisable = forceDisable;
            markDirty();
            if (this.forceDisable) {
                setActive(false);
            }
        }
    }

    @ComputerMethod(nameOverride = "getStatus", methodDescription = "true -> active, false -> off")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        if (this.active != active && (!active || !isForceDisabled())) {
            this.active = active;
            markDirty();
        }
    }

    public boolean isBurning() {
        return lastBurnRate > 0;
    }

    @Override
    public void writeUpdateTag(CompoundTag tag, HolderLookup.Provider provider) {
        super.writeUpdateTag(tag, provider);
        tag.putInt(SerializationConstants.PROGRESS, progress);
        tag.putInt("defaultRecipeProgress", defaultRecipeProgress);
        tag.putFloat(SerializationConstants.SCALE, preFuelScale);
        tag.put(SerializationConstants.CHEMICAL, fuelTank.getStack().saveOptional(provider));

    }

    @Override
    public void readUpdateTag(CompoundTag tag, HolderLookup.Provider provider) {
        super.readUpdateTag(tag,provider);
        NBTUtils.setFloatIfPresent(tag, SerializationConstants.SCALE, scale -> preFuelScale = scale);
        NBTUtils.setIntIfPresent(tag,SerializationConstants.PROGRESS, pg -> progress = pg);
        NBTUtils.setIntIfPresent(tag,"defaultRecipeProgress", pg -> defaultRecipeProgress = pg);
        NBTUtils.setChemicalStackIfPresent(provider,tag, SerializationConstants.CHEMICAL, value -> fuelTank.setStack(value));
    }

    @Override
    protected int getMultiblockRedstoneLevel() {
        return MekanismUtils.redstoneLevelFromContents(fuelTank.getStored(), fuelTank.getCapacity());
    }


    public boolean handlesSound(TileEntityArtificialSunCasing tile) {
        return tile.getBlockPos().equals(getMinPos().offset(3, 0, 0)) ||
                tile.getBlockPos().equals(getMaxPos().offset(-3, 0, 0));
    }

    public Color getColor(){
        return fuelTank.isEmpty() ? Color.WHITE : Color.rgb(fuelTank.getStack().getChemicalTint());
    }
}
