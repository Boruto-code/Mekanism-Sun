package com.hamburger0abcde.mekanismsun.common.upgrade;

import com.hamburger0abcde.mekanismsun.common.inventory.slot.AdvanceBinInventorySlot;
import mekanism.common.upgrade.IUpgradeData;

public record MSBinUpgradeData(boolean redstone, AdvanceBinInventorySlot binSlot) implements IUpgradeData {
}
