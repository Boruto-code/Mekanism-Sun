package com.hamburger0abcde.mekanismsun.common.tiles.transmitter;

import mekanism.api.chemical.ChemicalStack;
import mekanism.common.integration.computer.BaseComputerHelper;
import mekanism.common.integration.computer.ComputerException;
import mekanism.common.integration.computer.ComputerMethodFactory;
import mekanism.common.integration.computer.MethodData;
import mekanism.common.integration.computer.annotation.MethodFactory;

@MethodFactory(target = TileEntityAdvancePressurizedTube.class)
public class TileEntityAdvancePressurizedTube$ComputerHandler extends ComputerMethodFactory<TileEntityAdvancePressurizedTube> {
    public TileEntityAdvancePressurizedTube$ComputerHandler() {
        register(MethodData.builder("getBuffer", TileEntityAdvancePressurizedTube$ComputerHandler::getBuffer_0)
                .returnType(ChemicalStack.class));
        register(MethodData.builder("getCapacity", TileEntityAdvancePressurizedTube$ComputerHandler::getCapacity_0)
                .returnType(long.class));
        register(MethodData.builder("getNeeded", TileEntityAdvancePressurizedTube$ComputerHandler::getNeeded_0)
                .returnType(long.class));
        register(MethodData.builder("getFilledPercentage", TileEntityAdvancePressurizedTube$ComputerHandler::getFilledPercentage_0)
                .returnType(double.class));
    }

    public static Object getBuffer_0(TileEntityAdvancePressurizedTube subject, BaseComputerHelper helper)
            throws ComputerException {
        return helper.convert(subject.getBuffer());
    }

    public static Object getCapacity_0(TileEntityAdvancePressurizedTube subject, BaseComputerHelper helper)
            throws ComputerException {
        return helper.convert(subject.getCapacity());
    }

    public static Object getNeeded_0(TileEntityAdvancePressurizedTube subject, BaseComputerHelper helper)
            throws ComputerException {
        return helper.convert(subject.getNeeded());
    }

    public static Object getFilledPercentage_0(TileEntityAdvancePressurizedTube subject,
                                               BaseComputerHelper helper) throws ComputerException {
        return helper.convert(subject.getFilledPercentage());
    }
}
