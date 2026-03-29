package com.hamburger0abcde.mekanismsun.upgrade;

import com.hamburger0abcde.mekanismsun.inventory.slot.AdvanceBinInventorySlot;
import mekanism.common.upgrade.IUpgradeData;

public record MSBinUpgradeData(boolean redstone, AdvanceBinInventorySlot binSlot) implements IUpgradeData {
}
