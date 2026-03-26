package com.hamburger0abcde.mekanismsun.tiles.storage;

import mekanism.api.chemical.ChemicalStack;
import mekanism.common.integration.computer.*;
import mekanism.common.integration.computer.annotation.MethodFactory;
import mekanism.common.tile.TileEntityChemicalTank;
import net.minecraft.world.item.ItemStack;

@MethodFactory(target = TileEntityAdvanceChemicalTank.class)
public class TileEntityAdvanceChemicalTank$ComputerHandler extends ComputerMethodFactory<TileEntityAdvanceChemicalTank> {
    private final String[] NAMES_mode = new String[]{"mode"};

    private final Class[] TYPES_ef806282 = new Class[]{TileEntityChemicalTank.GasMode.class};

    public TileEntityAdvanceChemicalTank$ComputerHandler() {
        register(MethodData.builder("getDumpingMode", TileEntityAdvanceChemicalTank$ComputerHandler::getDumpingMode_0)
                .returnType(TileEntityChemicalTank.GasMode.class).methodDescription("Get the current Dumping configuration"));
        register(MethodData.builder("getDrainItem", TileEntityAdvanceChemicalTank$ComputerHandler::drainSlot$getDrainItem)
                .returnType(ItemStack.class).methodDescription("Get the contents of the drain slot."));
        register(MethodData.builder("getFillItem", TileEntityAdvanceChemicalTank$ComputerHandler::fillSlot$getFillItem)
                .returnType(ItemStack.class).methodDescription("Get the contents of the fill slot."));
        register(MethodData.builder("getStored", TileEntityAdvanceChemicalTank$ComputerHandler::getCurrentTank$getStored)
                .returnType(ChemicalStack.class).methodDescription("Get the contents of the tank."));
        register(MethodData.builder("getCapacity", TileEntityAdvanceChemicalTank$ComputerHandler::getCurrentTank$getCapacity)
                .returnType(long.class).methodDescription("Get the capacity of the tank."));
        register(MethodData.builder("getNeeded", TileEntityAdvanceChemicalTank$ComputerHandler::getCurrentTank$getNeeded)
                .returnType(long.class).methodDescription("Get the amount needed to fill the tank."));
        register(MethodData.builder("getFilledPercentage",
                TileEntityAdvanceChemicalTank$ComputerHandler::getCurrentTank$getFilledPercentage)
                .returnType(double.class).methodDescription("Get the filled percentage of the tank."));
        register(MethodData.builder("setDumpingMode", TileEntityAdvanceChemicalTank$ComputerHandler::setDumpingMode_1)
                .methodDescription("Set the Dumping mode of the tank").requiresPublicSecurity().arguments(NAMES_mode, TYPES_ef806282));
        register(MethodData.builder("incrementDumpingMode", TileEntityAdvanceChemicalTank$ComputerHandler::incrementDumpingMode_0)
                .methodDescription("Advance the Dumping mode to the next configuration in the list").requiresPublicSecurity());
        register(MethodData.builder("decrementDumpingMode", TileEntityAdvanceChemicalTank$ComputerHandler::decrementDumpingMode_0)
                .methodDescription("Descend the Dumping mode to the previous configuration in the list").requiresPublicSecurity());
    }

    public static Object getDumpingMode_0(TileEntityAdvanceChemicalTank subject, BaseComputerHelper helper)
            throws ComputerException {
        return helper.convert(subject.dumping);
    }

    public static Object drainSlot$getDrainItem(TileEntityAdvanceChemicalTank subject,
                                                BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.getStack(subject.drainSlot));
    }

    public static Object fillSlot$getFillItem(TileEntityAdvanceChemicalTank subject,
                                              BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.getStack(subject.fillSlot));
    }

    public static Object getCurrentTank$getStored(TileEntityAdvanceChemicalTank subject,
                                                  BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.getStack(subject.getCurrentTank()));
    }

    public static Object getCurrentTank$getCapacity(TileEntityAdvanceChemicalTank subject,
                                                    BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.getCapacity(subject.getCurrentTank()));
    }

    public static Object getCurrentTank$getNeeded(TileEntityAdvanceChemicalTank subject,
                                                  BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.getNeeded(subject.getCurrentTank()));
    }

    public static Object getCurrentTank$getFilledPercentage(TileEntityAdvanceChemicalTank subject,
                                                            BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.getFilledPercentage(subject.getCurrentTank()));
    }

    public static Object setDumpingMode_1(TileEntityAdvanceChemicalTank subject, BaseComputerHelper helper)
            throws ComputerException {
        subject.setDumpingMode(helper.getEnum(0, TileEntityChemicalTank.GasMode.class));
        return helper.voidResult();
    }

    public static Object incrementDumpingMode_0(TileEntityAdvanceChemicalTank subject,
                                                BaseComputerHelper helper) throws ComputerException {
        subject.incrementDumpingMode();
        return helper.voidResult();
    }

    public static Object decrementDumpingMode_0(TileEntityAdvanceChemicalTank subject,
                                                BaseComputerHelper helper) throws ComputerException {
        subject.decrementDumpingMode();
        return helper.voidResult();
    }
}
