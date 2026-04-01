package com.hamburger0abcde.mekanismsun.common.tiles.transmitter;

import mekanism.common.integration.computer.BaseComputerHelper;
import mekanism.common.integration.computer.ComputerException;
import mekanism.common.integration.computer.ComputerMethodFactory;
import mekanism.common.integration.computer.MethodData;
import mekanism.common.integration.computer.annotation.MethodFactory;

@MethodFactory(target = TileEntityAdvanceUniversalCable.class)
public class TileEntityAdvanceUniversalCable$ComputerHandler extends ComputerMethodFactory<TileEntityAdvanceUniversalCable> {
    public TileEntityAdvanceUniversalCable$ComputerHandler() {
        register(MethodData.builder("getBuffer", TileEntityAdvanceUniversalCable$ComputerHandler::getBuffer_0)
                .returnType(long.class));
        register(MethodData.builder("getCapacity", TileEntityAdvanceUniversalCable$ComputerHandler::getCapacity_0)
                .returnType(long.class));
        register(MethodData.builder("getNeeded", TileEntityAdvanceUniversalCable$ComputerHandler::getNeeded_0)
                .returnType(long.class));
        register(MethodData.builder("getFilledPercentage", TileEntityAdvanceUniversalCable$ComputerHandler::getFilledPercentage_0)
                .returnType(double.class));
    }

    public static Object getBuffer_0(TileEntityAdvanceUniversalCable subject, BaseComputerHelper helper)
            throws ComputerException {
        return helper.convert(subject.getBuffer());
    }

    public static Object getCapacity_0(TileEntityAdvanceUniversalCable subject, BaseComputerHelper helper)
            throws ComputerException {
        return helper.convert(subject.getCapacity());
    }

    public static Object getNeeded_0(TileEntityAdvanceUniversalCable subject, BaseComputerHelper helper)
            throws ComputerException {
        return helper.convert(subject.getNeeded());
    }

    public static Object getFilledPercentage_0(TileEntityAdvanceUniversalCable subject,
                                               BaseComputerHelper helper) throws ComputerException {
        return helper.convert(subject.getFilledPercentage());
    }
}
