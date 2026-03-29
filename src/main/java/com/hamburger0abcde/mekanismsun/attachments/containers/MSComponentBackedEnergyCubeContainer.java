package com.hamburger0abcde.mekanismsun.attachments.containers;

import com.hamburger0abcde.mekanismsun.item.block.MSItemBlockEnergyCube;
import com.hamburger0abcde.mekanismsun.tiers.storage.AdvanceEnergyCubeTier;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.functions.ConstantPredicates;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.energy.ComponentBackedEnergyContainer;
import net.minecraft.world.item.ItemStack;

@NothingNullByDefault
public class MSComponentBackedEnergyCubeContainer extends ComponentBackedEnergyContainer {
    public static MSComponentBackedEnergyCubeContainer create(ContainerType<?, ?, ?> ignored, ItemStack attachedTo, int containerIndex) {
        if (!(attachedTo.getItem() instanceof MSItemBlockEnergyCube item)) {
            throw new IllegalStateException("Attached to should always be an energy cube item");
        }
        return new MSComponentBackedEnergyCubeContainer(attachedTo, containerIndex, item.getAdvancedTier());
    }

    private final boolean isCreative;

    public MSComponentBackedEnergyCubeContainer(ItemStack attachedTo, int containerIndex, AdvanceEnergyCubeTier tier) {
        super(attachedTo, containerIndex, ConstantPredicates.alwaysTrue(), ConstantPredicates.alwaysTrue(),
                tier::getOutput, tier::getMaxEnergy);
        isCreative = false;
    }

    @Override
    public long insert(long amount, Action action, AutomationType automationType) {
        return super.insert(amount, action.combine(!isCreative), automationType);
    }

    @Override
    public long extract(long amount, Action action, AutomationType automationType) {
        return super.extract(amount, action.combine(!isCreative), automationType);
    }
}
