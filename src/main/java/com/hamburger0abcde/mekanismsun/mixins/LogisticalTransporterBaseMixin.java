package com.hamburger0abcde.mekanismsun.mixins;

import com.hamburger0abcde.mekanismsun.common.interfaces.ILogisticalTransporterBaseMixin;
import mekanism.common.content.network.InventoryNetwork;
import mekanism.common.content.network.transmitter.LogisticalTransporterBase;
import mekanism.common.content.network.transmitter.Transmitter;
import mekanism.common.content.transporter.TransporterStack;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tile.transmitter.TileEntityTransmitter;
import net.neoforged.neoforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = LogisticalTransporterBase.class, remap = false)
public abstract class LogisticalTransporterBaseMixin extends Transmitter<IItemHandler, InventoryNetwork, LogisticalTransporterBase>
        implements ILogisticalTransporterBaseMixin {

    public LogisticalTransporterBaseMixin(TileEntityTransmitter transmitterTile, TransmissionType... transmissionTypes) {
        super(transmitterTile, transmissionTypes);
    }

    @Shadow
    protected abstract void entityEntering(TransporterStack stack, int progress);

    @Override
    public void mekanismsun$getEntity(TransporterStack stack, int progress) {
        entityEntering(stack, progress);
    }
}
