package com.hamburger0abcde.mekanismsun.multiblock.artificial_sun;

import com.hamburger0abcde.mekanismsun.config.MSConfig;
import com.hamburger0abcde.mekanismsun.registries.MSChemicals;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunPort;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.SerializationConstants;
import mekanism.api.chemical.IChemicalHandler;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.ChemicalToChemicalRecipe;
import mekanism.common.capabilities.chemical.VariableCapacityChemicalTank;
import mekanism.common.capabilities.energy.VariableCapacityEnergyContainer;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper.ComputerChemicalTankWrapper;
import mekanism.common.integration.computer.annotation.SyntheticComputerMethod;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.container.sync.dynamic.ContainerSync;
import mekanism.common.lib.Color;
import mekanism.common.lib.multiblock.IValveHandler;
import mekanism.common.lib.multiblock.MultiblockData;
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
    public long lastBurnRate = 0;
    @ContainerSync
    @SyntheticComputerMethod(getter = "getBurnRate", getterDescription = "Configured burn rate")
    public double rateLimit = MSConfig.GENERAL.sunDefaultBurnRate.get();
    public double burnRemaining = 0, partialWaste = 0;

    public ArtificialSunMultiblockData(TileEntityArtificialSunCasing tile) {
        super(tile);
        fuelTank = VariableCapacityChemicalTank.input(this, MSConfig.GENERAL.sunFuelCapacity,
                chemical -> chemical.is(MekanismChemicals.HYDROGEN),
                ChemicalAttributeValidator.ALWAYS_ALLOW, createSaveAndComparator());
        wasteTank = VariableCapacityChemicalTank.output(this, MSConfig.GENERAL.sunFuelCapacity,
                chemical -> chemical.is(MSChemicals.HELIUM),
                ChemicalAttributeValidator.ALWAYS_ALLOW, this);
        energyContainers.add(energyContainer = VariableCapacityEnergyContainer
                .output(MSConfig.GENERAL.sunEnergyCapacity, this));
        Collections.addAll(chemicalTanks, fuelTank, wasteTank);
    }

    @Override
    public boolean tick(Level world) {
        boolean needsPacket = super.tick(world);

        long stored = fuelTank.getStored();
        if (lastBurnRate == 0) {
            lastBurnRate = MathUtils.clampToLong(Math.min(Math.max(stored, lastBurnRate), rateLimit));
        }

        long energyNeeded = energyContainer.getNeeded();
        if (stored > 0 && energyNeeded > 0L) {
            long energyPerHydrogen = MSConfig.GENERAL.energyPerHydrogen.get();
            energyContainer.insert(MathUtils.clampToLong(Math.min(stored, lastBurnRate) * energyPerHydrogen),
                    Action.EXECUTE, AutomationType.INTERNAL);
            //MekanismSun.LOGGER.info("Energy: {}", MathUtils.clampToLong(Math.min(stored, lastBurnRate) * energyPerHydrogen));
            fuelTank.shrinkStack(lastBurnRate, Action.EXECUTE);
            wasteTank.insert(MSChemicals.HELIUM.asStack(1), Action.EXECUTE, AutomationType.INTERNAL);
        }

        if (!chemicalOutputTargets.isEmpty() && !wasteTank.isEmpty()) {
            ChemicalUtil.emit(getActiveOutputs(chemicalOutputTargets), wasteTank);
        }
        if (!energyOutputTargets.isEmpty() && !energyContainer.isEmpty()) {
            CableUtils.emit(getActiveOutputs(energyOutputTargets), energyContainer);
        }

        float scale = MekanismUtils.getScale(preFuelScale, fuelTank);
        if (MekanismUtils.scaleChanged(scale, preFuelScale)) {
            needsPacket = true;
            preFuelScale = scale;
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

    public void setRateLimit(double rate) {
        rate = Mth.clamp(rate, 0, MSConfig.GENERAL.sunMaxBurnRate.get());
        if (rateLimit != rate) {
            rateLimit = rate;
            markDirty();
        }
    }
}
