package com.hamburger0abcde.mekanismsun;

import com.hamburger0abcde.mekanismsun.config.MSConfig;
import com.hamburger0abcde.mekanismsun.content.artificial_sun.ArtificialSunMultiblockData;
import com.hamburger0abcde.mekanismsun.multiblock.MSBuilders;
import com.hamburger0abcde.mekanismsun.multiblock.artificial_sun.ArtificialSunCache;
import com.hamburger0abcde.mekanismsun.multiblock.artificial_sun.ArtificialValidator;
import com.hamburger0abcde.mekanismsun.registries.MSBlocks;
import com.hamburger0abcde.mekanismsun.registries.MSChemicals;
import com.hamburger0abcde.mekanismsun.registries.MSCreativeTabs;
import com.hamburger0abcde.mekanismsun.registries.MSTileEntityTypes;
import com.mojang.logging.LogUtils;
import mekanism.common.command.builders.BuildCommand;
import mekanism.common.lib.multiblock.MultiblockManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MekanismSun.MODID)
public class MekanismSun {
    public static final String MODID = "mekanismsun";
    public static final String NAME = "MekanismSun";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final MultiblockManager<ArtificialSunMultiblockData> artificialSunManager =
            new MultiblockManager<>("artificial_sun", ArtificialSunCache::new, ArtificialValidator::new);

    public MekanismSun(IEventBus modEventBus, ModContainer modContainer) {
        MSConfig.registerConfigs(modContainer);

        modEventBus.addListener(this::commonSetup);

        MSBlocks.BLOCKS.register(modEventBus);
        MSCreativeTabs.CREATIVE_TABS.register(modEventBus);
        MSTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        MSChemicals.CHEMICALS.register(modEventBus);

        //NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(MSConfig::onConfigLoad);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BuildCommand.register("artificial_sun", MekanismSunLang.ARTIFICIAL_SUN, new MSBuilders.ArtificialSunBuilder());
        });
    }
}
