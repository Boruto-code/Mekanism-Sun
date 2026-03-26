package com.hamburger0abcde.mekanismsun.client;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.client.gui.GuiAdvanceChemicalTank;
import com.hamburger0abcde.mekanismsun.client.gui.GuiAlloyer;
import com.hamburger0abcde.mekanismsun.client.gui.GuiArtificialSun;
import com.hamburger0abcde.mekanismsun.client.gui.GuiElectricNeutronActivator;
import com.hamburger0abcde.mekanismsun.registries.MSContainerTypes;
import mekanism.client.ClientRegistrationUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = MekanismSun.MODID, value = Dist.CLIENT)
public class MSClientRegistration {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerScreens(RegisterMenuScreensEvent event) {
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ARTIFICIAL_SUN, GuiArtificialSun::new);

        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ALLOYER, GuiAlloyer::new);
        ClientRegistrationUtil.registerElectricScreen(event, MSContainerTypes.TRANSMUTATOR);
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ELECTRIC_NEUTRON_ACTIVATOR, GuiElectricNeutronActivator::new);

        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ADVANCE_CHEMICAL_TANK, GuiAdvanceChemicalTank::new);
    }
}
