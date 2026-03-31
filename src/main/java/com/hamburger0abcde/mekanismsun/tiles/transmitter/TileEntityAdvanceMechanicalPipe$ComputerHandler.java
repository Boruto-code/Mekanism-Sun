package com.hamburger0abcde.mekanismsun.tiles.transmitter;

import mekanism.common.integration.computer.BaseComputerHelper;
import mekanism.common.integration.computer.ComputerException;
import mekanism.common.integration.computer.ComputerMethodFactory;
import mekanism.common.integration.computer.MethodData;
import mekanism.common.integration.computer.annotation.MethodFactory;
import net.neoforged.neoforge.fluids.FluidStack;

@MethodFactory(target = TileEntityAdvanceMechanicalPipe.class)
public class TileEntityAdvanceMechanicalPipe$ComputerHandler extends ComputerMethodFactory<TileEntityAdvanceMechanicalPipe> {
    public TileEntityAdvanceMechanicalPipe$ComputerHandler() {
        register(MethodData.builder("getBuffer", TileEntityAdvanceMechanicalPipe$ComputerHandler::getBuffer_0)
                .returnType(FluidStack.class));
        register(MethodData.builder("getCapacity", TileEntityAdvanceMechanicalPipe$ComputerHandler::getCapacity_0)
                .returnType(long.class));
        register(MethodData.builder("getNeeded", TileEntityAdvanceMechanicalPipe$ComputerHandler::getNeeded_0)
                .returnType(long.class));
        register(MethodData.builder("getFilledPercentage", TileEntityAdvanceMechanicalPipe$ComputerHandler::getFilledPercentage_0)
                .returnType(double.class));
    }

    public static Object getBuffer_0(TileEntityAdvanceMechanicalPipe subject, BaseComputerHelper helper)
            throws ComputerException {
        return helper.convert(subject.getBuffer());
    }

    public static Object getCapacity_0(TileEntityAdvanceMechanicalPipe subject, BaseComputerHelper helper)
            throws ComputerException {
        return helper.convert(subject.getCapacity());
    }

    public static Object getNeeded_0(TileEntityAdvanceMechanicalPipe subject, BaseComputerHelper helper)
            throws ComputerException {
        return helper.convert(subject.getNeeded());
    }

    public static Object getFilledPercentage_0(TileEntityAdvanceMechanicalPipe subject,
                                               BaseComputerHelper helper) throws ComputerException {
        return helper.convert(subject.getFilledPercentage());
    }
}
