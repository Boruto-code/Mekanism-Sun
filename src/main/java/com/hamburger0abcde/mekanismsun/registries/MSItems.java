package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.item.MSItemAlloy;
import com.hamburger0abcde.mekanismsun.tiers.AdvanceAlloyTier;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class MSItems {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MekanismSun.MODID);

    private static ItemRegistryObject<MSItemAlloy> registerAlloy(AdvanceAlloyTier tier, Rarity rarity) {
        return ITEMS.registerItem(tier.getName() + "_alloy", properties -> new MSItemAlloy(tier, properties.rarity(rarity)));
    }

    public static final ItemRegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver");

    public static final ItemRegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot");
    public static final ItemRegistryObject<Item> ELECTRUM_INGOT = ITEMS.register("electrum_ingot");

    public static final ItemRegistryObject<Item> SILVER_DUST = ITEMS.register("silver_dust");
    public static final ItemRegistryObject<Item> ELECTRUM_DUST = ITEMS.register("electrum_dust");

    public static final ItemRegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget");
    public static final ItemRegistryObject<Item> ELECTRUM_NUGGET = ITEMS.register("electrum_nugget");

    public static final ItemRegistryObject<MSItemAlloy> SUPERNOVA_ALLOY =
            registerAlloy(AdvanceAlloyTier.SUPERNOVA, Rarity.RARE);
}
