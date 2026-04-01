package com.hamburger0abcde.mekanismsun.common.attachments.containers;

import com.hamburger0abcde.mekanismsun.common.item.block.MSItemBlockFluidTank;
import com.hamburger0abcde.mekanismsun.common.tiers.storage.AdvanceFluidTankTier;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.functions.ConstantPredicates;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.fluid.AttachedFluids;
import mekanism.common.attachments.containers.fluid.ComponentBackedFluidTank;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

@NothingNullByDefault
public class MSComponentBackedFluidTankFluidTank extends ComponentBackedFluidTank {
    private final boolean isCreative;

    public static MSComponentBackedFluidTankFluidTank create(ContainerType<?, ?, ?> ignored, ItemStack attachedTo, int tankIndex) {
        if (!(attachedTo.getItem() instanceof MSItemBlockFluidTank item)) {
            throw new IllegalStateException("Attached to should always be a fluid tank item");
        }
        return new MSComponentBackedFluidTankFluidTank(attachedTo, tankIndex, item.getAdvancedTier());
    }

    private MSComponentBackedFluidTankFluidTank(ItemStack attachedTo, int tankIndex, AdvanceFluidTankTier tier) {
        super(attachedTo, tankIndex, ConstantPredicates.alwaysTrueBi(), ConstantPredicates.alwaysTrueBi(),
                ConstantPredicates.alwaysTrue(), tier::getOutput, tier::getStorage);
        isCreative = false;
    }

    @Override
    public @NotNull FluidStack insert(@NotNull FluidStack stack, Action action, @NotNull AutomationType automationType) {
        return super.insert(stack, action.combine(!isCreative), automationType);
    }

    @Override
    public @NotNull FluidStack extract(@NotNull AttachedFluids attachedFluids, @NotNull FluidStack stored, int amount, Action action,
                                       @NotNull AutomationType automationType) {
        return super.extract(attachedFluids, stored, amount, action.combine(!isCreative), automationType);
    }

    /**
     * {@inheritDoc}
     *
     * Note: We are only patching {@link #setStackSize(AttachedFluids, FluidStack, int, Action)}, as both {@link #growStack(int, Action)} and
     * {@link #shrinkStack(int, Action)} are wrapped through this method.
     */
    @Override
    public int setStackSize(@NotNull AttachedFluids attachedFluids, @NotNull FluidStack stored, int amount, Action action) {
        return super.setStackSize(attachedFluids, stored, amount, action.combine(!isCreative));
    }
}
