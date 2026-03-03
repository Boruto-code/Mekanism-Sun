package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.MekanismSunLang;
import mekanism.common.registration.MekanismDeferredHolder;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import mekanism.common.registries.MekanismCreativeTabs;
import net.minecraft.world.item.CreativeModeTab;

public class MSCreativeTabs {
    public static final CreativeTabDeferredRegister CREATIVE_TABS = new CreativeTabDeferredRegister(MekanismSun.MODID);

    public static final MekanismDeferredHolder<CreativeModeTab, CreativeModeTab> MEKANISM_SUN =
            CREATIVE_TABS.registerMain(MekanismSunLang.MEKANISM_SUN, MSBlocks.ARTIFICIAL_SUN_PORT.getItemHolder(), builder ->
                builder.withSearchBar(70)
                        .backgroundTexture(MekanismSun.rl("textures/gui/creative_tab.png"))
                        .withTabsBefore(MekanismCreativeTabs.MEKANISM.getKey())
                        .displayItems((displayParameters, output) -> {
                            CreativeTabDeferredRegister.addToDisplay(MSBlocks.BLOCKS, output);
                        })
            );
}
