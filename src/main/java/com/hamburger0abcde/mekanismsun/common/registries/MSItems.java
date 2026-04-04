package com.hamburger0abcde.mekanismsun.common.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.common.item.MSItemAlloy;
import com.hamburger0abcde.mekanismsun.common.tiers.AdvanceAlloyTier;
import com.hamburger0abcde.mekanismsun.common.tiers.AdvancedTier;
import mekanism.api.text.TextComponentUtil;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public class MSItems {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MekanismSun.MODID);

    private static ItemRegistryObject<Item> registerAlloy(AdvancedTier tier) {
        return ITEMS.registerItem(tier.getLowerName() + "_alloy", properties -> new Item(properties) {
            @NotNull
            @Override
            public Component getName(@NotNull ItemStack stack) {
                return TextComponentUtil.build(tier.getColor(), super.getName(stack));
            }
        });
    }

    private static ItemRegistryObject<Item> registerCircuit(AdvancedTier tier) {
        return ITEMS.registerItem(tier.getLowerName() + "_control_circuit", properties -> new Item(properties) {
            @NotNull
            @Override
            public Component getName(@NotNull ItemStack stack) {
                return TextComponentUtil.build(tier.getColor(), super.getName(stack));
            }
        });
    }

    public static final ItemRegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver");

    public static final ItemRegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot");
    public static final ItemRegistryObject<Item> ELECTRUM_INGOT = ITEMS.register("electrum_ingot");

    public static final ItemRegistryObject<Item> SILVER_DUST = ITEMS.register("silver_dust");
    public static final ItemRegistryObject<Item> ELECTRUM_DUST = ITEMS.register("electrum_dust");

    public static final ItemRegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget");
    public static final ItemRegistryObject<Item> ELECTRUM_NUGGET = ITEMS.register("electrum_nugget");

    public static final ItemRegistryObject<Item> SUPERNOVA_ALLOY = registerAlloy(AdvancedTier.SUPERNOVA);

    public static final ItemRegistryObject<Item> SUPERNOVA_CONTROL_CIRCUIT = registerCircuit(AdvancedTier.SUPERNOVA);
}
