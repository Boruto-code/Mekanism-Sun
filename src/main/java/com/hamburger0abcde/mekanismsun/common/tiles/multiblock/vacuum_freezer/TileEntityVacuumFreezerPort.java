package com.hamburger0abcde.mekanismsun.common.tiles.multiblock.vacuum_freezer;

import com.hamburger0abcde.mekanismsun.common.MekanismSunLang;
import com.hamburger0abcde.mekanismsun.common.multiblock.vacuum_freezer.VacuumFreezerMultiblockData;
import com.hamburger0abcde.mekanismsun.common.registries.MSBlocks;
import mekanism.api.Action;
import mekanism.api.IContentsListener;
import mekanism.api.text.EnumColor;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.lib.multiblock.MultiblockData.CapabilityOutputTarget;
import mekanism.common.util.text.BooleanStateDisplay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TileEntityVacuumFreezerPort extends TileEntityVacuumFreezerCasing {
    private final Map<Direction, BlockCapabilityCache<IFluidHandler, @Nullable Direction>> fluidCapabilityCaches =
            new EnumMap<>(Direction.class);
    public MachineEnergyContainer<TileEntityVacuumFreezerPort> energyContainer;

    public TileEntityVacuumFreezerPort(BlockPos pos, BlockState state) {
        super(MSBlocks.VACUUM_FREEZER_PORT, pos, state);
        delaySupplier = NO_DELAY;
    }

    @Override
    protected boolean onUpdateServer(VacuumFreezerMultiblockData multiblock) {
        boolean needsPacket = super.onUpdateServer(multiblock);
        if (multiblock.isFormed()) {
            if (!energyContainer.isEmpty() && multiblock.canOperate()) {
                multiblock.receiveEnergy(energyContainer.getEnergy());
            }
        }
        return needsPacket;
    }

    @NotNull
    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSide(facingSupplier);
        builder.addContainer(energyContainer = MachineEnergyContainer.input(this, listener));
        return builder.build();
    }

    @Override
    public @Nullable IFluidTankHolder getInitialFluidTanks(IContentsListener listener) {
        return side -> getMultiblock().getFluidTanks(side);
    }

    public void addFluidTargetCapability(List<CapabilityOutputTarget<IFluidHandler>> outputTargets, Direction side) {
        BlockCapabilityCache<IFluidHandler, @Nullable Direction> cache = fluidCapabilityCaches.get(side);
        if (cache == null) {
            cache = Capabilities.FLUID.createCache((ServerLevel) level, worldPosition.relative(side), side.getOpposite());
            fluidCapabilityCaches.put(side, cache);
        }
        outputTargets.add(new CapabilityOutputTarget<>(cache, this::getActive));
    }

    @Override
    public InteractionResult onSneakRightClick(Player player) {
        if (!isRemote()) {
            boolean oldMode = getActive();
            setActive(!oldMode);
            player.displayClientMessage(MekanismSunLang.VACUUM_FREEZER_PORT_MODE
                    .translateColored(EnumColor.GRAY, BooleanStateDisplay.InputOutput.of(oldMode, true)), true);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public int getRedstoneLevel() {
        return getMultiblock().getCurrentRedstoneLevel();
    }

    @ComputerMethod(methodDescription = "true -> output, false -> input.")
    boolean getMode() {
        return getActive();
    }

    @ComputerMethod(methodDescription = "true -> output, false -> input.")
    void setMode(boolean output) {
        setActive(output);
    }
}
