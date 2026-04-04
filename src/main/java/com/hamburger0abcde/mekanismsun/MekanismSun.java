package com.hamburger0abcde.mekanismsun;

import com.hamburger0abcde.mekanismsun.common.MekanismSunLang;
import com.hamburger0abcde.mekanismsun.common.config.MSConfig;
import com.hamburger0abcde.mekanismsun.common.multiblock.artificial_sun.ArtificialSunMultiblockData;
import com.hamburger0abcde.mekanismsun.common.multiblock.MSBuilders;
import com.hamburger0abcde.mekanismsun.common.multiblock.artificial_sun.ArtificialSunCache;
import com.hamburger0abcde.mekanismsun.common.multiblock.artificial_sun.ArtificialSunValidator;
import com.hamburger0abcde.mekanismsun.common.multiblock.matrix.AdvanceMatrixMultiblockData;
import com.hamburger0abcde.mekanismsun.common.multiblock.matrix.AdvanceMatrixValidator;
import com.hamburger0abcde.mekanismsun.common.network.MSPacketHandler;
import com.hamburger0abcde.mekanismsun.common.registries.*;
import com.mojang.logging.LogUtils;
import mekanism.common.base.IModModule;
import mekanism.common.command.CommandMek;
import mekanism.common.command.builders.BuildCommand;
import mekanism.common.lib.Version;
import mekanism.common.lib.multiblock.MultiblockCache;
import mekanism.common.lib.multiblock.MultiblockManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
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
    public static final MultiblockManager<AdvanceMatrixMultiblockData> matrixManager =
            new MultiblockManager<>("advance_matrix", MultiblockCache::new, AdvanceMatrixValidator::new);

    public MekanismSun(IEventBus modEventBus, ModContainer modContainer) {
        versionNumber = new Version(modContainer);
        MSConfig.registerConfigs(modContainer);

        NeoForge.EVENT_BUS.addListener(this::registerCommands);

        MSBlocks.BLOCKS.register(modEventBus);
        MSItems.ITEMS.register(modEventBus);
        MSCreativeTabs.CREATIVE_TABS.register(modEventBus);
        MSTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        MSChemicals.CHEMICALS.register(modEventBus);
        MSContainerTypes.CONTAINER_TYPES.register(modEventBus);
        MSRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);

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

    private void registerCommands(RegisterCommandsEvent event) {
        BuildCommand.register("artificial_sun", MekanismSunLang.ARTIFICIAL_SUN, new MSBuilders.ArtificialSunBuilder());
        BuildCommand.register("advance_matrix", MekanismSunLang.ADVANCE_MATRIX, new MSBuilders.AdvanceMatrixBuilder());
        event.getDispatcher().register(CommandMek.register());
    }
}
