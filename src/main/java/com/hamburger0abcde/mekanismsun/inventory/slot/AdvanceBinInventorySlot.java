package com.hamburger0abcde.mekanismsun.inventory.slot;

import com.hamburger0abcde.mekanismsun.attachments.containers.MSComponentBackedBinInventorySlot;
import com.hamburger0abcde.mekanismsun.item.block.MSItemBlockBin;
import com.hamburger0abcde.mekanismsun.tiers.storage.AdvanceBinTier;
import lombok.Getter;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.SerializationConstants;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.functions.ConstantPredicates;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.inventory.IMekanismInventory;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.inventory.container.slot.InventoryContainerSlot;
import mekanism.common.inventory.slot.BasicInventorySlot;
import mekanism.common.inventory.slot.BinInventorySlot;
import mekanism.common.item.block.ItemBlockBin;
import mekanism.common.util.NBTUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@NothingNullByDefault
public class AdvanceBinInventorySlot extends BasicInventorySlot {
    public static final Predicate<@NotNull ItemStack> validator =
            stack -> !(stack.getItem() instanceof MSItemBlockBin) && !(stack.getItem() instanceof ItemBlockBin);

    @Nullable
    public static MSComponentBackedBinInventorySlot getForStack(@NotNull ItemStack stack) {
        if (!stack.isEmpty() && stack.getItem() instanceof MSItemBlockBin) {
            IMekanismInventory attachment = ContainerType.ITEM.createHandler(stack);
            if (attachment != null) {
                List<IInventorySlot> slots = attachment.getInventorySlots(null);
                if (slots.size() == 1) {
                    IInventorySlot slot = slots.get(0);
                    if (slot instanceof MSComponentBackedBinInventorySlot binSlot) {
                        return binSlot;
                    }
                }
            }
        }
        return null;
    }

    public static AdvanceBinInventorySlot create(@Nullable IContentsListener listener, AdvanceBinTier tier) {
        Objects.requireNonNull(tier, "Bin tier cannot be null");
        return new AdvanceBinInventorySlot(listener, tier);
    }

    private final boolean isCreative;
    @Getter
    private ItemStack lockStack = ItemStack.EMPTY;

    private AdvanceBinInventorySlot(@Nullable IContentsListener listener, AdvanceBinTier tier) {
        super(tier.getStorage(), ConstantPredicates.alwaysTrueBi(), ConstantPredicates.alwaysTrueBi(), validator, listener, 0, 0);
        isCreative = false;
        obeyStackLimit = false;
    }

    @Override
    public @NotNull ItemStack insertItem(@NotNull ItemStack stack, @NotNull Action action, @NotNull AutomationType automationType) {
        if (isEmpty()) {
            if (isLocked() && !ItemStack.isSameItemSameComponents(lockStack, stack)) {
                // When locked, we need to make sure the correct item type is being inserted
                return stack;
            } else if (isCreative && action.execute() && automationType != AutomationType.EXTERNAL) {
                //If a player manually inserts into a creative bin, that is empty we need to allow setting the type,
                // Note: We check that it is not external insertion because an empty creative bin acts as a "void" for automation
                ItemStack simulatedRemainder = super.insertItem(stack, Action.SIMULATE, automationType);
                if (simulatedRemainder.isEmpty()) {
                    //If we are able to insert it then set perform the action of setting it to full
                    setStackUnchecked(stack.copyWithCount(getLimit(stack)));
                }
                return simulatedRemainder;
            }
        }
        return super.insertItem(stack, action.combine(!isCreative), automationType);
    }

    @Override
    public ItemStack extractItem(int amount, Action action, AutomationType automationType) {
        return super.extractItem(amount, action.combine(!isCreative), automationType);
    }

    @Override
    public int setStackSize(int amount, Action action) {
        return super.setStackSize(amount, action.combine(!isCreative));
    }

    @Nullable
    @Override
    public InventoryContainerSlot createContainerSlot() {
        return null;
    }

    public ItemStack getBottomStack() {
        if (isEmpty()) {
            return ItemStack.EMPTY;
        }
        return current.copyWithCount(Math.min(getCount(), current.getMaxStackSize()));
    }

    public boolean setLocked(boolean lock) {
        if (isCreative || isLocked() == lock || (lock && isEmpty())) {
            return false;
        }
        lockStack = lock ? current.copyWithCount(1) : ItemStack.EMPTY;
        return true;
    }

    public void setLockStack(@NotNull ItemStack stack) {
        lockStack = stack.copyWithCount(1);
    }

    public boolean isLocked() {
        return !lockStack.isEmpty();
    }

    public ItemStack getRenderStack() {
        return isLocked() ? getLockStack() : getStack();
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag nbt = super.serializeNBT(provider);
        if (isLocked()) {
            nbt.put(SerializationConstants.LOCK_STACK, lockStack.save(provider));
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        NBTUtils.setItemStackOrEmpty(provider, nbt, SerializationConstants.LOCK_STACK, s -> this.lockStack = s);
        super.deserializeNBT(provider, nbt);
    }
}
