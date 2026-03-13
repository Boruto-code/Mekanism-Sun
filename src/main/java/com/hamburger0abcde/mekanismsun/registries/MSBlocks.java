package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.block.ore.MSBlockOre;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunPort;
import com.hamburger0abcde.mekanismsun.world.MSOreBlockType;
import com.hamburger0abcde.mekanismsun.world.MSOreType;
import mekanism.common.block.interfaces.IHasDescription;
import mekanism.common.block.prefab.BlockBasicMultiblock;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MSBlocks {
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(MekanismSun.MODID);

    public static final Map<MSOreType, MSOreBlockType> ORES = new LinkedHashMap<>();

    static {
        for (MSOreType ore : MSOreType.values()) {
            ORES.put(ore, registerOre(ore));
        }
    }

    private static <BLOCK extends Block & IHasDescription> BlockRegistryObject<BLOCK, ItemBlockTooltip<BLOCK>> registerBlock(
            String name, Supplier<? extends BLOCK> blockSupplier) {
        return BLOCKS.register(name, blockSupplier, ItemBlockTooltip::new);
    }

    private static MSOreBlockType registerOre(MSOreType ore) {
        String name = ore.getResource().getRegistrySuffix() + "_ore";
        BlockRegistryObject<MSBlockOre, ItemBlockTooltip<MSBlockOre>> stoneOre = registerBlock(name, () -> new MSBlockOre(ore));
        BlockRegistryObject<MSBlockOre, ItemBlockTooltip<MSBlockOre>> deepslateOre = BLOCKS.register("deepslate_" + name,
                () -> new MSBlockOre(ore, BlockBehaviour.Properties.ofLegacyCopy(stoneOre.value()).mapColor(MapColor.DEEPSLATE)
                        .strength(4.5F, 3).sound(SoundType.DEEPSLATE)), ItemBlockTooltip::new);
        return new MSOreBlockType(stoneOre, deepslateOre);
    }

    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityArtificialSunCasing>,
            ItemBlockTooltip<BlockBasicMultiblock<TileEntityArtificialSunCasing>>> ARTIFICIAL_SUN_CASING = registerBlock(
                    "artificial_sun_casing",
                    () -> new BlockBasicMultiblock<>(MSBlockTypes.ARTIFICIAL_SUN_CASING,
                            properties -> properties.mapColor(MapColor.COLOR_YELLOW))
    );

    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityArtificialSunPort>,
            ItemBlockTooltip<BlockBasicMultiblock<TileEntityArtificialSunPort>>> ARTIFICIAL_SUN_PORT = registerBlock(
            "artificial_sun_port",
            () -> new BlockBasicMultiblock<>(MSBlockTypes.ARTIFICIAL_SUN_PORT,
                    properties -> properties.mapColor(MapColor.COLOR_YELLOW))
    );
}
