package com.hamburger0abcde.mekanismsun.common.multiblock.matrix;

import mekanism.common.integration.computer.*;
import mekanism.common.integration.computer.annotation.MethodFactory;
import net.minecraft.world.item.ItemStack;

@MethodFactory(target = AdvanceMatrixMultiblockData.class)
public class AdvanceMatrixMultiblockData$ComputerHandler extends ComputerMethodFactory<AdvanceMatrixMultiblockData> {
    public AdvanceMatrixMultiblockData$ComputerHandler() {
        register(MethodData.builder("getInputItem", AdvanceMatrixMultiblockData$ComputerHandler::energyInputSlot$getInputItem)
                .returnType(ItemStack.class).methodDescription("Get the contents of the input slot."));
        register(MethodData.builder("getOutputItem", AdvanceMatrixMultiblockData$ComputerHandler::energyOutputSlot$getOutputItem)
                .returnType(ItemStack.class).methodDescription("Get the contents of the output slot."));
        register(MethodData.builder("getTransferCap", AdvanceMatrixMultiblockData$ComputerHandler::getTransferCap_0)
                .returnType(long.class));
        register(MethodData.builder("getLastInput", AdvanceMatrixMultiblockData$ComputerHandler::getLastInput_0)
                .returnType(long.class));
        register(MethodData.builder("getLastOutput", AdvanceMatrixMultiblockData$ComputerHandler::getLastOutput_0)
                .returnType(long.class));
        register(MethodData.builder("getInstalledCells", AdvanceMatrixMultiblockData$ComputerHandler::getInstalledCells_0)
                .returnType(int.class));
        register(MethodData.builder("getInstalledProviders", AdvanceMatrixMultiblockData$ComputerHandler::getInstalledProviders_0)
                .returnType(int.class));
    }

    public static Object energyInputSlot$getInputItem(AdvanceMatrixMultiblockData subject,
                                                      BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.getStack(subject.energyInputSlot));
    }

    public static Object energyOutputSlot$getOutputItem(AdvanceMatrixMultiblockData subject,
                                                        BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.getStack(subject.energyOutputSlot));
    }

    public static Object getTransferCap_0(AdvanceMatrixMultiblockData subject, BaseComputerHelper helper)
            throws ComputerException {
        return helper.convert(subject.getTransferCap());
    }

    public static Object getLastInput_0(AdvanceMatrixMultiblockData subject, BaseComputerHelper helper)
            throws ComputerException {
        return helper.convert(subject.getLastInput());
    }

    public static Object getLastOutput_0(AdvanceMatrixMultiblockData subject, BaseComputerHelper helper)
            throws ComputerException {
        return helper.convert(subject.getLastOutput());
    }

    public static Object getInstalledCells_0(AdvanceMatrixMultiblockData subject, BaseComputerHelper helper)
            throws ComputerException {
        return helper.convert(subject.getCellCount());
    }

    public static Object getInstalledProviders_0(AdvanceMatrixMultiblockData subject,
                                                 BaseComputerHelper helper) throws ComputerException {
        return helper.convert(subject.getProviderCount());
    }
}
