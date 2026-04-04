package com.hamburger0abcde.mekanismsun.common.tiles.multiblock.matrix;

import mekanism.common.integration.computer.BaseComputerHelper;
import mekanism.common.integration.computer.ComputerException;
import mekanism.common.integration.computer.ComputerMethodFactory;
import mekanism.common.integration.computer.MethodData;
import mekanism.common.integration.computer.annotation.MethodFactory;

@MethodFactory(target = TileEntityAdvanceInductionPort.class)
public class TileEntityAdvanceInductionPort$ComputerHandler extends ComputerMethodFactory<TileEntityAdvanceInductionPort> {
    private final String[] NAMES_output = new String[]{"output"};

    private final Class[] TYPES_3db6c47 = new Class[]{boolean.class};

    public TileEntityAdvanceInductionPort$ComputerHandler() {
        register(MethodData.builder("getMode", TileEntityAdvanceInductionPort$ComputerHandler::getMode_0)
                .returnType(boolean.class).methodDescription("true -> output, false -> input."));
        register(MethodData.builder("setMode", TileEntityAdvanceInductionPort$ComputerHandler::setMode_1)
                .methodDescription("true -> output, false -> input").arguments(NAMES_output, TYPES_3db6c47));
    }

    public static Object getMode_0(TileEntityAdvanceInductionPort subject, BaseComputerHelper helper) throws ComputerException {
        return helper.convert(subject.getMode());
    }

    public static Object setMode_1(TileEntityAdvanceInductionPort subject, BaseComputerHelper helper) throws ComputerException {
        subject.setMode(helper.getBoolean(0));
        return helper.voidResult();
    }
}
