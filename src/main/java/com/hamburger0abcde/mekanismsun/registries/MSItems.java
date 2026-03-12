package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.item.Item;

public class MSItems {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MekanismSun.MODID);

    public static final ItemRegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot");

    public static final ItemRegistryObject<Item> SILVER_DUST = ITEMS.register("silver_dust");

    public static final ItemRegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget");
}
