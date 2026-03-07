package com.hamburger0abcde.mekanismsun.client;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.client.gui.GuiArtificialSun;
import com.hamburger0abcde.mekanismsun.registries.MSContainerTypes;
import mekanism.client.ClientRegistrationUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = MekanismSun.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class MSClientRegistration {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerScreens(RegisterMenuScreensEvent event) {
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ARTIFICIAL_SUN, GuiArtificialSun::new);
    }
}
