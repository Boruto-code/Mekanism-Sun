package com.hamburger0abcde.mekanismsun.tiles.storage;

import mekanism.common.integration.computer.*;
import mekanism.common.integration.computer.annotation.MethodFactory;
import net.minecraft.world.item.ItemStack;

@MethodFactory(target = TileEntityAdvanceEnergyCube.class)
public class TileEntityAdvanceEnergyCube$ComputerHandler extends ComputerMethodFactory<TileEntityAdvanceEnergyCube> {
    public TileEntityAdvanceEnergyCube$ComputerHandler() {
        register(MethodData.builder("getChargeItem", TileEntityAdvanceEnergyCube$ComputerHandler::chargeSlot$getChargeItem)
                .returnType(ItemStack.class).methodDescription("Get the contents of the charge slot."));
        register(MethodData.builder("getDischargeItem", TileEntityAdvanceEnergyCube$ComputerHandler::dischargeSlot$getDischargeItem)
                .returnType(ItemStack.class).methodDescription("Get the contents of the discharge slot."));
    }

    public static Object chargeSlot$getChargeItem(TileEntityAdvanceEnergyCube subject,
                                                  BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.getStack(subject.chargeSlot));
    }

    public static Object dischargeSlot$getDischargeItem(TileEntityAdvanceEnergyCube subject,
                                                        BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.getStack(subject.dischargeSlot));
    }
}
