package com.hamburger0abcde.mekanismsun.client;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.client.render.transmitter.RenderAdvanceThermodynamicConductor;
import com.hamburger0abcde.mekanismsun.common.block.attribute.MSAttribute;
import com.hamburger0abcde.mekanismsun.client.gui.*;
import com.hamburger0abcde.mekanismsun.client.model.AdvanceEnergyCubeModelLoader;
import com.hamburger0abcde.mekanismsun.client.model.ColorModelEnergyCore;
import com.hamburger0abcde.mekanismsun.client.render.item.block.RenderAdvanceEnergyCubeItem;
import com.hamburger0abcde.mekanismsun.client.render.item.block.RenderAdvanceFluidTankItem;
import com.hamburger0abcde.mekanismsun.client.render.tile_entity.RenderAdvanceBin;
import com.hamburger0abcde.mekanismsun.client.render.tile_entity.RenderAdvanceEnergyCube;
import com.hamburger0abcde.mekanismsun.client.render.tile_entity.RenderAdvanceFluidTank;
import com.hamburger0abcde.mekanismsun.client.render.transmitter.RenderAdvanceMechanicalPipe;
import com.hamburger0abcde.mekanismsun.client.render.transmitter.RenderAdvancePressurizedTube;
import com.hamburger0abcde.mekanismsun.client.render.transmitter.RenderAdvanceUniversalCable;
import com.hamburger0abcde.mekanismsun.common.item.block.MSItemBlockEnergyCube;
import com.hamburger0abcde.mekanismsun.common.item.block.MSItemBlockFluidTank;
import com.hamburger0abcde.mekanismsun.common.registries.MSBlocks;
import com.hamburger0abcde.mekanismsun.common.registries.MSContainerTypes;
import com.hamburger0abcde.mekanismsun.common.registries.MSTileEntityTypes;
import com.hamburger0abcde.mekanismsun.common.tiers.AdvancedTier;
import mekanism.client.ClientRegistrationUtil;
import mekanism.client.render.MekanismRenderer;
import mekanism.client.render.RenderPropertiesProvider;
import mekanism.client.render.item.TransmitterTypeDecorator;
import mekanism.client.render.transmitter.RenderLogisticalTransporter;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = MekanismSun.MODID, value = Dist.CLIENT)
public class MSClientRegistration {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerScreens(RegisterMenuScreensEvent event) {
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ARTIFICIAL_SUN, GuiArtificialSun::new);
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ADVANCE_INDUCTION_MATRIX, GuiAdvanceInductionMatrix::new);
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ADVANCE_MATRIX_STATS, GuiAdvanceMatrixStats::new);

        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ALLOYER, GuiAlloyer::new);
        ClientRegistrationUtil.registerElectricScreen(event, MSContainerTypes.TRANSMUTATOR);
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ELECTRIC_NEUTRON_ACTIVATOR, GuiElectricNeutronActivator::new);

        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ADVANCE_CHEMICAL_TANK, GuiAdvanceChemicalTank::new);
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ADVANCE_FLUID_TANK, GuiAdvanceFluidTank::new);
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ADVANCE_ENERGY_CUBE, GuiAdvanceEnergyCube::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ColorModelEnergyCore.CORE_LAYER, ColorModelEnergyCore::createLayerDefinition);
    }

    @SubscribeEvent
    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        ClientRegistrationUtil.registerClientReloadListeners(event, RenderAdvanceEnergyCubeItem.ADVANCE_RENDERER);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ClientRegistrationUtil.bindTileEntityRenderer(event, RenderAdvanceFluidTank::new, MSTileEntityTypes.SUPERNOVA_FLUID_TANK);
        ClientRegistrationUtil.bindTileEntityRenderer(event, RenderAdvanceEnergyCube::new, MSTileEntityTypes.SUPERNOVA_ENERGY_CUBE);
        ClientRegistrationUtil.bindTileEntityRenderer(event, RenderAdvanceBin::new, MSTileEntityTypes.SUPERNOVA_BIN);

        ClientRegistrationUtil.bindTileEntityRenderer(event, RenderAdvanceUniversalCable::new, MSTileEntityTypes.SUPERNOVA_UNIVERSAL_CABLE);
        ClientRegistrationUtil.bindTileEntityRenderer(event, RenderAdvanceMechanicalPipe::new, MSTileEntityTypes.SUPERNOVA_MECHANICAL_PIPE);
        ClientRegistrationUtil.bindTileEntityRenderer(event, RenderAdvancePressurizedTube::new, MSTileEntityTypes.SUPERNOVA_PRESSURIZED_TUBE);
        ClientRegistrationUtil.bindTileEntityRenderer(event, RenderAdvanceThermodynamicConductor::new, MSTileEntityTypes.SUPERNOVA_THERMODYNAMIC_CONDUCTOR);
    }

    @SubscribeEvent
    public static void registerModelLoaders(ModelEvent.RegisterGeometryLoaders event) {
        event.register(MekanismSun.rl("energy_cube"), AdvanceEnergyCubeModelLoader.INSTANCE);
    }

    @SubscribeEvent
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        ClientRegistrationUtil.registerBlockColorHandler(event, (state, world, pos, tintIndex) -> {
            if (tintIndex == 1) {
                AdvancedTier tier = MSAttribute.getAdvancedTier(state.getBlockHolder().value());
                if (tier != null) {
                    return MekanismRenderer.getColorARGB(tier, 1);
                }
            }
            return -1;
        }, MSBlocks.SUPERNOVA_FLUID_TANK);

        ClientRegistrationUtil.registerBlockColorHandler(event, (state, world, pos, index) -> {
            if (index == 1) {
                AdvancedTier tier = MSAttribute.getAdvancedTier(state.getBlockHolder().value());
                if (tier != null) {
                    return MekanismRenderer.getColorARGB(tier, 1);
                }
            }
            return -1;
        }, MSBlocks.SUPERNOVA_ENERGY_CUBE);
    }

    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        ClientRegistrationUtil.registerItemColorHandler(event, (stack, tintIndex) -> {
            Item item = stack.getItem();
            if (tintIndex == 1 && item instanceof MSItemBlockFluidTank tank) {
                return MekanismRenderer.getColorARGB(tank.getAdvancedTier().getAdvanceTier(), 1);
            }
            return -1;
        }, MSBlocks.SUPERNOVA_FLUID_TANK);

        ClientRegistrationUtil.registerItemColorHandler(event, (stack, tintIndex) -> {
            Item item = stack.getItem();
            if (tintIndex == 1 && item instanceof MSItemBlockEnergyCube cube) {
                return MekanismRenderer.getColorARGB(cube.getAdvancedTier().getAdvanceTier(), 1);
            }
            return -1;
        }, MSBlocks.SUPERNOVA_ENERGY_CUBE);
    }

    @SubscribeEvent
    public static void registerItemDecorations(RegisterItemDecorationsEvent event) {
        TransmitterTypeDecorator.registerDecorators(
                event,
                MSBlocks.SUPERNOVA_UNIVERSAL_CABLE,
                MSBlocks.SUPERNOVA_PRESSURIZED_TUBE,
                MSBlocks.SUPERNOVA_THERMODYNAMIC_CONDUCTOR
        );
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        ClientRegistrationUtil.registerItemExtensions(event, new RenderPropertiesProvider
                .MekRenderProperties(RenderAdvanceFluidTankItem.ADVANCE_RENDERER), MSBlocks.SUPERNOVA_FLUID_TANK);
        ClientRegistrationUtil.registerItemExtensions(event, new RenderPropertiesProvider
                .MekRenderProperties(RenderAdvanceEnergyCubeItem.ADVANCE_RENDERER), MSBlocks.SUPERNOVA_ENERGY_CUBE);
        ClientRegistrationUtil.registerBlockExtensions(event, MSBlocks.BLOCKS);
    }
}
