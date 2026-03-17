package com.hamburger0abcde.mekanismsun;

import com.hamburger0abcde.mekanismsun.config.MSConfig;
import com.hamburger0abcde.mekanismsun.multiblock.artificial_sun.ArtificialSunMultiblockData;
import com.hamburger0abcde.mekanismsun.multiblock.MSBuilders;
import com.hamburger0abcde.mekanismsun.multiblock.artificial_sun.ArtificialSunCache;
import com.hamburger0abcde.mekanismsun.multiblock.artificial_sun.ArtificialSunValidator;
import com.hamburger0abcde.mekanismsun.network.MSPacketHandler;
import com.hamburger0abcde.mekanismsun.registries.*;
import com.mojang.logging.LogUtils;
import mekanism.common.command.builders.BuildCommand;
import mekanism.common.lib.Version;
import mekanism.common.lib.multiblock.MultiblockManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MekanismSun.MODID)
public class MekanismSun {
    public static final String MODID = "mekanismsun";
    public static final String NAME = "MekanismSun";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static MekanismSun instance;
    public final Version versionNumber;
    private final MSPacketHandler packetHandler;

    public static final MultiblockManager<ArtificialSunMultiblockData> artificialSunManager =
            new MultiblockManager<>("artificial_sun", ArtificialSunCache::new, ArtificialSunValidator::new);

    public MekanismSun(IEventBus modEventBus, ModContainer modContainer) {
        versionNumber = new Version(modContainer);
        MSConfig.registerConfigs(modContainer);

        modEventBus.addListener(this::commonSetup);

        MSBlocks.BLOCKS.register(modEventBus);
        MSItems.ITEMS.register(modEventBus);
        MSCreativeTabs.CREATIVE_TABS.register(modEventBus);
        MSTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        MSChemicals.CHEMICALS.register(modEventBus);
        MSContainerTypes.CONTAINER_TYPES.register(modEventBus);
        MSRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        MSFeatures.FEATURES.register(modEventBus);

        //NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(MSConfig::onConfigLoad);
        packetHandler = new MSPacketHandler(modEventBus, versionNumber);
    }

    public static MSPacketHandler getPacketHandler() {
        return instance.packetHandler;
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
