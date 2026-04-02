package com.hamburger0abcde.mekanismsun.common.tiles.multiblock.artificial_sun;

import com.hamburger0abcde.mekanismsun.common.MekanismSunLang;
import com.hamburger0abcde.mekanismsun.common.registries.MSBlocks;
import mekanism.api.IContentsListener;
import mekanism.api.chemical.IChemicalHandler;
import mekanism.api.text.EnumColor;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.integration.energy.BlockEnergyCapabilityCache;
import mekanism.common.lib.multiblock.MultiblockData.EnergyOutputTarget;
import mekanism.common.lib.multiblock.MultiblockData.CapabilityOutputTarget;
import mekanism.common.util.text.BooleanStateDisplay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TileEntityArtificialSunPort extends TileEntityArtificialSunCasing {
    private final Map<Direction, BlockCapabilityCache<IChemicalHandler, @Nullable Direction>> chemicalCapabilityCaches =
            new EnumMap<>(Direction.class);
    private final Map<Direction, BlockEnergyCapabilityCache> energyCapabilityCaches =
            new EnumMap<>(Direction.class);

    public TileEntityArtificialSunPort(BlockPos pos, BlockState state) {
        super(MSBlocks.ARTIFICIAL_SUN_PORT, pos, state);
        delaySupplier = NO_DELAY;
    }

    @NotNull
    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener) {
        return side -> getMultiblock().getEnergyContainers(side);
    }

    @Override
    public @Nullable IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener) {
        return side -> getMultiblock().getChemicalTanks(side);
    }

    @Override
    public boolean persists(ContainerType<?, ?, ?> type) {
        if (type == ContainerType.CHEMICAL || type == ContainerType.ENERGY) {
            return false;
        }
        return super.persists(type);
    }

    public void addChemicalTargetCapability(List<CapabilityOutputTarget<IChemicalHandler>> outputTargets, Direction side) {
        BlockCapabilityCache<IChemicalHandler, @Nullable Direction> cache = chemicalCapabilityCaches.get(side);
        if (cache == null) {
            cache = Capabilities.CHEMICAL.createCache((ServerLevel) level, worldPosition.relative(side), side.getOpposite());
            chemicalCapabilityCaches.put(side, cache);
        }
        outputTargets.add(new CapabilityOutputTarget<>(cache, this::getActive));
    }

    public void addEnergyTargetCapability(List<EnergyOutputTarget> outputTargets, Direction side) {
        BlockEnergyCapabilityCache cache = energyCapabilityCaches.get(side);
        if (cache == null) {
            cache = BlockEnergyCapabilityCache.create((ServerLevel) level, worldPosition.relative(side), side.getOpposite());
            energyCapabilityCaches.put(side, cache);
        }
        outputTargets.add(new EnergyOutputTarget(cache, this::getActive));
    }

    @Override
    public InteractionResult onSneakRightClick(Player player) {
        if (!isRemote()) {
            boolean oldMode = getActive();
            setActive(!oldMode);
            player.displayClientMessage(MekanismSunLang.ARTIFICIAL_SUN_PORT_MODE.translateColored(EnumColor.GRAY,
                    BooleanStateDisplay.InputOutput.of(oldMode, true)), true);
        }
        return InteractionResult.SUCCESS;
    }

    @ComputerMethod(methodDescription = "true -> output, false -> input")
    boolean getMode() {
        return getActive();
    }

    @ComputerMethod(methodDescription = "true -> output, false -> input")
    void setMode(boolean output) {
        setActive(output);
    }
}
